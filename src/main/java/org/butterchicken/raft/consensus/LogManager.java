package org.butterchicken.raft.consensus;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.butterchicken.raft.cluster.ClusterNode;
import org.butterchicken.raft.consensus.enums.ServerState;
import org.butterchicken.raft.consensus.persist.RaftPersistantState;
import org.butterchicken.raft.consensus.request.AppendEntriesRequest;
import org.butterchicken.raft.consensus.request.AppendEntriesResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static java.lang.Math.min;

@Getter
@Log4j2
public class LogManager {
    private RaftPersistantState raftPersistantState;
    private List<Integer> nextIndexes;
    private List<Integer> matchIndexes;

    private final Integer clusterSize;
    public Integer commitIndex;
    public Integer lastApplied;
    private static LogManager instance;
    private final Integer currentTerm;
    private final Integer id;

    private LogManager () {
        this.raftPersistantState = RaftPersistantState.getInstance();
        this.clusterSize = RaftServer.getInstance().getCluster().size();
        this.currentTerm = RaftServer.getInstance().getCurrentTerm();
        this.id = RaftServer.getInstance().getId();
        this.commitIndex = 0;
        this.lastApplied = 0;

    }

    public static LogManager getInstance()  {
        if(instance == null){
            instance = new LogManager();
        }
        return instance;
    }

    public void resetLogStates() {
        this.nextIndexes = new ArrayList<>(Collections.nCopies(this.clusterSize,RaftPersistantState.getInstance().getLogEntries().size() + 1));
        this.matchIndexes = new ArrayList<>(this.clusterSize);
    }

    public AppendEntriesResponse append(LogEntry logEntry){
        log.trace("Processing new log entry ..............");
        Boolean isReplicatedInMajority = Boolean.FALSE;
        try {
            appendToLocalLog(logEntry);
            log.trace("Entry appended to local log ");
            isReplicatedInMajority  = replicateToCluster();
            updateCommitIndex();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        if(isReplicatedInMajority){

        }

        return AppendEntriesResponse.builder().isReplicated(isReplicatedInMajority).build();

    }

    protected void appendToLocalLog(LogEntry logEntry) throws IOException {
        try {
            this.raftPersistantState.appendToLog(logEntry);
        }catch (IOException e){
            throw  new IOException("Failed to append to local log "+ e.getMessage());

        }

    }

    public Boolean replicateToCluster(){
        log.trace("Replication process has started ");
        AtomicReference<Integer> replicatedCount = new AtomicReference<>(0);
        Integer clusterSize = RaftServer.getInstance().getCluster().size();
        IntStream.range(0,clusterSize)
                .forEach(index -> {
                    log.trace("Initiating replication request to " + index);
                    ClusterNode clusterNode = RaftServer.getInstance().getCluster().get(index);

                    if(!clusterNode.getId().equals(this.id)){
                        log.debug(this.nextIndexes.size());
                        Integer nextIndex = this.nextIndexes.get(index);
                        log.debug("cluster node retrieved");
                        Integer prevLogIndex = nextIndex - 1;
                        Integer prevLogTerm = raftPersistantState.getLogEntries().get(prevLogIndex).getTerm();
                        List<LogEntry> logEntriesToSend = IntStream.range(nextIndex, raftPersistantState.getLogEntries().size()).mapToObj(logEntryIndex -> raftPersistantState.getLogEntries().get(logEntryIndex)).toList();

                        log.trace("Prev log Index " + prevLogIndex);
                        log.trace("prev log term " + prevLogTerm);
                        log.trace("Replication request sent to " + clusterNode.getId());
                        AppendEntriesResponse response = RPCAdapter.sendAppendEntriesRequest(
                                clusterNode,
                                AppendEntriesRequest.builder()
                                        .prevLogIndex(prevLogIndex)
                                        .prevLogTerm(prevLogTerm)
                                        .logEntries(logEntriesToSend)
                                        .leaderID(this.id)
                                        .leaderCommitIndex(this.commitIndex)
                                        .term(this.currentTerm)
                                        .build()
                        );
                        log.trace("isReplicated " + response.getIsReplicated());
                        if(response.getIsReplicated()){
                            replicatedCount.getAndSet(replicatedCount.get() + 1);
                            log.debug("Index " + index);
                            nextIndexes.set(index,raftPersistantState.getLogEntries().size());
                            matchIndexes.set(index,raftPersistantState.getLogEntries().size() - 1);
                        }

                    }else{
                        replicatedCount.getAndSet(replicatedCount.get() + 1);
                        nextIndexes.set(index,raftPersistantState.getLogEntries().size());
                        matchIndexes.set(index,raftPersistantState.getLogEntries().size() - 1);
                    }
                });
        return (((clusterSize + 1)/2) >= replicatedCount.get());

    }

    void updateCommitIndex(){
        assert RaftServer.getInstance().serverState.equals(ServerState.LEADER);
        for(int possibleCommitIndex = raftPersistantState.getLogEntries().size();possibleCommitIndex >= 0; possibleCommitIndex--){
            if(raftPersistantState.getLogEntries().get(possibleCommitIndex).getTerm().equals(this.currentTerm)){
                Integer logMatchingCount = 0;
                for(int matchCandidate = 0;matchCandidate < this.getMatchIndexes().size();matchCandidate++){
                    logMatchingCount += matchIndexes.get(matchCandidate) >= possibleCommitIndex ? 1 : 0;
                }
                if(logMatchingCount >= (this.clusterSize + 1)/2){
                    this.commitIndex = possibleCommitIndex;
                    break;
                }
            }
        }

    }




    // handles remote append entry RPC's
    public AppendEntriesResponse appendEntriesHandle(AppendEntriesRequest appendEntriesRequest){
        log.info("Recieved append entries RPC from leader node " + appendEntriesRequest.getLeaderID() + " validating the request");
        if(appendEntriesRequest.getTerm() < this.currentTerm) return AppendEntriesResponse.builder().isReplicated(false).build();
        if(!raftPersistantState.getLogEntries().isEmpty() && !raftPersistantState.getLogEntries().get(appendEntriesRequest.getPrevLogIndex()).getTerm().equals(appendEntriesRequest.getPrevLogTerm())) return AppendEntriesResponse.builder().isReplicated(false).build();
        log.info("Request has been validated appending logs to local store");

        AtomicReference<Boolean> isReplicated = new AtomicReference<>(true);
        appendEntriesRequest.getLogEntries().forEach(logEntry -> {
            try {
                appendToLocalLog(logEntry);
            } catch (IOException e) {
                log.error("Failed to append to local log");
                isReplicated.set(false);
            }
        });
        if(appendEntriesRequest.getLeaderCommitIndex() > this.commitIndex){
            commitIndex = min(appendEntriesRequest.getLeaderCommitIndex(),raftPersistantState.getLogEntries().size() - 1);
        }
        log.info("Entries appended to local log");
        return AppendEntriesResponse.builder().isReplicated(isReplicated.get()).build();
    }
}

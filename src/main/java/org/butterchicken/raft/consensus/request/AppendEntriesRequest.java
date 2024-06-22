package org.butterchicken.raft.consensus.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.butterchicken.raft.consensus.LogEntry;

import java.util.List;

@Getter
@Builder
public class AppendEntriesRequest {
    Integer leaderID;
    Integer prevLogIndex;
    Integer term;
    Integer prevLogTerm;
    List<LogEntry> logEntries;
    Integer leaderCommitIndex;
}

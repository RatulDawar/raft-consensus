package org.butterchicken.raft.consensus.persist;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.butterchicken.raft.consensus.LogEntry;
import org.butterchicken.raft.consensus.RaftServer;
import org.butterchicken.raft.persistantstore.Persistent;
import org.butterchicken.raft.persistantstore.Store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.serializer.SerializerException;

import java.io.*;
import java.util.IllegalFormatException;
import java.util.LinkedList;
import java.util.List;

@Getter

public class RaftPersistantState implements Persistent,Serializable {

    private static final Logger logger = LogManager.getLogger(RaftPersistantState.class);

    private final String PERSISTANT_FILE_NAME = "raft-persistant-state-with-log.dat";
    private  Integer votedFor;
    private Integer currentTerm;
    private List<LogEntry> logEntries;
    private static RaftPersistantState instance;

    // tries to load from disk otherwise falls back to initial values
    private RaftPersistantState() {
        try {
            this.load();
            logger.trace("Persistent state successfully loaded from disk");
        }catch (FileNotFoundException e){
            this.votedFor = null;
            this.currentTerm = 0;
            this.logEntries = new LinkedList<>();
            logger.warn("Failed to load persistent state from disk, initialising the state , Ignore is starting server for the first time ");
        }catch (IOException ioException){
            logger.error("Persistent state was present but read operation failed terminating raft server");
            System.exit(1);
        }
    }

    void debugLogEntry(RaftPersistantState raftPersistantState){
        logger.debug(raftPersistantState.getCurrentTerm());
        logger.debug(raftPersistantState.getVotedFor());
        raftPersistantState.getLogEntries().forEach(logger::debug);

    }

    // Implement serialize method
    @Override
    public byte[] serialize() throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(this);
            return bos.toByteArray();
        }catch (IOException exception){
            throw new IOException("Serialization failed "+ exception.getMessage());
        }
    }

    // Implement deserialize method
    @Override
    public void deserialize(byte[] serializedObject) throws IOException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedObject);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            RaftPersistantState state = (RaftPersistantState) in.readObject();
            this.votedFor = state.votedFor;
            this.currentTerm = state.currentTerm;
            this.logEntries = state.logEntries;
        } catch (ClassNotFoundException | IOException e) {
            throw new IOException("Failed to deserialize object", e);
        }
    }

    @Override
    public synchronized void  persist()  throws IOException{
        try {
            byte[] serializedObject = this.serialize();
            Store.getInstance().writeToFile(serializedObject, this.getStorageFileName());
        } catch (IOException e) {
            throw  new IOException("Unable to persist object: "  + e.getMessage());
        }
    }

    @Override
    public void load() throws FileNotFoundException,IOException {
        try {
            byte[] serializedObject =  Store.getInstance().readFromFile(this.getStorageFileName());
            this.deserialize(serializedObject);
        }catch (FileNotFoundException e){
            throw e;
        }
        catch (IOException e){
            throw new IOException("Unable to load object " + e.getMessage());
        }
    }

    public static RaftPersistantState getInstance() {
        if(instance == null){
            instance = new RaftPersistantState();
        }
        return instance;
    }


    // forces the class to set a file name for persistant store to store it
    @Override
    public String getStorageFileName() {
        return this.PERSISTANT_FILE_NAME;
    }


    public synchronized void setVotedFor(Integer votedFor) throws IOException{
        this.votedFor = votedFor;
        this.persist();
    }
    public synchronized void setCurrentTerm(Integer currentTerm) throws IOException{
        this.currentTerm = currentTerm;
        this.persist();
    }
    public synchronized void appendToLog(LogEntry logEntry) throws IOException{
        this.logEntries.addLast(logEntry);

        this.persist();
    }


}

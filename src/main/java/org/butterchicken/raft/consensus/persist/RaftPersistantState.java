package org.butterchicken.raft.consensus.persist;

import lombok.Getter;
import lombok.Setter;
import org.butterchicken.raft.consensus.RaftServer;
import org.butterchicken.raft.persistantstore.Persistent;
import org.butterchicken.raft.persistantstore.Store;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

@Getter

public class RaftPersistantState implements Persistent,Serializable {

    private static final Logger logger = LogManager.getLogger(RaftPersistantState.class);

    private final String PERSISTANT_FILE_NAME = "raft-persistant-state.dat";
    private  Integer votedFor;
    private Integer currentTerm;

    // tries to load from disk otherwise falls back to initial values
    public RaftPersistantState() {
        try {
            this.load();
            logger.trace("Persistent state successfully loaded from disk");
        }catch (FileNotFoundException e){
            this.votedFor = null;
            this.currentTerm = 0;
            logger.warn("Failed to load persistent state from disk, initialising the state , Ignore is starting server for the first time ");
        }
    }

    // Implement serialize method
    @Override
    public byte[] serialize() {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(this);
            return bos.toByteArray();
        }catch (IOException exception){
            logger.error("Unable to serialise : "+ exception.getLocalizedMessage());
            return null;
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
        } catch (ClassNotFoundException | IOException e) {
            throw new IOException("Failed to deserialize object", e);
        }
    }

    @Override
    public synchronized void  persist()  {
        try {
            byte[] serializedObject = this.serialize();
            Store.getInstance().writeToFile(serializedObject,this.getStorageFileName());
        } catch (IOException e) {
            logger.error("Unable to persist object: "  + e.getMessage());
        }
    }

    @Override
    public void load() throws FileNotFoundException {
        try {
            byte[] serializedObject =  Store.getInstance().readFromFile(this.getStorageFileName());
            this.deserialize(serializedObject);
        } catch (FileNotFoundException e) {
            throw e;
        }catch (IOException e){
            logger.error("Unable to load object: " + e.getMessage());
        }
    }


    // forces the class to set a file name for persistant store to store it
    @Override
    public String getStorageFileName() {
        return this.PERSISTANT_FILE_NAME;
    }


    public synchronized void setVotedFor(Integer votedFor){
        this.votedFor = votedFor;
        this.persist();
    }
    public synchronized void setCurrentTerm(Integer currentTerm){
        this.currentTerm = currentTerm;
        this.persist();
    }


}

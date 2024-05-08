package org.butterchicken.raft;

import org.butterchicken.raft.consensus.RaftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ServerStarter {

    private static final Logger logger = LogManager.getLogger(ServerStarter.class);
    public static void main(String[] args) {
        RaftServer raftServer = RaftServer.getInstance();
        try {
            raftServer.start();
            logger.info("Server started at " + raftServer.getPort());
        }catch (IOException e){
            logger.error("Failed to start Raft Server ");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
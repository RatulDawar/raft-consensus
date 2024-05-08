package org.butterchicken.raft.persistantstore;



import java.io.*;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.butterchicken.raft.consensus.RaftServer;


// Singleton public store access class
public class Store {

    private static Store instance;
    private static final Logger logger = LogManager.getLogger(Store.class);
    String PERSISTENT_STORE_DIR = "/var/project-store/raft-consensus";

    private Store(){}

    public static Store getInstance(){
        if(instance == null){
            instance = new Store();
        }
        return instance;
    }


    public void writeToFile(byte[] serializedData, @NonNull String fileName) throws IOException {
        // Construct the file path
        File file = new File(this.PERSISTENT_STORE_DIR, fileName);
        // Create the file if it doesn't exist
        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Failed to create file: " + file.getAbsolutePath());
            }
        }

        // Write to the file
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(serializedData);
        } catch (IOException e) {
            logger.error("Failed to persist data " + e.getMessage());
        }
    }


    public byte[] readFromFile(String fileName) throws FileNotFoundException{
        byte[] persistedData = new byte[0];
        try (FileInputStream fis = new FileInputStream(this.PERSISTENT_STORE_DIR + "/" +fileName)) {
            persistedData = fis.readAllBytes();
        }catch (FileNotFoundException e){
            throw e;
        } catch (IOException e) {
            logger.error("Failed to load data "  + e.getMessage());
        }
        return persistedData;
    }




}


package org.butterchicken.raft.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.butterchicken.raft.cluster.ClusterNode;
import org.butterchicken.raft.consensus.RaftServer;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private static final String CLUSTER_CONFIG_FILE_PATH = "./target/classes/config/cluster_config.yaml";
    private static final String SERVER_CONFIG_FILE_PATH = "./target/classes/config/server_config.yaml";

    public static List<ClusterNode> loadClusterConfig() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

        return objectMapper.<ArrayList<ClusterNode>>readValue(new File(CLUSTER_CONFIG_FILE_PATH), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, ClusterNode.class));
    }

    public static RaftServerConfig loadServerConfig() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(SERVER_CONFIG_FILE_PATH), RaftServerConfig.class);
    }


}

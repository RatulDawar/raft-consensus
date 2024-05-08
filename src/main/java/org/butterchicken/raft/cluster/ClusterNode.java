package org.butterchicken.raft.cluster;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClusterNode {

    private Integer id;
    private String host;
    private Integer port;
    private Integer votedFor;

}


package org.butterchicken.raft.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// loader class to store data temporarily from config
public class RaftServerConfig {
    private Integer id;
    private Integer port;
}

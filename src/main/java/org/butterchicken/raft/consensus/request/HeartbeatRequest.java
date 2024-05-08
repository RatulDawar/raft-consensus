package org.butterchicken.raft.consensus.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeartbeatRequest {
    Integer senderId;
    Integer term;
}

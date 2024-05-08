package org.butterchicken.raft.consensus.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoteResponse {
    Integer term;
    Boolean voteGranted;
}

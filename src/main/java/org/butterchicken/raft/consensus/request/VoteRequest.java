package org.butterchicken.raft.consensus.request;

import lombok.Getter;

@Getter
public class VoteRequest {
    Integer term;
    Integer candidateId;
    public VoteRequest(Integer term,Integer candidateId){
        this.term = term;
        this.candidateId = candidateId;
    }

}

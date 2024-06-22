package org.butterchicken.raft.consensus.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppendEntriesResponse {
    Boolean isReplicated;
}

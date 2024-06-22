package org.butterchicken.raft.consensus;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class LogEntry implements Serializable {
    Integer term;
    byte[] command;
}

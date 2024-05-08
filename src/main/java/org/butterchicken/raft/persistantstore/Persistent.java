package org.butterchicken.raft.persistantstore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface Persistent {
    public byte[]  serialize() throws IOException;

    void deserialize(byte [] serializedObject) throws IOException;

    void persist() ;

    void load() throws FileNotFoundException;

    String getStorageFileName();
}

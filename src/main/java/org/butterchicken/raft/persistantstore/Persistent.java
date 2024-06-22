package org.butterchicken.raft.persistantstore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface Persistent {
    public byte[]  serialize() throws IOException;

    void deserialize(byte [] serializedObject) throws IOException;

    void persist() throws IOException;

    void load() throws FileNotFoundException, IOException;

    String getStorageFileName();
}

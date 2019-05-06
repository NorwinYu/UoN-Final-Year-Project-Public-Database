package org.yesworkflow.save.serialization;


public interface IYwSerializer {
    String Serialize(Object object);
    <T> T Deserialize(String json, Class<T> dto);
}

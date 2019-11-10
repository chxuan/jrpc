package pers.chxuan.jrpc.serialize;

public interface MessageSerialize {

    byte[] serialize(Object object);

    Object unserialize(String className, byte[] bytes) throws Exception;
}

package pers.chxuan.jrpc.json.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import pers.chxuan.jrpc.serialize.MessageSerialize;

public class JsonMessageSerialize implements MessageSerialize {

    private final static ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object object) throws Exception {
        return mapper.writeValueAsBytes(object);
    }

    @Override
    public Object unserialize(String className, byte[] bytes) throws Exception {
        Class<?> cls = Class.forName(className);
        return mapper.readValue(new String(bytes), cls);
    }
}

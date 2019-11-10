package pers.chxuan.jrpc;

import com.google.protobuf.Message;
import pers.chxuan.jrpc.serialize.MessageSerialize;

import java.lang.reflect.Method;

public class ProtoBufferMessageSerialize implements MessageSerialize {

    @Override
    public byte[] serialize(Object object) {
        Message message = (Message) object;
        return message.toByteArray();
    }

    @Override
    public Object unserialize(String className, byte[] bytes) throws Exception {
        Class<?> cls = Class.forName(className);
        Method method = cls.getDeclaredMethod("parseFrom", byte[].class);
        return method.invoke(null, bytes);
    }
}

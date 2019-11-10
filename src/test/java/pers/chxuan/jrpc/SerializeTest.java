package pers.chxuan.jrpc;

import com.google.protobuf.Message;
import pers.chxuan.jrpc.protobuf.java.TestProto;
import pers.chxuan.jrpc.serialize.MessageSerialize;

public class SerializeTest {

    public static void main(String[] args) throws Exception {
        TestProto.RequestLogin.Builder builder = TestProto.RequestLogin.newBuilder();

        builder.setUsername("chxuan");
        builder.setPassword("123456");

        Message message = builder.build();

        System.out.println(message);

        MessageSerialize messageSerialize = new ProtoBufferMessageSerialize();
        byte[] bytes = messageSerialize.serialize(message);
        Message msg = (Message) messageSerialize.unserialize(message.getClass().getName(), bytes);
        System.out.println(msg);
    }
}

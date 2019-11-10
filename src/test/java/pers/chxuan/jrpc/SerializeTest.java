package pers.chxuan.jrpc;

import com.google.protobuf.Message;
import pers.chxuan.jrpc.java.serialize.JavaMessageSerialize;
import pers.chxuan.jrpc.json.serialize.JsonMessageSerialize;
import pers.chxuan.jrpc.proto.serialize.ProtoBufferMessageSerialize;
import pers.chxuan.jrpc.proto.protobuf.java.TestProto;
import pers.chxuan.jrpc.serialize.MessageSerialize;

public class SerializeTest {

    public static void main(String[] args) throws Exception {
        protobufSerializeTest();
        javaSerializeTest();
        jsonSerializeTest();
    }

    private static void protobufSerializeTest() throws Exception {
        System.out.println("===============protobuffer序列化测试============");

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

    private static void javaSerializeTest() throws Exception {
        System.out.println("===============java序列化测试============");

        pers.chxuan.jrpc.java.entity.RequestLogin message = new pers.chxuan.jrpc.java.entity.RequestLogin();

        message.setUsername("chxuan");
        message.setPassword("123456");
        System.out.println(message);

        MessageSerialize messageSerialize = new JavaMessageSerialize();
        byte[] bytes = messageSerialize.serialize(message);
        pers.chxuan.jrpc.java.entity.RequestLogin msg = (pers.chxuan.jrpc.java.entity.RequestLogin) messageSerialize.unserialize(null, bytes);

        System.out.println(msg + "\n");
    }

    private static void jsonSerializeTest() throws Exception {
        System.out.println("===============json序列化测试============");

        pers.chxuan.jrpc.json.entity.RequestLogin message = new pers.chxuan.jrpc.json.entity.RequestLogin();

        message.setUsername("chxuan");
        message.setPassword("123456");
        System.out.println(message);

        MessageSerialize messageSerialize = new JsonMessageSerialize();
        byte[] bytes = messageSerialize.serialize(message);
        System.out.println(new String(bytes));

        pers.chxuan.jrpc.json.entity.RequestLogin msg = (pers.chxuan.jrpc.json.entity.RequestLogin) messageSerialize.unserialize(message.getClass().getName(), bytes);
        System.out.println(msg);
    }
}

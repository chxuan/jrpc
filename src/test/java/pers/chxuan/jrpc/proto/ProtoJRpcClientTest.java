package pers.chxuan.jrpc.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.proto.protobuf.java.TestProto;
import pers.chxuan.jrpc.proto.serialize.ProtoBufferMessageSerialize;
import pers.chxuan.jrpc.rpc.JRpcClient;

public class ProtoJRpcClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoJRpcClientTest.class);

    public static void main(String[] args) {
        JRpcClient rpcClient = new JRpcClient("127.0.0.1", 9999, new ProtoBufferMessageSerialize());

        TestProto.RequestLogin.Builder builder = TestProto.RequestLogin.newBuilder();
        builder.setUsername("chxuan");
        builder.setPassword("123456");

        TestProto.RequestLogin resp = (TestProto.RequestLogin) rpcClient.send(builder.build());
        if (resp != null) {
            LOGGER.info("返回消息:{}", resp);
        } else {
            LOGGER.info("返回消息为空");
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package pers.chxuan.jrpc.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.proto.protobuf.java.TestProto;
import pers.chxuan.jrpc.proto.serialize.ProtoBufferMessageSerialize;
import pers.chxuan.jrpc.rpc.JRpcClient;

public class ProtoJRpcClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtoJRpcClientTest.class);

    public static void main(String[] args) {
        JRpcClient rpcClient = new JRpcClient(new ProtoBufferMessageSerialize());

        if (rpcClient.connect("127.0.0.1", 9999)) {
            TestProto.RequestLogin.Builder builder = TestProto.RequestLogin.newBuilder();
            builder.setUsername("chxuan");
            builder.setPassword("123456");

            TestProto.RequestLogin resp = (TestProto.RequestLogin) rpcClient.send(builder.build());
            if (resp != null) {
                System.out.println(resp);
            } else {
                System.out.println("返回消息为空");
            }
        } else {
            LOGGER.info("连接失败");
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

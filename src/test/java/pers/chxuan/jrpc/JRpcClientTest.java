package pers.chxuan.jrpc;

import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.entity.NetworkMessage;
import pers.chxuan.jrpc.protobuf.java.TestProto;
import pers.chxuan.jrpc.rpc.JRpcClient;

public class JRpcClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JRpcClientTest.class);

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

package pers.chxuan.jrpc.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.json.entity.RequestLogin;
import pers.chxuan.jrpc.json.serialize.JsonMessageSerialize;
import pers.chxuan.jrpc.rpc.JRpcClient;

public class JsonJRpcClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonJRpcClientTest.class);

    public static void main(String[] args) {
        JRpcClient rpcClient = new JRpcClient(new JsonMessageSerialize());

        if (rpcClient.connect("127.0.0.1", 9999)) {
            RequestLogin message = new RequestLogin();
            message.setUsername("chxuan");
            message.setPassword("123456");

            RequestLogin resp = (RequestLogin) rpcClient.send(message);
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

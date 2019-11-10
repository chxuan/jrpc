package pers.chxuan.jrpc.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.json.entity.RequestLogin;
import pers.chxuan.jrpc.json.serialize.JsonMessageSerialize;
import pers.chxuan.jrpc.rpc.JRpcClientPool;

public class JsonJRpcClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonJRpcClientTest.class);

    public static void main(String[] args) {
        JRpcClientPool rpcClient = new JRpcClientPool("127.0.0.1", 9999, new JsonMessageSerialize());

        RequestLogin message = new RequestLogin();
        message.setUsername("chxuan");
        message.setPassword("123456");

        RequestLogin resp = (RequestLogin) rpcClient.send(message);
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

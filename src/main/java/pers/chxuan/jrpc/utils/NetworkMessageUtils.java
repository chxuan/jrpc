package pers.chxuan.jrpc.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.chxuan.jrpc.entity.NetworkMessage;
import pers.chxuan.jrpc.serialize.MessageSerialize;

import java.util.concurrent.atomic.AtomicInteger;

public class NetworkMessageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkMessageUtils.class);

    private static final int FIXED_LENGHT = 4 + 4 + 4 + 4;

    private static final AtomicInteger serial = new AtomicInteger(0);

    public static NetworkMessage toNetworkMessage(MessageSerialize messageSerialize, Object object) {
        try {
            NetworkMessage message = new NetworkMessage();

            message.setSerial(serial.getAndIncrement());

            String name = object.getClass().getName();
            message.setNameLength(name.length());
            message.setName(name);

            byte[] content = messageSerialize.serialize(object);
            message.setContentLength(content.length);
            message.setContent(content);

            int totalLenght = FIXED_LENGHT + name.length() + content.length;
            message.setTotalLength(totalLenght);

            return message;
        } catch (Exception e) {
            LOGGER.info("序列化消息失败,name:{},exception:{}", object.getClass().getName(), e.getMessage());
        }

        return null;
    }

    public static Object toUserObject(MessageSerialize messageSerialize, NetworkMessage message) {
        try {
            return messageSerialize.unserialize(message.getName(), message.getContent());
        } catch (Exception e) {
            LOGGER.info("反序列化消息失败,name:{},exception:{}", message.getName(), e.getMessage());
        }

        return null;
    }
}

package pers.chxuan.jrpc.java.serialize;

import pers.chxuan.jrpc.serialize.MessageSerialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class JavaMessageSerialize implements MessageSerialize {

    @Override
    public byte[] serialize(Object object) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);

        byte[] bytes = bos.toByteArray();

        oos.close();
        bos.close();

        return bytes;
    }

    @Override
    public Object unserialize(String className, byte[] bytes) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object object = ois.readObject();

        ois.close();
        bais.close();

        return object;
    }
}

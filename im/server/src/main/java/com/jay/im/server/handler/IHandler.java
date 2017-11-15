package com.jay.im.server.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class IHandler {

    public void handlerRequest(String methodName, byte[] bytes, SocketChannel channel, long luid) throws IOException,
            ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException{

        Method method = this.getClass().getMethod(methodName, byte[].class, SocketChannel.class);
        method.invoke(bytes, channel, luid);
    }

    public void writeChannel(byte[] data, SocketChannel channel) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(data);
        channel.write(buffer);
    }

    public byte[] serizlize(Object obj) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(obj);
        out.close();
        return bos.toByteArray();
    }

    public <T> T deserialize(byte[] datas, Class<T> type)
            throws IOException, ClassNotFoundException {
        if (datas == null) {
            return null;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(datas);
        ObjectInputStream ois = new ObjectInputStream(bis);
        T result = (T) ois.readObject();
        ois.close();
        bis.close();
        return result;
    }
}

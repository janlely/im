package com.jay.im.client.handler;

import com.jay.im.api.protocol.PExport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class IHandler {

    public byte[] serizlize(Object obj) throws IOException {
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

    public abstract void handler(SocketChannel channel) throws IOException, ClassNotFoundException;

    public void writeChannel(byte[] data, SocketChannel channel) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(data);
        channel.write(buffer);
    }

    public <T> T waitForResponse(long luid, Class<T> type, UnPacker unPacker) throws IOException, ClassNotFoundException {
        long curTime = System.currentTimeMillis();
        while(!unPacker.have(luid) && System.currentTimeMillis() - curTime < 1000){
        }
        if(unPacker.have(luid)){
            PExport export = deserialize(unPacker.fetch(luid), PExport.class);
            T response = deserialize(export.getData(), type);
            unPacker.rem(luid);
            return response;
        }else{
            unPacker.rem(luid);
            return null;
        }
    }
}

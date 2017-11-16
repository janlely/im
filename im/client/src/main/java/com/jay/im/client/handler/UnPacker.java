package com.jay.im.client.handler;

import com.jay.im.api.protocol.PExport;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service("unPacker")
public class UnPacker extends IHandler {

    private Set<Long> timer;

    private Map<Long, byte[]> floor;

    public UnPacker(){
        timer = new HashSet<>();
        floor = new HashMap<>();
    }

    public boolean have(long luid){
        return floor.containsKey(luid);
    }


    public byte[] fetch(long luid){
        byte[] result = floor.get(luid);
        floor.remove(luid);
        return result;
    }

    public void reg(long luid){
        timer.add(luid);
    }

    public void rem(long luid){
        timer.remove(luid);
    }

    public void unpack(byte[] data) throws IOException, ClassNotFoundException {
        PExport export = deserialize(data, PExport.class);
        long luid = export.getLuid();
        System.out.println("got data, luid: " + luid);
        if(timer.contains(luid)){
            floor.put(luid, export.getData());
        }
    }

    @Override
    public void handler(SocketChannel channel) throws IOException, ClassNotFoundException {

    }
}

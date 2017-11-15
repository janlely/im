package com.jay.im.client.handler;

import com.jay.im.api.protocol.PExport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service("unPacker")
public class UnPacker extends IHandler {

    private Map<Long, Long> timer;

    private Map<Long, byte[]> floor;

    public UnPacker(){
        timer = new HashMap<>();
        floor = new HashMap<>();
    }

    public boolean have(long luid){
        return floor.containsKey(luid);
    }


    public byte[] get(long luid){
        return floor.get(luid);
    }

    public void reg(long luid, long deadtime){
        timer.put(luid, deadtime);
    }

    public void getDeadtime(long luid){
        timer.get(luid);
    }

    public void unpack(byte[] data) throws IOException, ClassNotFoundException {
        PExport export = deserialize(data, PExport.class);
        long luid = export.getLuid();
        if(timer.containsKey(luid)){
            floor.put(luid, export.getData());
        }
    }
}

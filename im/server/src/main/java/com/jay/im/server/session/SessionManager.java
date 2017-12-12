package com.jay.im.server.session;

import org.springframework.stereotype.Service;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

@Service
public class SessionManager {

    private Map<String, SocketChannel> sessionMap1;

    private Map<SocketChannel, String> sessionMap2;

    public SessionManager(){
        sessionMap1 = new HashMap<>();
        sessionMap2 = new HashMap<>();
    }

    public void put(String username, SocketChannel channel){
        sessionMap1.put(username, channel);
        sessionMap2.put(channel, username);
    }

    public void clear(SocketChannel channel){
        if(sessionMap2.containsKey(channel)){
            String username = sessionMap2.get(channel);
            if(sessionMap1.containsKey(username)){
                sessionMap1.remove(username);
            }
            sessionMap2.remove(channel);
        }
    }
}

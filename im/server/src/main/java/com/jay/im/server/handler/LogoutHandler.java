package com.jay.im.server.handler;

import com.jay.im.server.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.channels.SocketChannel;

@Service
public class LogoutHandler extends IHandler{

    @Autowired
    private SessionManager sessionManager;

    public void logout(SocketChannel channel) throws IOException {
        sessionManager.clear(channel);
        channel.close();
    }
}

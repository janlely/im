package com.jay.im.server.handler;

import com.jay.im.api.common.ErrorCode;
import com.jay.im.api.protocol.PExport;
import com.jay.im.api.protocol.PLogout;
import com.jay.im.api.protocol.PLogout.Response;
import com.jay.im.server.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.channels.SocketChannel;

@Service
public class LogoutHandler extends IHandler{

    @Autowired
    private SessionManager sessionManager;

    public void logout(byte[] data, SocketChannel channel, long luid) throws IOException {
        sessionManager.clear(channel);
        PExport export = new PExport();
        export.setLuid(luid);
        PLogout.Response res = new Response();
        res.setCode(ErrorCode.OK);
        export.setData(serizlize(res));
        writeChannel(serizlize(export), channel);
    }
}

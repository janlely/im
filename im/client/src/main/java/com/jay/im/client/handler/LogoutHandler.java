package com.jay.im.client.handler;

import com.jay.im.api.common.ErrorCode;
import com.jay.im.api.common.MethodNames;
import com.jay.im.api.common.ModuleNames;
import com.jay.im.api.protocol.PEntrance;
import com.jay.im.api.protocol.PLogin;
import com.jay.im.api.protocol.PLogout;
import com.jay.im.api.protocol.PLogout.Response;
import com.jay.im.client.util.LUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.channels.SocketChannel;

@Service
public class LogoutHandler extends IHandler{

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandler.class);

    @Autowired
    private UnPacker unPacker;

    @Override
    public void handler(SocketChannel channel) throws IOException, ClassNotFoundException {
        long luid = LUID.getLuid();
        PEntrance entrance = new PEntrance();
        entrance.setLuid(luid);
        entrance.setModuleName(ModuleNames.LOGOUT);
        entrance.setMethodName(MethodNames.LOGOUT_LOGOUT);
        writeChannel(serizlize(entrance), channel);
        PLogout.Response res= waitForResponse(luid, PLogout.Response.class, unPacker);
        if(res.getCode() != ErrorCode.OK){
            System.out.println("logout error");
            return;
        }
        System.out.println("logout success");
    }
}

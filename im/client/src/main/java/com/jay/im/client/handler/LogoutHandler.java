package com.jay.im.client.handler;

import com.jay.im.api.common.ErrorCode;
import com.jay.im.api.common.MethodNames;
import com.jay.im.api.common.ModuleNames;
import com.jay.im.api.protocol.PEntrance;
import com.jay.im.api.protocol.PLogout;
import com.jay.im.client.util.LUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class LogoutHandler extends IHandler{

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandler.class);

    @Autowired
    private UnPacker unpacker;

    @Override
    public void handler(SocketChannel channel) throws IOException, ClassNotFoundException {
        long luid = LUID.getLuid();
        PEntrance entrance = new PEntrance();
        entrance.setLuid(luid);
        entrance.setModuleName(ModuleNames.LOGOUT);
        entrance.setMethodName(MethodNames.LOGOUT_LOGOUT);
        writeChannel(serizlize(entrance), channel);
        channel.close();
        LOG.info("logou success");
    }
}

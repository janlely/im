package com.jay.im.client.handler;

import com.jay.im.api.common.ErrorCode;
import com.jay.im.api.common.MethodNames;
import com.jay.im.api.common.ModuleNames;
import com.jay.im.api.protocol.PEntrance;
import com.jay.im.api.protocol.PLogin;
import com.jay.im.api.protocol.PLogin.Request;
import com.jay.im.api.protocol.PLogin.Response;
import com.jay.im.client.util.LUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

@Service
public class LoginHandler extends IHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHandler.class);

    @Autowired
    private UnPacker unPacker;

    @Override
    public void handler(SocketChannel channel) throws IOException, ClassNotFoundException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username:");
        String username = sc.nextLine();
        System.out.println("Enter your password:");
        String password = sc.nextLine();
        PLogin.Response res = login(username, password, channel);
        if(res.getCode() != ErrorCode.OK){
            System.out.println(res.getDesc());
            return;
        }
        System.out.println("login success");
    }

    private Response login(String username, String password, SocketChannel channel)
        throws IOException, ClassNotFoundException {
        PEntrance entrance = new PEntrance();
        long luid = LUID.getLuid();
        entrance.setLuid(luid);
        PLogin.Request req = new Request();
        req.setUsername(username);
        req.setPassword(password);
        entrance.setModuleName(ModuleNames.LOGIN);
        entrance.setMethodName(MethodNames.LOGIN_LOGIN);
        entrance.setData(serizlize(req));
        writeChannel(serizlize(entrance), channel);
        return waitForResponse(luid, PLogin.Response.class, unPacker);
    }
}

package com.jay.im.client.handler;

import com.jay.im.api.common.ErrorCode;
import com.jay.im.api.common.MethodNames;
import com.jay.im.api.common.ModuleNames;
import com.jay.im.api.protocol.PEntrance;
import com.jay.im.api.protocol.PRegister;
import com.jay.im.client.util.LUID;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.Timer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterHandler extends IHandler {

    @Autowired
    private UnPacker unPacker;

    @Override
    public void handler(SocketChannel channel) throws IOException, ClassNotFoundException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new username:");
        String username = sc.nextLine();
        PRegister.Response res = checkUserName(username, channel);
        if(res == null){
            System.out.println("network error!");
            return;
        }
        if(res.getCode() != ErrorCode.OK){
            System.out.println(res.getDesc());
            return;
        }

        System.out.println("Enter password:");
        String password1 = sc.nextLine();
        System.out.println("Repeate password:");
        String password2 = sc.nextLine();
        while(!StringUtils.equals(password1, password2)){
            System.out.println("the two password differ, please enter aggin!");
            System.out.println("Enter password:");
            password1 = sc.nextLine();
            System.out.println("Repeate password:");
            password2 = sc.nextLine();
        }
        System.out.println("registering...");
        res = register(username, password1, channel);
        if(res == null){
            System.out.println("network error!");
            return;
        }
        if(res.getCode() != ErrorCode.OK){
            System.out.println(res.getDesc());
            return;
        }
        System.out.println("register success!");

    }

    private PRegister.Response register(String username, String password1, SocketChannel channel) throws IOException, ClassNotFoundException {
        PEntrance entrance = new PEntrance();
        long luid = LUID.getLuid();
        entrance.setLuid(luid);
        PRegister.RegisterRequest registerRequest = new PRegister.RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setPassword(password1);
        entrance.setData(serizlize(registerRequest));
        writeChannel(serizlize(entrance), channel);

        return waitForResponse(luid, PRegister.Response.class, unPacker);

    }


    private PRegister.Response checkUserName(String username, SocketChannel channel) throws IOException,
    ClassNotFoundException {
        PEntrance entrance = new PEntrance();
        PRegister.CheckUserRequest checkUserRequest = new PRegister.CheckUserRequest();
        checkUserRequest.setUsername(username);
        long luid = LUID.getLuid();
        entrance.setLuid(luid);
        entrance.setData(serizlize(checkUserRequest));
        entrance.setModuleName(ModuleNames.REGISTER);
        entrance.setMethodName(MethodNames.REGISTER_CHECKUSER);
        writeChannel(serizlize(entrance), channel);
        unPacker.reg(luid);
        return waitForResponse(luid, PRegister.Response.class, unPacker);
    }

}

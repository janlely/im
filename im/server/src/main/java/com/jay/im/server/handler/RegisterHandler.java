package com.jay.im.server.handler;

import com.jay.im.api.common.ErrorCode;
import com.jay.im.api.protocol.PExport;
import com.jay.im.api.protocol.PRegister;
import com.jay.im.server.dao.mybatis.RegisterMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.channels.SocketChannel;

@Service
public class RegisterHandler extends IHandler{

    @Autowired
    private RegisterMybatis registerMybatis;


    public void register(byte[] data, SocketChannel channel, long luid) throws IOException, ClassNotFoundException {
        PRegister.RegisterRequest reg = this.deserialize(data, PRegister.RegisterRequest.class);
        String username = reg.getUsername();
        String password = reg.getPassword();
        int userCount = registerMybatis.checkUserName(username);
        PRegister.Response response = new PRegister.Response();
        if(userCount != 0){
            response.setCode(ErrorCode.USER_EXISTED);
            response.setDesc("user existed");
        }else{
            registerMybatis.addUser(username, password);
            response.setCode(ErrorCode.REGISTER_SUCCESS);
            response.setDesc("register success");
        }
        PExport export = new PExport();
        export.setLuid(luid);
        export.setData(serizlize(response));
        writeChannel(serizlize(export), channel);
    }

    public void checkUser(byte[] data, SocketChannel channel, long luid) throws IOException, ClassNotFoundException {
        PRegister.CheckUserRequest checkUserRequest = this.deserialize(data, PRegister.CheckUserRequest.class);
        String username = checkUserRequest.getUsername();
        int userCount = registerMybatis.checkUserName(username);
        PRegister.Response response = new PRegister.Response();
        if(userCount != 0){
            response.setCode(ErrorCode.USER_EXISTED);
            response.setDesc("user existed");
        }else{
            response.setCode(ErrorCode.OK);
        }
        PExport export = new PExport();
        export.setLuid(luid);
        export.setData(serizlize(response));
        writeChannel(serizlize(export), channel);
    }

}

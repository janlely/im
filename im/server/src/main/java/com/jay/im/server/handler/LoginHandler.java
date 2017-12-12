package com.jay.im.server.handler;

import com.jay.im.api.common.ErrorCode;
import com.jay.im.api.protocol.PExport;
import com.jay.im.api.protocol.PLogin;
import com.jay.im.server.dao.mybatis.LoginMybatis;
import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.jay.im.server.session.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginHandler extends IHandler {

    @Autowired
    private LoginMybatis loginMybatis;

    @Autowired
    private SessionManager sessonManager;

    private void login(byte[] data, SocketChannel channel, long luid) throws IOException, ClassNotFoundException {
        PLogin.Request request = deserialize(data, PLogin.Request.class);
        String username = request.getUsername();
        String password = request.getPassword();
        String passwordDB = loginMybatis.getUserPass(username);
        PLogin.Response response = new PLogin.Response();
        if(passwordDB == null){
            response.setCode(ErrorCode.USER_NOT_REGISTERED);
            response.setDesc("user not registered!");
        }else if(!passwordValid(password, passwordDB)){
            response.setCode(ErrorCode.WRONG_PASSWORD);
            response.setDesc("wrong passowrd!");
        }else{
            response.setCode(ErrorCode.LOGIN_SUCCESS);
            response.setDesc("login success!");
        }
        PExport export = new PExport();
        export.setData(serizlize(response));
        export.setLuid(luid);
        //add session
        sessonManager.put(username, channel);
        writeChannel(serizlize(export), channel);
    }

    private boolean passwordValid(String password, String passwordDB) {
        return StringUtils.equals(password, passwordDB);
    }
}

package com.jay.im.server.dao.mybatis;

import org.apache.ibatis.annotations.Param;

public interface RegisterMybatis {

    int checkUserName(@Param("username") String username);

    void addUser(@Param("username") String username, @Param("password") String password);
}

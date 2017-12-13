package com.jay.im.server.dao.mybatis;

import org.apache.ibatis.annotations.Param;

public interface LoginMybatis {
    String getUserPass(@Param("username") String username);
}

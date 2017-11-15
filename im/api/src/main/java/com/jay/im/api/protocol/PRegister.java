/*
 * Project: nio
 * 
 * File Created at 17-9-30
 * 
 * Copyright 2016 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.jay.im.api.protocol;

import java.io.Serializable;

/**
 * @Type PRegister.java
 * @Desc
 * @author jay
 * @date 17-9-30 下午3:27
 * @version
 */
public class PRegister {

    public static class RegisterRequest {

        private String username;

        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class CheckUserRequest{
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
    public static class Response{
        private int code;

        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
/**
 * Revision history
 * -------------------------------------------------------------------------
 *
 * Date Author Note
 * -------------------------------------------------------------------------
 * 17-9-30 jay create
 */

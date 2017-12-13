package com.jay.im.api.protocol;

import java.io.Serializable;

public class PLogout implements Serializable{

    public static class Response implements  Serializable{

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

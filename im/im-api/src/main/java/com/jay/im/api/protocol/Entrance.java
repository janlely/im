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
package java.com.jay.im.api.protocol;

import java.io.Serializable;

/**
 * @Type Entrance.java
 * @Desc
 * @author jay
 * @date 17-9-30 下午3:35
 * @version
 */
public class Entrance implements Serializable{

    public class Request{

        private String className;

        private String methodName;

        private String argClassName;

        private byte[] data;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getArgClassName() {
            return argClassName;
        }

        public void setArgClassName(String argClassName) {
            this.argClassName = argClassName;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }

    public class Response{

        private int code;

        private byte[] data;

        private String className;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
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

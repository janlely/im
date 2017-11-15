package com.jay.im.api.protocol;

public class PEntrance {

    private long luid;

    public long getLuid() {
        return luid;
    }

    public void setLuid(long luid) {
        this.luid = luid;
    }

    private String moduleName;

    private String methodName;

    private byte[] data;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}

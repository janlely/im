package com.jay.im.api.protocol;

public class PExport {

    private long luid;

    private byte[] data;

    public long getLuid() {
        return luid;
    }

    public void setLuid(long luid) {
        this.luid = luid;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

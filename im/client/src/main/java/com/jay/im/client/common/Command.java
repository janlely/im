package com.jay.im.client.common;

public class Command {

    private String name;

    public Command(String cmd){
        this.name = cmd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

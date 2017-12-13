package com.jay.im.client.common;

import com.jay.im.api.common.ModuleNames;
import com.jay.im.client.handler.LoginHandler;
import com.jay.im.client.handler.LogoutHandler;
import com.jay.im.client.handler.RegisterHandler;
import java.util.HashMap;
import java.util.Map;

public class HandlerContainer {
    private static Map<String, Class> map;

    static {
        map = new HashMap<>();
        map.put(ModuleNames.REGISTER, RegisterHandler.class);
        map.put(ModuleNames.LOGIN, LoginHandler.class);
        map.put(ModuleNames.LOGOUT, LogoutHandler.class);
    }

    public static Map<String, Class> getMap() {
        return map;
    }

    public static Class getClass(String name){
        return map.get(name);
    }

}

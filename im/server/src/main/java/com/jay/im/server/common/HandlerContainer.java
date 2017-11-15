package com.jay.im.server.common;

import com.jay.im.api.common.ModuleNames;
import com.jay.im.server.handler.LoginHandler;
import com.jay.im.server.handler.RegisterHandler;
import java.util.HashMap;
import java.util.Map;

public class HandlerContainer {
    private static Map<String, Class> map;

    static {
        map = new HashMap<>();
        map.put(ModuleNames.REGISTER, RegisterHandler.class);
        map.put(ModuleNames.LOGIN, LoginHandler.class);
    }

    public static Map<String, Class> getMap() {
        return map;
    }

    public static Class getClass(String name){
        return map.get(name);
    }

}

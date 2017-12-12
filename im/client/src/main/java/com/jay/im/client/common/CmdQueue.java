package com.jay.im.client.common;

import java.util.ArrayDeque;
import java.util.Queue;

public class CmdQueue {

    private static Queue<Command> queue = new ArrayDeque<>();

    public static void push(Command cmd){
        queue.add(cmd);
    }

    public static Command pop(){
        return queue.remove();
    }

    public static boolean isEmpty(){
        return queue.isEmpty();
    }


}

package com.jay.im.client.common;

import java.util.Stack;

public class CmdStack {

    private static Stack<Command> stack = new Stack<>();

    public static void push(Command cmd){
        stack.push(cmd);
    }

    public static Command pop(){
        return stack.pop();
    }

    public static boolean isEmpty(){
        return stack.isEmpty();
    }
}

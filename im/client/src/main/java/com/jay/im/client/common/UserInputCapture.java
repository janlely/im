package com.jay.im.client.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class UserInputCapture {

    public static void start(){
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter a commond(try with 'help'): ");
                String command = sc.nextLine();
                if(StringUtils.equals("help", command)){
                    showHelp();
                }else{
                    Class aClass = HandlerContainer.getClass(command);
                    if(aClass == null){
                        System.out.println("unrecgnized command");
                    }else{
                        CmdQueue.push(new Command(command));
                    }
                }
            }
        };
        new Thread(runnable).start();
    }

    private static void showHelp() {
        System.out.println(" register : open register process");
        System.out.println(" login : open login process");
        System.out.println(" help : show help info");
    }

}

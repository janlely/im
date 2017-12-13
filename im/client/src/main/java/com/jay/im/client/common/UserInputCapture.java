package com.jay.im.client.common;

import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;
import java.util.concurrent.BlockingDeque;

public class UserInputCapture {

    private static volatile boolean blocked = false;
    public static void start(){
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                while (true){
                    if(!blocked){
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
                                blocked = true;
                                CmdQueue.push(new Command(command));
                            }
                        }
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

    public static void unBlock(){
        blocked = false;
    }
}

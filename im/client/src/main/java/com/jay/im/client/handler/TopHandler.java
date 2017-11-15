package com.jay.im.client.handler;

import com.jay.im.client.common.HandlerContainer;
import com.jay.im.client.context.SpringContextHolder;
import com.sun.org.apache.xalan.internal.xsltc.dom.MultiValuedNodeHeapIterator;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import org.springframework.stereotype.Service;

@Service("topHandler")
public class TopHandler extends IHandler {

    @Override
    public void handler(SocketChannel channel) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter you operation:");
        String operation = sc.nextLine();
        handler(operation, channel);
    }

    public void handler(String operation, SocketChannel channel) throws IOException, ClassNotFoundException {
        IHandler handler = (IHandler) SpringContextHolder.getSingleton(HandlerContainer.getClass(operation));
        handler.handler(channel);
    }
}

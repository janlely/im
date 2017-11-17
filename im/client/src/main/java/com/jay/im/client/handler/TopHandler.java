package com.jay.im.client.handler;

import com.jay.im.client.common.CmdStack;
import com.jay.im.client.common.Command;
import com.jay.im.client.common.HandlerContainer;
import com.jay.im.client.context.SpringContextHolder;
import com.sun.org.apache.xalan.internal.xsltc.dom.MultiValuedNodeHeapIterator;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("topHandler")
public class TopHandler extends IHandler {


    private static final Logger LOG = LoggerFactory.getLogger(TopHandler.class);

    private ExecutorService executor;

    public TopHandler(){
        executor = Executors.newFixedThreadPool(10);
    }

    @Override
    public void handler(SocketChannel channel) throws IOException, ClassNotFoundException {
        Command cmd = CmdStack.pop();
        String operation = cmd.getName();
        handler(operation, channel);
    }

    public void handler(final String operation, final SocketChannel channel) throws IOException, ClassNotFoundException {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                IHandler handler = (IHandler) SpringContextHolder.getSingleton(HandlerContainer.getClass(operation));
                try {
                    handler.handler(channel);
                } catch (Exception e) {
                    LOG.error("error handler operation: {}", operation, e);
                }
            }
        });
    }
}

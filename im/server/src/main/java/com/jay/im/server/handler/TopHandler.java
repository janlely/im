/*
 * Project: nio
 * 
 * File Created at 17-9-30
 * 
 * Copyright 2016 CMCC Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * ZYHY Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license.
 */
package com.jay.im.server.handler;

import com.jay.im.api.protocol.PEntrance;
import com.jay.im.server.common.HandlerContainer;
import com.jay.im.server.context.SpringContextHolder;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Type TopHandler.java
 * @Desc
 * @author jay
 * @date 17-9-30 下午3:42
 * @version
 */
@Service("topHandler")
public class TopHandler extends IHandler{

    private static final Logger LOG = LoggerFactory.getLogger(TopHandler.class);

    private ExecutorService executor;

    @Autowired
    private RegisterHandler registerHandler;

    public TopHandler(){
        executor = Executors.newFixedThreadPool(10);
    }

    public void handlerRequest(final byte[] data, final SocketChannel channel) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    PEntrance entrance = deserialize(data, PEntrance.class);
                    String moduleName = entrance.getModuleName();
                    String methodName = entrance.getMethodName();
                    long luid = entrance.getLuid();
                    byte[] bytes = entrance.getData();
                    IHandler handler = (IHandler) SpringContextHolder.getSingleton(HandlerContainer.getClass(moduleName));
                    handler.handlerRequest(methodName, bytes, channel, luid);
                } catch (Exception e) {
                    LOG.error("error handling request", e);
                }
            }
        });
    }

}
/**
 * Revision history
 * -------------------------------------------------------------------------
 *
 * Date Author Note
 * -------------------------------------------------------------------------
 * 17-9-30 jay create
 */

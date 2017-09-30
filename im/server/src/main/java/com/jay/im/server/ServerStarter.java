/*
 * Project: server
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
package com.jay.im.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Type ServerStarter.java
 * @Desc
 * @author jay
 * @date 17-9-30 下午2:36
 * @version
 */
public class ServerStarter {

    private static final Logger LOG = LoggerFactory.getLogger(ServerStarter.class);

    private Selector selector;
    private InetSocketAddress listenAddress;


    public ServerStarter(String host, int port) {
        listenAddress = new InetSocketAddress(host, port);
    }

    public static void main(String[] args) {
        if(args.length != 2){
            System.out.println("Usage: ./IMServer <host> <port>");
        }
        final String host = args[0];
        final String port = args[1];

        Runnable server = new Runnable() {
            @Override
            public void run() {
                try {
                    new ServerStarter(host, Integer.parseInt(port)).startServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        new Thread(server).start();
    }

    private void startServer() throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        serverChannel.socket().bind(listenAddress);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        //spring config
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");

        LOG.info("Server Started");

        while (true){
            selector.select();

            Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
            while (keys.hasNext()) {

                SelectionKey key = keys.next();

                keys.remove();
                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    this.accept(key);
                }
                else if (key.isReadable()) {
                    this.read(key);
                }
            }
        }
    }

    private void read(SelectionKey key) throws IOException {

        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int readBytes = channel.read(buffer);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while(readBytes != -1){
            byte[] data = new byte[readBytes];
            bos.write(data, 0, readBytes);
        }

    }

    private void accept(SelectionKey key) throws IOException {

        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        LOG.info("new connection from: {}", remoteAddr);

        channel.register(this.selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
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

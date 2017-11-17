package com.jay.im.client;

import com.jay.im.client.common.CmdStack;
import com.jay.im.client.context.SpringContextHolder;
import com.jay.im.client.handler.TopHandler;
import com.jay.im.client.handler.UnPacker;
import com.jay.im.client.util.LUID;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientStarter {

    private Selector selector;
    private InetSocketAddress socketAddress;

    public ClientStarter(String host, int port){
        this.socketAddress = new InetSocketAddress(host, port);
    }
    public static void main(String args[]){

        if(args.length != 2){
            System.out.println("Usage: ./IMClient <host> <port>");
        }
        final String host = args[0];
        final String port = args[1];

        Runnable client = new Runnable() {
            @Override
            public void run() {
                try {
                    new ClientStarter(host, Integer.parseInt(port)).startClient();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(client, "client-A").start();
    }

    private void startClient() throws IOException, ClassNotFoundException {

        this.selector = Selector.open();
        SocketChannel client = SocketChannel.open(socketAddress);
        client.configureBlocking(false);
        client.register(this.selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);

        //spring config
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        context.start();
        LUID.setLuid(System.currentTimeMillis());
        System.out.println("Client Started");

        while(true){
            this.selector.select();
            Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();

                keys.remove();

                if (!key.isValid()) {
                    continue;
                }

                else if (key.isReadable()) {
                    this.read(key);

                }else if(key.isWritable()){
                    this.write(key);
                }
            }
        }
    }

    private void write(SelectionKey key) throws IOException, ClassNotFoundException {
        TopHandler topHandler = SpringContextHolder.getBean("topHandler");
        SocketChannel channel = (SocketChannel) key.channel();
        if(!CmdStack.isEmpty()){
            topHandler.handler(channel);
        }
    }

    private void read(SelectionKey key) throws IOException, ClassNotFoundException {
        UnPacker unPacker = SpringContextHolder.getBean("unPacker");
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int readBytes = channel.read(buffer);
        buffer.flip();
        while(readBytes > 0){
            bos.write(buffer.array(), 0, readBytes);
            readBytes = channel.read(buffer);
            buffer.flip();
        }

        unPacker.unpack(bos.toByteArray());
    }
}

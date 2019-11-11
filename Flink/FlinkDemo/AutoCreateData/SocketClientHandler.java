package com.guoshuai.mtdap3.socketsource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description 处理客户端的数据通信
 * Created with guoshuai
 * date 2019/8/2 13:42
 */
public class SocketClientHandler extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(SocketClientHandler.class);

    Socket client;
    String ip;

    BufferedReader reader;
    PrintWriter writer;

    public SocketClientHandler(Socket socket) {
        this.ip = socket.getInetAddress()
                .getHostAddress();
        try {
            this.client = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            logger.error("init socket client error,{}", e.getMessage());
        }
    }

    /**
     * {@inheritDoc}<br>
     *
     */
    @Override
    public void run() {
        while (!client.isClosed()) {
            try {
                String msg = reader.readLine();
                if (msg == null) {
                    break;
                }
                receiveMessage(msg);
            } catch (IOException e) {
                logger.info(e.getMessage());
                break;
            }
        }
    }

    /**
     * 收到消息之后的处理
     *
     * @param msg
     *            收到的消息
     */
    public void receiveMessage(String msg) {
        logger.info("from:{},receive:{}", ip, msg);
    }

    /**
     * 发送消息
     *
     * @param msg
     *            发送的消息
     */
    public void sendMessage(String msg) {
        logger.info("to:{},send:{}", ip, msg);
        writer.println(msg);
    }


}

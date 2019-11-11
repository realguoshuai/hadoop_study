package com.guoshuai.mtdap3.socketsource;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description  socker 服务端
 * Created with guoshuai
 * date 2019/8/2 13:40
 */
public class SocketServer extends TimerTask {
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    ServerSocket serverSocket;

    private List<SocketClientHandler> clients = new ArrayList<>();

    /**
     * 开启监听，接受链接
     */
    public SocketServer(Integer port) {
        // 清理客户端
        new Timer(true).schedule(this, 1000, 1000);
        try {
            serverSocket = new ServerSocket(port);
            logger.info("服务端已启动，等待客户端连接..");
            new Thread(() -> {
                while (!serverSocket.isClosed()) {
                    try {
                        Socket socket = serverSocket.accept();
                        String clientIP = socket.getInetAddress()
                                .getHostAddress();

                        logger.info("client:{}", clientIP);

                        SocketClientHandler handler = new SocketClientHandler(socket);
                        handler.start();

                        clients.add(handler);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}<br>
     * 用于清理已经断开的客户端
     *
     */
    @Override
    public void run() {
        Iterator<SocketClientHandler> it = clients.iterator();
        while (it.hasNext()) {
            SocketClientHandler handler = it.next();
            if (!handler.isAlive()) {
                it.remove();
            }
        }
    }

    /**
     * 往客户端发送消息
     *
     * @param msg
     *            消息
     */
    public void sendMessage(String msg) {
        //logger.info("send:{}", msg);
        clients.stream()
                .forEach(client -> client.sendMessage(msg));
    }


}

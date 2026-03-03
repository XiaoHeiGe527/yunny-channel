package com.yunny.channel.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 小黑哥
 * @data 2024/3/16 18:31
 */
public class TcpServer2 {
    public static void main(String[] args) throws Exception {
        System.out.println("服务端启动 , 等待连接 .... ");
        // 1.创建 ServerSocket对象，绑定端口，开始等待连接
        ServerSocket ss = new ServerSocket(6666);
        // 2.接收连接 accept 方法, 返回 socket 对象.
        Socket server = ss.accept();
        // 3.通过socket 获取输入流
        InputStream is = server.getInputStream();
        // 4.一次性读取数据
        // 4.1 创建字节数组
        byte[] b = new byte[1024];
        // 4.2 据读取到字节数组中.
        int len = is.read(b);
        // 4.3 解析数组,打印字符串信息
        String msg = new String(b, 0, len);
        System.out.println(msg);
        //5.关闭资源.
        is.close();
        server.close();


    }

}

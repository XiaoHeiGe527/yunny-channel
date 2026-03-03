package com.yunny.channel.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLOutput;

/**
 * @author 小黑哥
 * @data 2024/3/16 18:12
 */
public class UDPReceDemo  implements Runnable {

    private DatagramSocket ds;


    public UDPReceDemo(DatagramSocket ds){

        this.ds =ds;
    }

    @Override
    public void run() {

        try {

            while (true){
                byte[] buf =  new byte[1024];
                DatagramPacket dp = new DatagramPacket(buf,buf.length);
                ds.receive(dp);//阻塞式的

                String ip = dp.getAddress().getHostAddress();
                int port = dp.getPort();
                String text = new String(dp.getData(),0,dp.getLength());
                System.out.println(ip+":"+port+":"+text);

                if("886".equals(text)){
                    System.out.println(ip+":"+port+":退出聊天室");
                    break;
                }
            }

            ds.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

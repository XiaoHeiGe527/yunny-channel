package com.yunny.channel.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * @author 小黑哥
 * @data 2024/3/16 17:35
 */
public class UDPSendDemo implements Runnable {


    private DatagramSocket ds;

    public UDPSendDemo(DatagramSocket ds) {
        this.ds = ds;
    }

    @Override
    public void run() {
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((System.in)));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {

                byte[] buf = line.getBytes();
                DatagramPacket dp = new DatagramPacket(buf, buf.length, InetAddress.getByName("192.168.0.6"), 10000);
                ds.send(dp);


                if ("over".equals(line)) {

                    System.out.println("消息发送停止");
                    break;
                }

            }

            ds.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

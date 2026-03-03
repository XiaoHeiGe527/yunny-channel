package com.yunny.channel.util;

import com.yunny.channel.common.entity.UserInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.io.File;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 小黑哥
 * @data 2024/3/14 20:12
 */
@Slf4j
public class TestUtil {


    @Lazy
    @Bean(name = "userInfoDO")
    public UserInfoDO getUserInfoDO(){

        UserInfoDO userInfoDO = new UserInfoDO();
        userInfoDO.setUserName("小黑哥");
        return userInfoDO;
    }


    @Test
    public void test() throws UnknownHostException, SocketException {

//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestUtil.class);
//         log.info("初始化容器成功！");
//         log.info("获取bean");
//        UserInfoDO userInfoDO = (UserInfoDO) applicationContext.getBean("userInfoDO");
//         log.info("从容器中取出userInfoDO");
//         log.info(userInfoDO.getUserName());

        // InetAddress ip = InetAddress.getLocalHost();
//        InetAddress ip = InetAddress.getByName("127.0.0.1");
//
//        //BF-202304220056
//
//        log.info("Address:[{}]",ip.getHostAddress());
//        log.info("hostName:[{}]",ip.getHostName());
//        DatagramSocket send = new DatagramSocket();
//        DatagramSocket rece = new DatagramSocket(10001);
//
//
//        new Thread(new UDPSendDemo(send)).start();
//        new Thread(new UDPReceDemo(rece)).start();


        List<Integer> testList = new ArrayList<>();
        testList.add(5);
        testList.add(9);
        testList.add(6);
        testList.add(1);
        // 降序排序
        //list.stream().sorted(Comparator.reverseOrder())
        Collections.sort(testList, Collections.reverseOrder());

        //正序排序Collections.sort(testList);;
        // 使用 Stream 排序 sorted(Comparator.comparing(Student::getAge))
        testList = testList.stream().sorted().collect(Collectors.toList());


        // 正序
        testList.sort(Comparator.comparing(Integer::intValue));

        // 倒序
        testList.sort(Comparator.comparing(Integer::intValue).reversed());

        // 打印 list 集合
        testList.forEach(p -> {
            System.out.println(p);
        });



    }


    public static void main(String[] args) throws SocketException {

//        DatagramSocket send = new DatagramSocket();
//        DatagramSocket rece = new DatagramSocket(10000);
//
//        new Thread(new UDPSendDemo(send)).start();
//        new Thread(new UDPReceDemo(rece)).start();

        int[] arr = {4, 3, 2, 5, 1, 93, 8};

        for (int i = 0; i < arr.length - 1; i++) {

            for (int j = 0; j < arr.length - 1 - i; j++) {

                if (arr[j] > arr[j + 1]) {

                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }

            }
        }

        for (int a : arr) {

            System.out.println(a);
        }


        File f = new File("D:\\BaiduNetdiskDownload\\ps2emu\\bios");
        TestUtil.traverseDirectory(f,0);

    }


    /**
     * 递归遍历目录树
     * @param file 当前处理的文件或目录
     * @param depth 当前深度，用于缩进显示
     */
    public static void traverseDirectory(File file, int depth) {
        // 基准情形：文件不存在或为 null
        if (file == null || !file.exists()) {
            return;
        }

        // 打印当前文件/目录，使用缩进表示层级
        printWithIndent(file.getName(), depth);

        // 如果是目录，递归遍历其内容
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    traverseDirectory(child, depth + 1);
                }
            }
        }
    }

    /**
     * 打印带有缩进的内容
     * @param name 要打印的名称
     * @param depth 缩进深度
     */
    private static void printWithIndent(String name, int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  "); // 两个空格表示一个缩进层级
        }
        System.out.println(indent + name);
    }

}

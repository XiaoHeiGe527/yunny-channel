package com.yunny.channel.common.util;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
	/**
	 * 获取上传文件的md5
	 * @param file
	 * @return
	 */
	public static String getMd5(MultipartFile file) {
		try {
			//获取文件的byte信息
			byte[] uploadBytes = file.getBytes();
			// 拿到一个MD5转换器
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(uploadBytes);
			//转换为16进制
			return new BigInteger(1, digest).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getFileMd5(File file) {
		if (file == null || !file.isFile()) {
			return null;
		}
		MessageDigest digest;
		FileInputStream in;
		byte[] buffer = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (IOException | NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

}

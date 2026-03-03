package com.yunny.channel.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author Administrator
 * 
 */
public class NoUtil {

	public static void main(String[] args){
		System.out.println("生成的涂层码为:" + getCoatingCode());
	}


	/**
	 * 生成分配虚机时的 user Password
	 * @return
	 */
	public static String getVmPassword(){
		// 随机生成总长度
		int totalLength = getLength(10,12);
		// 随机生成大写字母个数
		int upperLength = getLength(2,3);
		// 随机生成数字个数
		int numLength = getLength(3,4);
		// 小写字母个数
		int lowerLength = totalLength - upperLength - numLength;
		// 存最后生成的字符数组
		char[] totalChars = new char[totalLength];
		// 生成位置列表
		List<Integer> positionList = getPositionList(totalLength);
		// 生成大写字母字符数组
		char[] upperChars = getStringRandom(upperLength,2);
		// 大写字母占位置
		setPosition(totalChars,upperChars,positionList);
		// 生成数字字符数组
		char[] numChars = getStringRandom(numLength,0);
		// 数字占位置
		setPosition(totalChars,numChars,positionList);
		// 小写字母字符个数不为0则生成小写字母字符数组且占位置
		if(lowerLength > 0){
			char[] lowerChars = getStringRandom(lowerLength,1);
			setPosition(totalChars,lowerChars,positionList);
		}
		return String.valueOf(totalChars);
	}



	/**
	 * 生成涂层码
	 * @return
	 */
	public static String getCoatingCode(){
		// 随机生成总长度
		int totalLength = getLength(10,12);
		// 随机生成大写字母个数
		int upperLength = getLength(2,4);
		// 随机生成数字个数
		int numLength = getLength(3,6);
		// 小写字母个数
		int lowerLength = totalLength - upperLength - numLength;
		// 存最后生成的字符数组
		char[] totalChars = new char[totalLength];
		// 生成位置列表
		List<Integer> positionList = getPositionList(totalLength);
		// 生成大写字母字符数组
		char[] upperChars = getStringRandom(upperLength,2);
		// 大写字母占位置
		setPosition(totalChars,upperChars,positionList);
		// 生成数字字符数组
		char[] numChars = getStringRandom(numLength,0);
		// 数字占位置
		setPosition(totalChars,numChars,positionList);
		// 小写字母字符个数不为0则生成小写字母字符数组且占位置
		if(lowerLength > 0){
			char[] lowerChars = getStringRandom(lowerLength,1);
			setPosition(totalChars,lowerChars,positionList);
		}
		return String.valueOf(totalChars);
	}

	/**
	 * 生成范围值
	 * @param start
	 * @param end
	 * @return
	 */
	public static int getLength(int start,int end){
		Random random = new Random();
		return random.nextInt(end - start + 1) + start;
	}

	/**
	 * 设定位置
	 * @param totalChars
	 * @param chars
	 * @param positionList
	 */
	public static void setPosition(char[] totalChars, char[] chars, List<Integer> positionList){
		for(char temp : chars){
			// 随机取位置列表index
			Random positionRandom = new Random();
			int position = positionRandom.nextInt(positionList.size());
			// 位置列表value代表位置
			totalChars[positionList.get(position)] = temp;
			// 移除位置列表index元素  代表位置被使用
			positionList.remove(position);
		}
	}

	/**
	 * 生成位置列表
	 * @param length
	 * @return
	 */
	public static List<Integer> getPositionList(int length){
		List<Integer> positionList = new ArrayList<>(length);
		for(int i = 0;i < length;i++){
			positionList.add(i);
		}
		return positionList;
	}

	/**
	 * 生成length长度的随机字符数组 type类型固定 意思是要么全是数字要么全是小写字母等等
	 * type=0 字母
	 * type=1 小写字母
	 * type=2 大写字母
	 * @param length
	 * @param type
	 * @return
	 */
	public static char[] getStringRandom(int length,int type){
		char[] chars = new char[length];
		//参数length，表示生成几位随机数
		int i = 0;
		if(type == 0){
			while(true){
				Random random = new Random();
				char c = (char) (random.nextInt(10) + 48);
				if(c == '0'){
					continue;
				}
				chars[i] = c;
				i++;
				if(i == length){
					break;
				}
			}
		} else if(type == 1){
			while (true){
				Random random = new Random();
				char c = (char)(random.nextInt(26) + 97);
				if(c == 'l'){
					continue;
				}
				chars[i] = c;
				i++;
				if(i == length){
					break;
				}
			}
		}else{
			while (true){
				Random random = new Random();
				char c = (char)(random.nextInt(26) + 65);
				if(c == 'O' || c == 'I'){
					continue;
				}
				chars[i] = c;
				i++;
				if(i == length){
					break;
				}
			}
		}
		return chars;
	}

	/**
	 * 获取指定长度的随机大小写英文数字组合
	 * @param length
	 * @return
	 */
	public static String getStringRandom(int length) {

		String val = "";
		Random random = new Random();

		//参数length，表示生成几位随机数
		while(true){
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if( "char".equalsIgnoreCase(charOrNum) ) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				char c = (char)(random.nextInt(26) + temp);
				if(c=='i' || c=='o' || c=='l'){
					continue;
				}
				val += c;
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				int n = random.nextInt(10);
				if(n==0 || n==1){
					continue;
				}
				val += String.valueOf(n);
			}
			if(val.length()==length){
				break;
			}
		}
		return val;
	}

	public static String getNumberRandom(int len){
		Random r = new Random();
		int bitNum=1;
		for(int i=0;i<len;i++){
			bitNum=bitNum*10;
		}
		long num = Math.abs(r.nextLong() % (bitNum));
		String s = String.valueOf(num);
		for (int i = len - s.length(); i >0 ;i--) {
			s = "0" + s;
		}
		if(s.length()>len){
			s=s.substring(0,len);
		}
		return s;
	}

	/**
	 * 随机会员卡号
	 * @return
	 */
	public static String getCardNo() {
		//00yyyymmddHHmmss0001
		java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String oid = getStringRandom(4);
		return "C" +df2.format(now) + oid;
	}

	/**
	 * 时间卡订单号
	 * @return
	 */
	public static String getTimeOrderNo() {
		//00yyyymmddHHmmss0001
		java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String oid = getStringRandom(4);
		return "T" +df2.format(now) + oid;
	}

	/**
	 * 扣费单单号
	 * @return
	 */
	public static String getCostOrderNo() {
		//00yyyymmddHHmmss0001
		java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String oid = getStringRandom(4);
		return "K" +df2.format(now) + oid;
	}

	public static String getCrystalOrderNo() {
		//00yyyymmddHHmmss0001
		java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String oid = getStringRandom(4);
		return "O" +df2.format(now) + oid;
	}

	/**
	 *文件groupID
	 * @return
	 */
	public static String getGroupId() {
		//00yyyymmddHHmmss0001
		java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String oid = getStringRandom(4);
		return "GID" +df2.format(now) + oid;
	}

	/**
	 * 随机会员名
	 * @return
	 */
	public static String getCardName() {
		String oid = getStringRandom(6);
		return "Yun" + oid;
	}

	/**
	 * 随机虚拟机用户名
	 * @return
	 */
	public static String getVmUserName() {
		String oid = getStringRandom(6);
		return "Vm" + oid;
	}

	public static String getBusmachineid() {
		String oid = getStringRandom(32);
		return oid;
	}

	/**
	 * 短信验证码
	 * @return
	 */
	public static String getMobileCode() {

		return getNumberRandom(6);

	}

	/**
	 * 生成sessionid
	 * @return
	 */
	public static String getSessionId() {
		//00yyyymmddHHmmss0001
		java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String oid = getStringRandom(4);
		return "SI" +df2.format(now) + oid;
	}

	/**
	 * 生成sessionToken
	 * @return
	 */
	public static String getSessionToken() {
		//00yyyymmddHHmmss0001
		java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String oid = getStringRandom(4);
		return "ST" +df2.format(now) + oid;
	}

	/**
	 * 随机活动编号
	 * @return
	 */
	public static String getActivityno() {
		//00yyyymmddHHmmss0001
		java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String oid = getStringRandom(4);
		return "A" +df2.format(now) + oid;
	}


	/**
	 * 随机批次号
	 * @return
	 */
	public static String getBatchNo() {
		//00yyyymmddHHmmss0001
		java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String oid = getStringRandom(4);

		String batchNo  = "pch" +df2.format(now) + oid+"XH";

		return batchNo+NoUtil.getCoatingCode();
	}
}

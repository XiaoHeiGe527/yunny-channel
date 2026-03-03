package com.yunny.channel.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查控申请单审核信息
 * 
 * @author zhangke
 *
 */
@Data
@TableName("zjck_shxx")
public class ZjckShxx implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* ID
	*/
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 申请单号
	 */
	private String sqdh;
		
	/**
	 * 案件编号
	 */
	private String ajbh;
		
	/**
	 * 审核状态:0驳回,1同意
	 */
	private Integer state;
		
	/**
	 * 审核人警号
	 */
	private String jycode;
		
	/**
	 * 审核人姓名
	 */
	private String jyname;
		
	/**
	 * 审核人手机号
	 */
	private String longMobile;
		
	/**
	 * 审核意见
	 */
	private String shyj;
		
	/**
	 * 审核人部门代码
	 */
	private String bmcode;
		
	/**
	 * 审核人所属部门
	 */
	private String ssbm;
		
	/**
	 * 审核人身份证号
	 */
	private String userNumber;
		
	/**
	 * 审核时间
	 */
	private Date createTime;

	/**
	 * 审核方式 1自动审核
	 */
	private String shfs;
		

}

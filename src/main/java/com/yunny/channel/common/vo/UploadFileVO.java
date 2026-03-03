package com.yunny.channel.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @Description
* @CreateDate 2019/12/30 15:41
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileVO implements Serializable {

	private String url;
}

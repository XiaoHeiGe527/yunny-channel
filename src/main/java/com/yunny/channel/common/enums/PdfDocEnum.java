package com.yunny.channel.common.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * PDF文档的题材类型
 * */
@Getter
public class PdfDocEnum {

    @Getter
    public enum DocTypeEnum{
        WFSB("unknown","无法识别","无法识别"),
        LAJDS("LAJDS","立案决定书","立案决定书"),
        XZCXTZS("XZCXTZS","协助查询财产通知书","协助查询财产通知书"),
        XZDJTZS("XZDJTZS","协助冻结财产通知书","冻结");
        /**
         * 标识文书体裁
         * */
        private String code;
        /**
         * 文书的中文标题
         * */
        private String title;
        /**搜索关键字判断文档体裁*/
        private String keyword;
        DocTypeEnum(String code,String title,String keyword){
            this.code=code;
            this.title=title;
            this.keyword=keyword;
        }

        public static DocTypeEnum getInstance(String code){
            DocTypeEnum docTypeEnum= Arrays.stream(values()).filter(item->item.code.contains(code)).findAny().orElse(WFSB);
            return docTypeEnum;
        }
    }

}

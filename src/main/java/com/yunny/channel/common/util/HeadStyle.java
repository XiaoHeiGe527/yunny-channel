package com.yunny.channel.common.util;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.Data;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class HeadStyle {
    public HorizontalCellStyleStrategy getHeadStyle() {
        //1 表头样式策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //表头前景设置白色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.AUTOMATIC.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setBold(true);
        headWriteFont.setFontName("Calibri");
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteCellStyle.setWriteFont(headWriteFont);
        List<WriteCellStyle> listCntWritCellSty =  new ArrayList<>();
        return  new HorizontalCellStyleStrategy(headWriteCellStyle, listCntWritCellSty);
    }
}

package com.yunny.channel.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.util.Matrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.List;

@Slf4j
public class PdfUtil {

    // 中文字体路径（需将字体文件放入 resources/fonts 目录）
    private static final String CHINESE_FONT_PATH = "fonts/simhei.ttf"; // 黑体字体路径

    /**
     * 给 PDF 添加透明旋转水印（单行文本，不换行）
     * @param sourcePath    原始 PDF 路径
     * @param watermarkText 水印文本（单行显示）
     * @param outputPath    输出路径（可为 null，自动生成）
     * @return 处理后的 PDF 路径，失败返回 null
     */
    public static String addWatermark(String sourcePath, String watermarkText, String outputPath) {
        if (!FileUtil.exist(sourcePath)) {
            log.error("原始 PDF 不存在: {}", sourcePath);
            return null;
        }

        outputPath = StrUtil.isBlank(outputPath)
                ? sourcePath.replace(".pdf", "_watermark.pdf")
                : outputPath;

        try (PDDocument document = PDDocument.load(new File(sourcePath));
             InputStream fontStream = PdfUtil.class.getClassLoader()
                     .getResourceAsStream(CHINESE_FONT_PATH)) {

            if (fontStream == null) {
                log.error("字体文件未找到: {}", CHINESE_FONT_PATH);
                return null;
            }

            PDType0Font font = PDType0Font.load(document, fontStream, true);

            for (PDPage page : document.getPages()) {
                float pageWidth = page.getMediaBox().getWidth();
                float pageHeight = page.getMediaBox().getHeight();

                try (PDPageContentStream contentStream = new PDPageContentStream(
                        document, page,
                        PDPageContentStream.AppendMode.APPEND, // 确保水印在顶层
                        true, true
                )) {
                    contentStream.saveGraphicsState();

                    // 设置透明度
                    PDExtendedGraphicsState gs = new PDExtendedGraphicsState();
                    gs.setNonStrokingAlphaConstant(0.5f); // 50%不透明度
                    contentStream.setGraphicsStateParameters(gs);

                    // 设置水印颜色（红色）
                    contentStream.setNonStrokingColor(Color.RED);
                    contentStream.setFont(font, 20f); // 字体大小
                    // 计算文本宽度
                    float textWidth = font.getStringWidth(watermarkText) * 20f / 1000;

                    // 水印间隔
                    float xInterval = textWidth * 3.5f; // 水平间隔
                    float yInterval = 80; // 垂直间隔

                    // 计算行列数
                    int rows = (int) Math.ceil(pageHeight / yInterval) + 1;
                    int cols = (int) Math.ceil(pageWidth / xInterval) + 1;

                    // 循环绘制水印（交错排列）
                    for (int row = 0; row < rows; row++) {
                        // 偶数行添加水平偏移，实现交错
                        float xOffset = (row % 2 == 0) ? 0 : xInterval / 2;

                        for (int col = 0; col < cols; col++) {
                            float x = col * xInterval + xOffset;
                            float y = row * yInterval;

                            contentStream.saveGraphicsState();
                            contentStream.transform(Matrix.getTranslateInstance(x, y));
                            contentStream.transform(Matrix.getRotateInstance(Math.toRadians(45), 0, 0));

                            contentStream.beginText();
                            contentStream.moveTextPositionByAmount(-textWidth/2, 0); // 水平居中
                            contentStream.drawString(watermarkText);
                            contentStream.endText();

                            contentStream.restoreGraphicsState();
                        }
                    }

                    contentStream.restoreGraphicsState();
                }
            }

            document.save(outputPath);
            log.info("透明水印添加成功: {}", outputPath);
            return outputPath;

        } catch (Exception e) {
            log.error("添加水印失败: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * pdf转图片（分页多图）
     *
     * @param pdfPath pdf文件路径
     * @param imageDir 图片保存目录（为null时，默认保存到pdf所在目录)
     * @return
     */
    public static List<String> converter(String pdfPath, String imageDir) {
        List<String> imageList = CollUtil.toList();
        if(!FileUtil.exist(pdfPath)){ return imageList;}

        if(pdfPath.endsWith(".pdf")||pdfPath.endsWith(".PDF")){
            // PDF文件路径
            File pdfFile = new File(pdfPath);
            String pdfName =  StrUtil.subPre(pdfFile.getName(), pdfFile.getName().lastIndexOf("."));
            // 输出图片保存的目录
            imageDir = StrUtil.isBlank(imageDir) ? pdfFile.getParent().replace("\\", "/") : imageDir;
            FileUtil.mkdir(imageDir);

            try (PDDocument document = PDDocument.load(pdfFile)) {
                // 创建PDF渲染器
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                // 遍历PDF的每一页并转换为图片
                for (int page = 0; page < document.getNumberOfPages(); ++page) {
                    // 构建输出图片的文件名
                    String imagePath = imageDir + "/" + pdfName+"_"+(page + 1) + ".png";
                    if(!FileUtil.exist(imagePath)){
                        BufferedImage image = pdfRenderer.renderImageWithDPI(page, 80); // DPI可调整
                        // 将图片写入文件
                        File outputfile = new File(imagePath);
                        ImageIO.write(image, "PNG", outputfile);
                    }
                    imageList.add(imagePath);
                }
            } catch (Exception e) {
                log.error(">>>>> PDF转图片失败", e);
            }
        }

        return imageList;
    }
}
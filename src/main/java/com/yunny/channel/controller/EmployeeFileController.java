package com.yunny.channel.controller;


import com.google.gson.Gson;
import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.dto.EmployeeFileDTO;
import com.yunny.channel.common.dto.SystemLogDTO;
import com.yunny.channel.common.enums.SystemLogTypeEnum;
import com.yunny.channel.common.interfaces.UpdateGroup;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.aspect.lock.RedisLock;
import com.yunny.channel.common.util.IPUtils;
import com.yunny.channel.common.vo.EmployeeFileVO;
import com.yunny.channel.common.query.EmployeeFileQuery;
import com.yunny.channel.common.vo.SystemUserVO;
import com.yunny.channel.service.EmployeeFileService;
import com.yunny.channel.service.SystemLogService;
import com.yunny.channel.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * Created by fe
 */
@Slf4j
@RestController
@RequestMapping("/employeeFile")
public class EmployeeFileController {

    @Resource
    private EmployeeFileService employeeFileService;


    @Resource
    private SystemLogService systemLogService;

    @Autowired
    private SystemUserService systemUserService;



    /**
     * 更改员工档案PDF
     *
     * @param file
     * @return
     */
    @RedisLock
    @PostMapping("/replaceUpload")
    public BaseResult replaceUpload(MultipartFile file,
                                       @RequestParam("id") Integer id,
            @RequestAttribute("userNo") String userNo,
                                    HttpServletRequest httpServletRequest) {
        // 文件为空校验
        if (file == null || file.isEmpty()) {
            return BaseResult.failure(HttpURLConnection.HTTP_SERVER_ERROR, "请上传文件");
        }

        // 文件类型校验
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(".pdf") && !originalFilename.endsWith(".PDF")) {
            return BaseResult.failure(HttpURLConnection.HTTP_SERVER_ERROR, "仅支持PDF文件上传");
        }

        // 文件大小校验（1GB限制）
        long fileSize = file.getSize();
        long maxSize = 1024 * 1024 * 1024; // 1GB
        if (fileSize > maxSize) {
            return BaseResult.failure(HttpURLConnection.HTTP_SERVER_ERROR, "文件大小不能超过1GB");
        }

        // 业务处理
        BaseResult result = employeeFileService.replaceUpload(file, id);

        SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
        if (result.getCode() == ExceptionConstants.RESULT_CODE_SUCCESS) {
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.ADD.getCode())
                    .content("用户:["+userNo+"]"+"更改员工文件的记录ID["+id+"],"+file.getOriginalFilename())
                    .userNo(userNo)
                    .url("/employeeFile/replaceUpload")
                    .createTime(LocalDateTime.now())
                    .remarks("用户:["+userNo+"]"+"更改员工文件的记录ID["+id+"]" )
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .build();
            systemLogService.insertSelective(systemLogDTO);

            return BaseResult.success();
        }

        return result;

    }


    /**
     * 员工档案录入（PDF文件上传FTP）
     *
     * @param file
     * @param employeeCode
     * @param name
     * @param department
     * @param rank
     * @param profession
     * @param positionCode
     * @param state
     * @param remarks
     * @return
     */
    @RedisLock
    @PostMapping("/handleFileUpload")
    public BaseResult handleFileUpload(MultipartFile file,
                                       @RequestParam("employeeCode") String employeeCode,
                                       @RequestParam("name") String name,
                                       @RequestParam("department") String department,
                                       @RequestParam("rank") String rank,
                                       @RequestParam("profession") String profession,
                                       @RequestParam("positionCode") String positionCode,
                                       @RequestParam("state") Integer state,
                                       @RequestParam("remarks") String remarks,@RequestAttribute("userNo") String userNo,
                                       HttpServletRequest httpServletRequest) {
        // 文件为空校验
        if (file == null || file.isEmpty()) {
            return BaseResult.failure(HttpURLConnection.HTTP_SERVER_ERROR, "请上传文件");
        }

        // 文件类型校验
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(".pdf") && !originalFilename.endsWith(".PDF")) {
            return BaseResult.failure(HttpURLConnection.HTTP_SERVER_ERROR, "仅支持PDF文件上传");
        }

        // 文件大小校验（1GB限制）
        long fileSize = file.getSize();
        long maxSize = 1024 * 1024 * 1024; // 1GB
        if (fileSize > maxSize) {
            return BaseResult.failure(HttpURLConnection.HTTP_SERVER_ERROR, "文件大小不能超过1GB");
        }

        EmployeeFileQuery query =   EmployeeFileQuery.builder()
                .employeeCode(employeeCode)
                .name(name)
                .department(department)
                .rank(rank)
                .profession(profession)
                .positionCode(positionCode)
                .state(state)
                .remarks(remarks)
                .pdfFileUrl(file.getOriginalFilename())
                .updateMan(userNo)
                .build();

        // 业务处理
        BaseResult result = employeeFileService.handleFileUpload(file, query);
        SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
        if (result.getCode() == ExceptionConstants.RESULT_CODE_SUCCESS) {
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.ADD.getCode())
                    .content(this.queryToJSON(query))
                    .userNo(userNo)
                    .url("/employeeFile/handleFileUpload")
                    .createTime(LocalDateTime.now())
                    .remarks("新增上传人员信息记录:[" + 1 + "]条，员工编号：[" + query.getEmployeeCode() + "]")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .build();
            systemLogService.insertSelective(systemLogDTO);

            return BaseResult.success();
        }

        return result;

    }


    @RequestMapping("/listByPage")
    public BaseResult<CommonPager<EmployeeFileVO>> listByPage(@RequestBody EmployeeFileQuery query) {
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));
        CommonPager<EmployeeFileVO> commonPager = employeeFileService.listByPage(query);
        return BaseResult.success(commonPager);
    }



    /**
     * 添加水印并下载接口  @RedisLock 分布式锁 锁方法，后期加令牌同也行
     */
    @RedisLock
    @PostMapping("/addWatermarkAndDownload")
    public void addWatermarkAndDownload(
            @RequestParam("remoteFileName") String remoteFileName,
            @RequestAttribute("userNo") String userNo,
            HttpServletResponse response,
            HttpServletRequest httpServletRequest
    ) {

       String watermarkText = "文件下载者ID["+userNo+"]";
        try {
            // 设置响应头
            String encodedFileName = URLEncoder.encode(
                    "watermark_" + remoteFileName,
                    "UTF-8"
            );
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + encodedFileName + "\""
            );

            // 调用服务层处理水印并输出流
          boolean result =  employeeFileService.addWatermarkAndDownload(
                    remoteFileName,
                    watermarkText,
                    response.getOutputStream()
            );

            if (result) {
                String ip = IPUtils.getIpAddr(httpServletRequest);
                SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
                SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                        .operationType(SystemLogTypeEnum.DOWNLOAD.getCode())
                        .content("下载的带水印文件名称是:[" + remoteFileName + "]")
                        .userNo(userNo)
                        .url("/employeeFile/addWatermarkAndDownload")
                        .createTime(LocalDateTime.now())
                        .remarks("下载员工信息PDF带水印文件[" + remoteFileName + "]，操作的员工编号：[" + userNo + "]")
                        .ip(ip)
                        .operatorName(systemUserVO.getName())
                        .build();
                systemLogService.insertSelective(systemLogDTO);
            }

        } catch (IOException e) {
            log.error("响应设置失败: {}", e.getMessage(), e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "操作失败");
            } catch (IOException ex) {
                log.error("错误响应失败", ex);
            }
        }
    }



    /**
     * 下载文件原件
     *
     * @param remoteFileName 远程文件名
     * @param response       HttpServletResponse
     */
    @RedisLock
    @PostMapping("/downloadFile")
    public void downloadFile(
            @RequestParam(value = "remoteFileName") String remoteFileName,
            HttpServletResponse response,
            HttpServletRequest httpServletRequest,
            @RequestAttribute("userNo") String userNo
    ) {
        try {
            // 设置响应头
            response.setContentType("application/pdf");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=\"" + java.net.URLEncoder.encode(remoteFileName, "UTF-8") + "\"");

            // 调用服务层方法直接写入响应流
            BaseResult result =  employeeFileService.downloadFile(remoteFileName, response);

            if (result.getCode() == ExceptionConstants.RESULT_CODE_SUCCESS) {
                String ip = IPUtils.getIpAddr(httpServletRequest);
                SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
                SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                        .operationType(SystemLogTypeEnum.DOWNLOAD.getCode())
                        .content("下载的文件名称是:[" + remoteFileName + "]")
                        .userNo(userNo)
                        .url("/employeeFile/downloadFile")
                        .createTime(LocalDateTime.now())
                        .remarks("下载员工信息PDF原件[" + remoteFileName + "]，操作的员工编号：[" + userNo + "]")
                        .ip(ip)
                        .operatorName(systemUserVO.getName())
                        .build();
                systemLogService.insertSelective(systemLogDTO);
            }


        } catch (IOException e) {
            log.error("下载文件失败: {}", e.getMessage(), e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "下载文件失败");
            } catch (IOException ex) {
                log.error("设置错误响应失败", ex);
            }
        }
    }

    @PostMapping("/employeeFileEdit")
    public BaseResult update(@RequestBody @Validated({UpdateGroup.class}) EmployeeFileDTO employeeFileDto
            , @RequestAttribute("userNo") String userNo, HttpServletRequest httpServletRequest) {
        employeeFileDto.setUpdateMan(userNo);
        employeeFileDto.setUpdateTime(LocalDateTime.now());
     int count = employeeFileService.updateSelective(employeeFileDto);

        if (count == 1) {
            String ip = IPUtils.getIpAddr(httpServletRequest);
            SystemUserVO systemUserVO = systemUserService.getByUserNo(userNo);
            SystemLogDTO systemLogDTO = SystemLogDTO.builder()
                    .operationType(SystemLogTypeEnum.UPDATE.getCode())
                    .content(this.toJSON(employeeFileDto))
                    .userNo(userNo)
                    .url("/employeeFile/employeeFileEdit")
                    .createTime(LocalDateTime.now())
                    .remarks("编辑人员信息记录:[" + count + "]条，记录ID是：[" + employeeFileDto.getId() + "]")
                    .ip(ip)
                    .operatorName(systemUserVO.getName())
                    .build();
            systemLogService.insertSelective(systemLogDTO);

            return BaseResult.success();
        }


        return BaseResult.success();
    }


    @PostMapping("/getById")
    public BaseResult getById(@RequestParam("id") Integer id) {
        return BaseResult.success(employeeFileService.getById(id));
    }




    @PostMapping("/getNameCount")
    public BaseResult getNameCount(@RequestParam("name") String name) {
        EmployeeFileQuery query = EmployeeFileQuery.builder().name(name)
                .build();
        return BaseResult.success(employeeFileService.getnameCount(query));
    }





    private String toJSON(EmployeeFileDTO employeeFileDto) {
        Gson gson = new Gson();
        return gson.toJson(employeeFileDto);
    }

    private String queryToJSON(  EmployeeFileQuery query){
        Gson gson = new Gson();
        return gson.toJson(query);
    }

}

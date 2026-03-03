package com.yunny.channel.controller.view;

import com.google.gson.Gson;
import com.yunny.channel.common.query.DictionaryQuery;
import com.yunny.channel.common.vo.ChemicalFileVO;
import com.yunny.channel.common.vo.DictionaryVO;
import com.yunny.channel.common.vo.EmployeeFileVO;
import com.yunny.channel.service.ChemicalFileService;
import com.yunny.channel.service.DictionaryService;
import com.yunny.channel.service.EmployeeFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName RenShiViewJumpController
 * @Author sunfuwei521@qq.com
 * @Date 2025/5/14 15:31
 */
@Validated
@RestController
@RequestMapping("/renShiViewJump")
@Slf4j
public class RenShiViewJumpController {

    @Resource
    private EmployeeFileService employeeFileService;

    @Resource
    private DictionaryService dictionaryService;

    @Resource
    private ChemicalFileService chemicalFileService;

    /**
     * 跳转到员工信息录入页面
     */
    @PostMapping("/employeeFile/handleFileUpload")
    public ModelAndView employeeFileHandleFileUpload(Model model,@RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]");
        model.addAttribute("userNo", userNo);
        return new ModelAndView("renshi/employeeFileAdd");
    }

    /**
     * 跳转员工信息编辑界面
     * @param model
     * @param userNo
     * @param id
     * @return
     */
    @PostMapping("/employeeFile/employeeFileEdit")
    public ModelAndView employeeFileUpdate(Model model, @RequestAttribute("userNo") String userNo,
                                           @RequestParam("id") String id) {
        EmployeeFileVO employeeFile = employeeFileService.getById(Integer.parseInt(id));

        // 查询部门和职级数据（直接传递列表对象，而非JSON字符串）
        List<DictionaryVO> departments = dictionaryService.listByQuery(DictionaryQuery.builder().category("部门").build());
        List<DictionaryVO> ranks = dictionaryService.listByQuery(DictionaryQuery.builder().category("职级").build());

        // 传递列表对象到模板
        model.addAttribute("departments", departments); // 注意这里变量名改为departments
        model.addAttribute("ranks", ranks);
        model.addAttribute("employeeFile", employeeFile);
        model.addAttribute("userNo", userNo);

        return new ModelAndView("renshi/employeeFileEdit");
    }


    /**
     * 跳转员工信息列表界面
     * @param model
     * @param userNo
     * @return
     */
    @PostMapping("/employeeFile/employeeFileListPage")
    public ModelAndView employeeFileListPage(Model model,@RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]");
        model.addAttribute("userNo", userNo);
        return new ModelAndView("renshi/employeeFileListPage");
    }


    /**
     * 跳转员工档案重新上传页面
     * @param model
     * @param userNo
     * @param id
     * @return
     */
    @PostMapping("/employeeFile/replaceUpload")
    public ModelAndView employeeFilereplaceUpload(Model model, @RequestAttribute("userNo") String userNo,
                                                  @RequestParam("id") String id) {
        EmployeeFileVO employeeFile = employeeFileService.getById(Integer.parseInt(id));
        // 传递列表对象到模板
        model.addAttribute("employeeFile", employeeFile);
        model.addAttribute("userNo", userNo);

        return new ModelAndView("renshi/employeeFilereplaceUpload");
    }


//--------------------------------------------------------------------------------------------------------
    /**
     * 跳转到化工文件录入页面
     */
    @PostMapping("/chemicalFile/handleFileUpload")
    public ModelAndView  chemicalFileHandleFileUpload(Model model,@RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]");
        model.addAttribute("userNo", userNo);
        return new ModelAndView("renshi/chemicalFileAdd");
    }


    /**
     * 跳转行政文件列表界面
     * @param model
     * @param userNo
     * @return
     */
    @PostMapping("/chemicalFile/chemicalFileListPage")
    public ModelAndView chemicalFileListPage(Model model,@RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]");
        model.addAttribute("userNo", userNo);
        return new ModelAndView("renshi/chemicalFileListPage");
    }




    /**
     * 跳转档案信息编辑界面
     * @param model
     * @param userNo
     * @param id
     * @return
     */
    @PostMapping("/chemicalFile/chemicalFileEdit")
    public ModelAndView chemicalFilEdit(Model model, @RequestAttribute("userNo") String userNo,
                                           @RequestParam("id") String id) {
        ChemicalFileVO chemicalFile = chemicalFileService.getById(Long.parseLong(id));

        // 查询部门和职级数据（直接传递列表对象，而非JSON字符串）
        List<DictionaryVO> documentTypes = dictionaryService.listByQuery(DictionaryQuery.builder().category("档案类型").build());
        List<DictionaryVO> placeTimes = dictionaryService.listByQuery(DictionaryQuery.builder().category("归档年份").build());
        // 传递列表对象到模板
        model.addAttribute("documentTypes", documentTypes);
        model.addAttribute("placeTimes", placeTimes);
        model.addAttribute("chemicalFile", chemicalFile);
        model.addAttribute("userNo", userNo);
        return new ModelAndView("renshi/chemicalFilEdit");
    }


    /**
     * 跳转技术文件列表界面
     * @param model
     * @param userNo
     * @return
     */
    @PostMapping("/chemicalFile/technicalFileListPage")
    public ModelAndView technicalFileListPage(Model model,@RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]");
        model.addAttribute("userNo", userNo);
        return new ModelAndView("renshi/technicalFileListPage");
    }


    /**
     * 跳转到技术文件录入页面
     */
    @PostMapping("/chemicalFile/technicalFileAdd")
    public ModelAndView  technicalFileAdd(Model model,@RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]");
        model.addAttribute("userNo", userNo);
        return new ModelAndView("renshi/technicalFileAdd");
    }


    /**
     * 跳转技术档案信息编辑界面
     * @param model
     * @param userNo
     * @param id
     * @return
     */
    @PostMapping("/chemicalFile/technicalFilEdit")
    public ModelAndView technicalFilEdit(Model model, @RequestAttribute("userNo") String userNo,
                                        @RequestParam("id") String id) {
        ChemicalFileVO chemicalFile = chemicalFileService.getById(Long.parseLong(id));

        // 查询部门和职级数据（直接传递列表对象，而非JSON字符串）
        List<DictionaryVO> documentTypes = dictionaryService.listByQuery(DictionaryQuery.builder().category("档案类型").build());
        List<DictionaryVO> placeTimes = dictionaryService.listByQuery(DictionaryQuery.builder().category("归档年份").build());
        // 传递列表对象到模板
        model.addAttribute("documentTypes", documentTypes);
        model.addAttribute("placeTimes", placeTimes);
        model.addAttribute("chemicalFile", chemicalFile);
        model.addAttribute("userNo", userNo);
        return new ModelAndView("renshi/technicalFilEdit");
    }
}

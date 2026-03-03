package com.yunny.channel.controller.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

/**
 * @ClassName MaterialPlanViewJumpController
 * @Description TODO
 * @Author sunfuwei521@qq.com
 * @Date 2025/11/3 8:58
 */

@Validated
@RestController
@RequestMapping("/materialPlanViewJump")
@Slf4j
public class MaterialPlanViewJumpController {

    /**
     * 跳转到提计划页面
     */
    @PostMapping("/materialPlanMain/submit")
    public ModelAndView materialPlanMainSubmit(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]");
        model.addAttribute("userNo", userNo);
        return new ModelAndView("caigou/materiaPlanAdd");
    }




    /**
     * 跳转物资计划列表界面
     * @param model
     * @param userNo
     * @return
     */
    @PostMapping("/materialPlanMain/materialPlanMainListPage")
    public ModelAndView employeeFileListPage(Model model,@RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]");
        model.addAttribute("userNo", userNo);
        return new ModelAndView("caigou/materiaPlanListPage");
    }



}

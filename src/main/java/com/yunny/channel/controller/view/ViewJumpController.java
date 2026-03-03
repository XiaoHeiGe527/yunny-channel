package com.yunny.channel.controller.view;

import com.yunny.channel.common.query.CompanyVehiclesQuery;
import com.yunny.channel.common.query.EmployeeFileQuery;
import com.yunny.channel.common.query.VehiclesOutwardCardQuery;
import com.yunny.channel.common.vo.CompanyVehiclesVO;
import com.yunny.channel.common.vo.VehicleInsuranceVO;
import com.yunny.channel.service.CompanyVehiclesService;
import com.yunny.channel.service.EmployeeFileService;
import com.yunny.channel.service.VehicleInsuranceService;
import com.yunny.channel.service.VehiclesOutwardCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @program: yunny-channel
 * @description:
 * @author: sunfuwei
 * @create: 2025/03/25
 */

@Validated
@RestController
@RequestMapping("/viewJump")
@Slf4j
public class ViewJumpController {


    @Resource
    VehicleInsuranceService vehicleInsuranceService;

    @Resource
    CompanyVehiclesService companyVehiclesService;

    @Resource
    VehiclesOutwardCardService vehiclesOutwardCardService;

    @Resource
    private EmployeeFileService employeeFileService;




    /**
     * 跳转到系统用户管理
     */
    @PostMapping("/systemUser/listByPage")
    public ModelAndView systemUserListByPage(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]", userNo);
        return new ModelAndView("test/systemUserListByPage");
    }


    /**
     * 跳转到系统角色管理
     */
    @PostMapping("/systemRole/listByPage")
    public ModelAndView systemRoleListByPage(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]", userNo);
        return new ModelAndView("test/systemRoleListByPage");
    }



    /**
     * 跳转到车辆开卡列表界面
     */
    @PostMapping("/vehiclesOutwardCard/listByPage")
    public ModelAndView vehiclesOutwardCardListPage(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]", userNo);
        return new ModelAndView("test/vehiclesOutwardCardListPage");
    }


    /**
     * 跳转到车辆列表界面
     */
    @PostMapping("/companyVehicles/listByPage")
    public ModelAndView companyVehiclesListByPage(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]", userNo);
        return new ModelAndView("test/companyVehiclesListPage");
    }


    /**
     * 跳转给车开卡页面
     *
     * @param model
     * @param id
     * @return
     */
    @PostMapping("/companyVehicles/renewInsurance")
    public ModelAndView vehicleCardOpening(Model model, Long id) {

        CompanyVehiclesVO companyVehiclesVO = companyVehiclesService.getById(id);

        model.addAttribute("id", id);
        model.addAttribute("carNumber", companyVehiclesVO.getCarNumber());
        return new ModelAndView("test/vehicleCardOpening");
    }


    /**
     * 跳转到临期车辆列表
     */
    @PostMapping("/vehicleInsurance/query")
    public ModelAndView Query(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]", userNo);
        return new ModelAndView("test/carInsurance");
    }

    /**
     * 跳转到车辆列表增加页面
     */
    @PostMapping("/vehicleInsurance/add")
    public ModelAndView add(Model model) {
        return new ModelAndView("test/carInsuranceAdd");
    }

    /**
     * 跳转处理页面
     *
     * @param model
     * @param id
     * @return
     */
    @PostMapping("/vehicleInsurance/renewInsurance")
    public ModelAndView renewInsurance(Model model, Long id) {
        VehicleInsuranceVO vo = vehicleInsuranceService.getById(id);
        model.addAttribute("id", id);
        model.addAttribute("carNumber", vo.getCarNumber());
        return new ModelAndView("test/renewInsurancePag");
    }


    /**
     * 跳转到修改密码界面
     *
     * @param model
     * @param id
     * @return
     */
    @PostMapping("/vehicleInsurance/changePassword")
    public ModelAndView changePassword(Model model, Long id) {
        return new ModelAndView("test/changePassword");
    }


    /**
     * 跳转到所有车辆列表分页
     */

    @PostMapping("/vehicleInsurance/carInsuranceListPage")
    public ModelAndView queryListPage(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]", userNo);
        return new ModelAndView("test/carInsuranceListPage");
    }


    /**
     * 跳转到所有车辆列表分页
     */

    @PostMapping("/vehicleInsurance/importVehicleInsuranceExcel")
    public ModelAndView importVehicleInsuranceExcel(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]", userNo);
        return new ModelAndView("test/importVehicleInsuranceExcel");
    }


    /**
     * 跳转到车辆开卡列表界面
     */
    @PostMapping("/homepage")
    public ModelAndView homepage(Model model, @RequestAttribute("userNo") String userNo) {

        //可用卡数量
        Long effectiveVehiclesOutwardCardCount = vehiclesOutwardCardService
                .countByQuery(VehiclesOutwardCardQuery.builder().state(1).build());
        //可用 未发车的数量
       Long effectiveCompanyVehiclesCount = companyVehiclesService
               .countByQuery(CompanyVehiclesQuery.builder().isManage(1).state(1).activeState(1).build());

        //已外出的车辆数量
        Long outCompanyVehiclesCount = companyVehiclesService
                .countByQuery(CompanyVehiclesQuery.builder().isManage(1).state(1).activeState(2).build());


        //过期的卡
        Long alreadyExpiredCount = vehiclesOutwardCardService
                .countByQuery(VehiclesOutwardCardQuery.builder().state(2).build());

        //在职人员
        Long employeesCount =  employeeFileService.countByQuery(EmployeeFileQuery.builder().state(1).build());
        //离职人员
        Long resignationsCount = employeeFileService.countByQuery(EmployeeFileQuery.builder().state(0).build());
        //所有人
       Long allPersonnelCount = employeesCount + resignationsCount;


        model.addAttribute("employeesCount", employeesCount);
        model.addAttribute("resignationsCount", resignationsCount);
        model.addAttribute("allPersonnelCount", allPersonnelCount);

        model.addAttribute("effectiveVehiclesOutwardCardCount", effectiveVehiclesOutwardCardCount);
        model.addAttribute("effectiveCompanyVehiclesCount", effectiveCompanyVehiclesCount);
        model.addAttribute("outCompanyVehiclesCount", outCompanyVehiclesCount);
        model.addAttribute("alreadyExpiredCount", alreadyExpiredCount);

        return new ModelAndView("test/homepage");
    }



    /**
     * 跳转系统日志界面
     */

    @PostMapping("/systemLog/listByPage")
    public ModelAndView systemLogListByPage(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]", userNo);
        return new ModelAndView("test/SystemLogListPage");
    }

    /**
     * 跳转到字典管理页面
     * @param model
     * @param userNo
     * @return
     */
    @PostMapping("/dictionary/listByPage")
    public ModelAndView dictionarydListByPage(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]", userNo);
        return new ModelAndView("test/dictionaryListPage");
    }


    /**
     * 跳转到字典编辑页面
     * @param model
     * @param userNo
     * @return
     */
    @PostMapping("/dictionary/Edit")
    public ModelAndView dictionarydEdit(Model model, @RequestAttribute("userNo") String userNo,@RequestParam("id") Long id) {
        log.info("========userNo=======[{}]", userNo);
        model.addAttribute("id", id);
        return new ModelAndView("test/dictionaryEdit");
    }



    /**
     * 跳转到字典编辑页面
     * @param model
     * @param userNo
     * @return
     */
    @PostMapping("/dictionary/add")
    public ModelAndView dictionaryAdd(Model model, @RequestAttribute("userNo") String userNo) {
        log.info("========userNo=======[{}]", userNo);
        return new ModelAndView("test/dictionaryAdd");
    }



}
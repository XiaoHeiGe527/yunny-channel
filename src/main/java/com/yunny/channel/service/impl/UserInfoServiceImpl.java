package com.yunny.channel.service.impl;

import com.yunny.channel.common.constant.ExceptionConstants;
import com.yunny.channel.common.entity.UserInfoDO;
import com.yunny.channel.common.query.PageQuery;
import com.yunny.channel.common.query.UserBaseInfoQuery;
import com.yunny.channel.common.result.BaseResult;
import com.yunny.channel.common.tools.redis.RedisService;
import com.yunny.channel.common.util.NumberUtils;
import com.yunny.channel.common.vo.UserBaseInfoVo;
import com.yunny.channel.feign.client.MyTestFeignClient;
import com.yunny.channel.mapper.UserInfoMapper;

import com.yunny.channel.service.UserBaseInfoService;
import com.yunny.channel.service.UserInfoService;
import com.yunny.channel.common.dto.UserInfoDTO;
import com.yunny.channel.common.vo.UserInfoVO;
import com.yunny.channel.common.query.UserInfoQuery;
import com.yunny.channel.common.page.CommonPager;
import com.yunny.channel.common.page.PageParameter;
import com.yunny.channel.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

import static com.yunny.channel.common.constant.RedisKeyNameConstants.INSERT_USER_INFO_DATA_JOB_CURRENT_PAGE;


/**
 * Created by Fe
 * Service注解给类起名称name 注入的时候可以根据name注入
 */
@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserInfoServiceImpl implements UserInfoService {

    //Resource 根据name注入
    @Resource
    private UserInfoMapper userInfoMapper;


    @Resource
    private RedisService redisService;

    @Autowired
    MyTestFeignClient myTestFeignClient;


    //Autowired 根据类型注入
    //如果一个接口有多个实现类，我们可以使用@Qualifier注解来指定注入的具体实现类。@Qualifier注解需要和@Autowired注解一起使用。
    @Autowired
    @Qualifier("UserBaseInfoService1")
    private UserBaseInfoService userBaseInfoService;

    /**
    * 分页查询
    * @return
    */
    @Override
    public CommonPager<UserInfoVO> listByPage(UserInfoQuery userInfoQuery) {
        PageParameter pageParameter = userInfoQuery.getPageParameter();
        return new CommonPager<UserInfoVO>(
                new PageParameter(pageParameter.getCurrentPage(), pageParameter.getPageSize(),
                        userInfoMapper.countByQuery(userInfoQuery)),
                userInfoMapper.listByQuery(userInfoQuery).stream()
                        .map(item -> {
                            UserInfoVO userInfoVo = new UserInfoVO();
                            BeanUtils.copyProperties(item, userInfoVo);
                            return userInfoVo;
                        }).collect(Collectors.toList()));
    }

    /**
    * 通过id获取
    * @return
    */
    @Override
    public UserInfoVO getById(Long id) {
    	UserInfoDO userInfoDo = userInfoMapper.getById(id);
    	if(null == userInfoDo){
    		return null;
    	}
    	UserInfoVO userInfoVo = new UserInfoVO();
    	BeanUtils.copyProperties(userInfoDo, userInfoVo);
    	return userInfoVo;
    }

    /**
    * 新增
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int insertSelective(UserInfoDTO userInfoDto) {
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(userInfoDto, userInfoDo);
        return userInfoMapper.insertSelective(userInfoDo);
    }

    /**
    * 修改
    * @return
    */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int updateSelective(UserInfoDTO userInfoDto) {
        UserInfoDO userInfoDo = new UserInfoDO();
        BeanUtils.copyProperties(userInfoDto, userInfoDo);
        return userInfoMapper.updateSelective(userInfoDo);
    }

    @Override
    public BaseResult insertUserInfoData(Long jobparam) {

        //String monthNum = NumberUtils.formatLocalDateTimeStringMonth(LocalDateTime.now());

        /**
         * redis 获取批次 自增 1 生命周期为31 天， KEY 以月为单位
         */
        BaseResult<Long> resultBatch = redisService.incrBy31Day(INSERT_USER_INFO_DATA_JOB_CURRENT_PAGE,1);
        Long batch =resultBatch.getData()+jobparam;

        //根据redis 记录的页数去查询用户信息表
        UserBaseInfoQuery query = new UserBaseInfoQuery();
        query.setCurrentPage(batch.intValue());
        query.setPageSize(2);
        query.setPageParameter(new PageParameter(query.getCurrentPage(), query.getPageSize()));

        CommonPager<UserBaseInfoVo> cpUserBaseInfo = userBaseInfoService.listByQuery(query);
       List<UserBaseInfoVo>  userBaseInfoList = cpUserBaseInfo.getDataList();
       if(CollectionUtils.isEmpty(userBaseInfoList)){
           return BaseResult.success();
       }

       int successCount = this.batchInsertSelective(userBaseInfoList);
       return BaseResult.success("数据插入成功"+successCount+"条");
    }

    /**
     * 批量插入
     * @param userBaseInfoList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public int batchInsertSelective(List<UserBaseInfoVo>  userBaseInfoList) {

        List<UserInfoDO> doList = new ArrayList<>();

        for (UserBaseInfoVo p: userBaseInfoList) {
            if(StringUtil.isEmpty(p.getMobile())||StringUtil.isEmpty(p.getUserNo())||StringUtil.isEmpty(p.getPassword())){
                continue;
            }
            UserInfoDO userInfoDo = UserInfoDO.builder()
                    .userEmail(p.getUserEmail())
                    .userName(StringUtil.isEmpty(p.getUserName()) ? "" :p.getUserName())//三元判断  判断?真的情况结果:假的情况结果
                    .userNickName(StringUtil.isEmpty(p.getUserNickName())?"":p.getUserNickName())
                    .userNo(StringUtil.isEmpty(p.getUserNo())?"":p.getUserNo())
                    .userPicture(p.getUserPicture())
                    .id(p.getId())
                    .mobile(StringUtil.isEmpty(p.getMobile())?"":p.getMobile())
                    .password(StringUtil.isEmpty(p.getPassword())?"":p.getPassword())
                    .state(p.getState())
                    .build();
            doList.add(userInfoDo);
        }
        return userInfoMapper.batchInsertSelective(doList);
    }


    @Override
    public BaseResult insertUserInfoDataTestFeign(String token) {

        try {

            //根据虚机类型获得API地址
            String uri = "http://192.168.0.9:8081";
            BaseResult result = myTestFeignClient.insertUserInfoData(new URI(uri),token);

            if(ExceptionConstants.RESULT_CODE_SUCCESS!=result.getCode()){

                log.error("用户信息迁移调取失败:[{}]",result.getMessage());
                log.info("用户信息迁移调取失败:[{}]",result.getMessage());
                return  result;
            }

            log.info("用户信息迁移调取成功:[{}]",result.getMessage());
            return result;

        } catch (URISyntaxException e) {
            log.error("用户信息迁移调取失败:[{}]",e);
            log.info("用户信息迁移调取失败:[{}]",e);
            return BaseResult.failure(ExceptionConstants.RESULT_CODE_SERVER_ERROR,"失败信息:"+e.getMessage());

        }

    }


}

package com.yunny.channel.mapper;

import com.yunny.channel.common.entity.UserBaseInfoDO;
import com.yunny.channel.common.query.UserBaseInfoQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserBaseInfoMapper {

    /**
     * 查询用户
     *
     * @param userBaseInfoQuery
     * @return
     */
    List<UserBaseInfoDO> selectUserBaseInfoDOList(UserBaseInfoQuery userBaseInfoQuery);


    /**
     * 查询总条数
     *
     * @param productInfoQuery
     * @return
     */
    Long countByQuery(UserBaseInfoQuery productInfoQuery);

    /**
     * 分页查询
     *
     * @param productInfoQuery
     * @return
     */
    List<UserBaseInfoDO> listByQuery(UserBaseInfoQuery productInfoQuery);


}

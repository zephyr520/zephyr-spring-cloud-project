package com.zephyr.user.dao;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.zephyr.user.domain.TAdminUser;
import com.zephyr.user.dto.request.QueryAdminUserReqDTO;
import com.zephyr.user.dto.response.AdminUserRespDTO;

public interface TAdminUserMapper {
	int deleteByPrimaryKey(Integer userId);

	int insert(TAdminUser record);

	int insertSelective(TAdminUser record);

	TAdminUser selectByPrimaryKey(Integer userId);

	int updateByPrimaryKeySelective(TAdminUser record);

	int updateByPrimaryKey(TAdminUser record);

	TAdminUser queryByUsername(@Param("userName") String userName);

	TAdminUser queryByPhone(@Param("phone") String phone);

	Page<AdminUserRespDTO> queryListByPage(QueryAdminUserReqDTO reqDto);
}
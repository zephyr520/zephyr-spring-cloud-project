package com.zephyr.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zephyr.user.domain.TAdminRoleMenu;

public interface TAdminRoleMenuMapper {
	int deleteByPrimaryKey(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

	int insert(TAdminRoleMenu record);

	int insertSelective(TAdminRoleMenu record);

	List<Integer> queryMenuIdsByRoleId(@Param("roleId") Integer roleId);

	int deleteByRoleId(@Param("roleId") Integer roleId);
}
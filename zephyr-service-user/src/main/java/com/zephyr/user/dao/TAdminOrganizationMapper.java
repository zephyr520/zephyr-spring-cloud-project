package com.zephyr.user.dao;

import com.zephyr.user.domain.TAdminOrganization;

public interface TAdminOrganizationMapper {
    int deleteByPrimaryKey(String orgCode);

    int insert(TAdminOrganization record);

    int insertSelective(TAdminOrganization record);

    TAdminOrganization selectByPrimaryKey(String orgCode);

    int updateByPrimaryKeySelective(TAdminOrganization record);

    int updateByPrimaryKey(TAdminOrganization record);
}
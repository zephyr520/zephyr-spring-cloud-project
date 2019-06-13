package com.zephyr.user.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zephyr.common.exception.BOException;
import com.zephyr.common.web.ApiResultCode;
import com.zephyr.user.dao.TAdminRoleMapper;
import com.zephyr.user.dao.TAdminUserMapper;
import com.zephyr.user.dao.TAdminUserRoleMapper;
import com.zephyr.user.domain.TAdminUser;
import com.zephyr.user.domain.TAdminUserRole;

@Component
public class AdminUserManager {

	@Autowired
	TAdminUserMapper adminUserMapper;
	@Autowired
	TAdminRoleMapper adminRoleMapper;
	@Autowired
	TAdminUserRoleMapper adminUserRoleMapper;
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean createUser(TAdminUser record, List<Integer> roleIds) {
		int addRow = adminUserMapper.insertSelective(record);
        if (addRow < 1) {
            throw new BOException(ApiResultCode.SERVICE_ERROR, "新增用户信息失败");
        }
        TAdminUserRole userRole = null;
        for (Integer roleId : roleIds) {
        	userRole = new TAdminUserRole();
        	userRole.setRoleId(roleId);
        	userRole.setUserId(record.getUserId());
        	addRow = adminUserRoleMapper.insertSelective(userRole);
        	if (addRow < 1) {
        		throw new BOException(ApiResultCode.SERVICE_ERROR, "分配用户角色失败");
        	}
        }
		return Boolean.TRUE;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean modifyUser(TAdminUser record, List<Integer> roleIds) {
		int updateRow = adminUserMapper.updateByPrimaryKeySelective(record);
        if (updateRow < 1) {
            throw new BOException(ApiResultCode.SERVICE_ERROR, "修改用户信息失败");
        }
        int addRow = 0;
        TAdminUserRole userRole = null;
        adminUserRoleMapper.deleteByUserId(record.getUserId());
        for (Integer roleId : roleIds) {
        	userRole = new TAdminUserRole();
        	userRole.setRoleId(roleId);
        	userRole.setUserId(record.getUserId());
        	addRow = adminUserRoleMapper.insertSelective(userRole);
        	if (addRow < 1) {
        		throw new BOException(ApiResultCode.SERVICE_ERROR, "分配用户角色失败");
        	}
        }
		return Boolean.TRUE;
	}
}

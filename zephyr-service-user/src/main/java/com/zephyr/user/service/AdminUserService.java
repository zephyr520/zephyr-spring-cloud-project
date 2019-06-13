package com.zephyr.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.zephyr.common.constant.Constant;
import com.zephyr.common.exception.BOException;
import com.zephyr.common.util.AESUtil;
import com.zephyr.common.util.BeanUtil;
import com.zephyr.common.web.ApiResultCode;
import com.zephyr.user.dao.TAdminRoleMapper;
import com.zephyr.user.dao.TAdminUserMapper;
import com.zephyr.user.dao.TAdminUserRoleMapper;
import com.zephyr.user.domain.TAdminUser;
import com.zephyr.user.dto.request.AdminUserReqDTO;
import com.zephyr.user.dto.request.QueryAdminUserReqDTO;
import com.zephyr.user.dto.response.AdminUserRespDTO;
import com.zephyr.user.manager.AdminUserManager;

@Service
public class AdminUserService {

	@Autowired
	TAdminUserMapper adminUserMapper;
	@Autowired
	TAdminRoleMapper adminRoleMapper;
	@Autowired
	TAdminUserRoleMapper adminUserRoleMapper;
	@Autowired
	AdminUserManager adminUserManager;
	
	public Page<AdminUserRespDTO> queryList(QueryAdminUserReqDTO reqDto) {
		return adminUserMapper.queryListByPage(reqDto);
	}
	
	public Boolean createUser(AdminUserReqDTO reqDto) {
		TAdminUser record = BeanUtil.copy(reqDto, TAdminUser.class);
		record.setPassWord(AESUtil.encryptString(record.getPassWord(), Constant.ENCRYPT_PWD_KEY));
		try {
			return adminUserManager.createUser(record, reqDto.getRoleIds());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOException(ApiResultCode.SERVICE_ERROR, "创建用户信息失败");
		}
	}
	
	public Boolean modifyUser(AdminUserReqDTO reqDto) {
		Boolean result = Boolean.FALSE;
		TAdminUser oldRecord = adminUserMapper.selectByPrimaryKey(reqDto.getUserId());
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "用户信息不存在");
		}
		TAdminUser record = BeanUtil.copy(reqDto, TAdminUser.class);
		record.setPassWord(AESUtil.encryptString(record.getPassWord(), Constant.ENCRYPT_PWD_KEY));
		try {
			result = adminUserManager.modifyUser(record, reqDto.getRoleIds());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BOException(ApiResultCode.SERVICE_ERROR, "创建用户信息失败");
		}
		
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteUser(Integer userId) {
		TAdminUser oldRecord = adminUserMapper.selectByPrimaryKey(userId);
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "用户不存在");
		}
		if (!oldRecord.getIfOperate()) {
			throw new BOException(ApiResultCode.DELETE_FORBID);
		}
		TAdminUser record = new TAdminUser();
		record.setUserId(userId);
		record.setStatus(Constant.DISABLE);
		int deleteRow = adminUserMapper.updateByPrimaryKeySelective(record);
		if (deleteRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR);
		}
		return Boolean.TRUE;
	}
}

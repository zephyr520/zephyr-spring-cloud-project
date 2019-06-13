package com.zephyr.user.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.zephyr.common.exception.BOException;
import com.zephyr.common.web.ApiResultCode;
import com.zephyr.user.dao.TAdminRoleMapper;
import com.zephyr.user.dao.TAdminRoleMenuMapper;
import com.zephyr.user.dao.TAdminUserRoleMapper;
import com.zephyr.user.domain.TAdminRole;
import com.zephyr.user.domain.TAdminRoleMenu;
import com.zephyr.user.dto.request.QueryAdminRoleReqDTO;
import com.zephyr.user.dto.request.RoleMenuIdsReqDTO;
import com.zephyr.user.dto.response.AdminRoleRespDTO;

@Service
public class AdminRoleService {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminRoleService.class);

	@Autowired
	TAdminRoleMapper adminRoleMapper;
	@Autowired
	TAdminUserRoleMapper adminUserRoleMapper;
	@Autowired
	TAdminRoleMenuMapper adminRoleMenuMapper;
	
	public List<TAdminRole> queryAllRoles() {
		return adminRoleMapper.queryAllRoles();
	}
	
	public Page<AdminRoleRespDTO> queryList(QueryAdminRoleReqDTO reqDto) {
		return adminRoleMapper.queryListByPage(reqDto);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteRole(Integer roleId) {
		TAdminRole oldRecord = adminRoleMapper.selectByPrimaryKey(roleId);
		if (oldRecord == null) {
			throw new BOException(ApiResultCode.NO_DATA, "角色信息不存在");
		}
		if (!oldRecord.getIfOperate()) {
			throw new BOException(ApiResultCode.DELETE_FORBID);
		}
		int count = adminUserRoleMapper.countUserByRoleId(roleId);
		if (count > 0) {
			throw new BOException(ApiResultCode.OTHER_ERROR, "该角色已关联管理员, 不能被删除");
		}
		int deleteRow = adminRoleMapper.deleteByPrimaryKey(roleId);
		if (deleteRow < 1) {
			throw new BOException(ApiResultCode.SERVICE_ERROR);
		}
		return Boolean.TRUE;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Boolean roleAuth(RoleMenuIdsReqDTO reqDto) {
		logger.info("参数信息：{}",JSONObject.toJSONString(reqDto));
		adminRoleMenuMapper.deleteByRoleId(reqDto.getRoleId());
		TAdminRoleMenu roleMenu = null;
		int count = 0;
		for (Integer menuId : reqDto.getMenuIds()) {
			roleMenu = new TAdminRoleMenu();
			roleMenu.setRoleId(reqDto.getRoleId());
			roleMenu.setMenuId(menuId);
			int addRow = adminRoleMenuMapper.insertSelective(roleMenu);
			if (addRow > 0) {
				count ++;
			}
		}
		if (count != reqDto.getMenuIds().size()) {
			throw new BOException(ApiResultCode.SERVICE_ERROR);
		}
		return Boolean.TRUE;
	}
}

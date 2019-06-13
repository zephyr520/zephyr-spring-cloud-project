package com.zephyr.user.admin.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zephyr.common.util.BeanUtil;
import com.zephyr.common.web.AbstractApi;
import com.zephyr.common.web.ApiResult;
import com.zephyr.user.domain.TAdminMenu;
import com.zephyr.user.dto.AdminMenuDTO;
import com.zephyr.user.service.AdminMenuService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "系统菜单")
@RestController
public class AdminMenuApi extends AbstractApi {

	@Autowired
	AdminMenuService adminMenuService;
	
	@ApiOperation("获取用户授权菜单列表")
	@GetMapping("/admin/menu/auth/list")
	public ApiResult<List<AdminMenuDTO>> treeMenu() {
		Integer userId = 0;
		return new ApiResult<>(adminMenuService.listTreeMenu(userId));
	}
	
	@ApiOperation("根据角色ID获取授权菜单ID列表")
	@GetMapping("/admin/menu/{roleId}/auth/list")
	public ApiResult<List<Integer>> authMenuIds(@PathVariable Integer roleId) {
		return new ApiResult<>(adminMenuService.queryAuthMenuIdsByRoleId(roleId));
	}
	
	@ApiOperation("所有菜单信息列表")
	@GetMapping("/admin/menu/all/list")
	public ApiResult<List<AdminMenuDTO>> listAllMenu() {
		List<TAdminMenu> menus = adminMenuService.listAllMenu();
		menus.sort(Comparator.comparing(TAdminMenu::getType).thenComparing(TAdminMenu::getOrderNum));
		return new ApiResult<>(treeMenus(menus, 0));
	}
	
	private List<AdminMenuDTO> treeMenus(List<TAdminMenu> menus, Integer parentId) {
        List<AdminMenuDTO> menuList = new ArrayList<>();
        for (TAdminMenu menu : menus) {
    		AdminMenuDTO menuDTO = BeanUtil.copy(menu, AdminMenuDTO.class);
            if (menu.getParentId().compareTo(parentId) == 0) {
                List<AdminMenuDTO> treeList = treeMenus(menus, menuDTO.getMenuId());
                menuDTO.setSubMenus(treeList);
                menuList.add(menuDTO);
            }
        }
        return menuList;
    }
}

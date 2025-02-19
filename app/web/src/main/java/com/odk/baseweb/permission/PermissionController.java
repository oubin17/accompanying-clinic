package com.odk.baseweb.permission;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.inter.permission.PermissionApi;
import com.odk.baseapi.request.role.PermissionPageQueryRequest;
import com.odk.baseapi.request.role.RoleAddRequest;
import com.odk.baseapi.request.role.UserRoleRelaRequest;
import com.odk.baseapi.response.PermissionQueryResponse;
import com.odk.baseapi.vo.PermissionVO;
import com.odk.baseutil.enums.InnerRoleEnum;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PermissionController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/8
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionApi permissionApi;

    public PermissionController(PermissionApi permissionApi) {
        this.permissionApi = permissionApi;
    }

    /**
     * 查询用户角色权限
     *
     * @param userId
     * @return
     */
    @GetMapping("/userId")
    public ServiceResponse<PermissionQueryResponse> queryUserPermission(@RequestParam("userId") String userId) {
        return permissionApi.userPermission(userId);
    }

    /**
     * 查询用户角色权限
     *
     * @param request
     * @return
     */
    @GetMapping("/list/userId")
    public ServiceResponse<PermissionQueryResponse> queryUserPermissionByPage(@ModelAttribute PermissionPageQueryRequest request) {
        return permissionApi.userPermissionByPage(request);
    }

    /**
     * 查询所有角色权限
     *
     * @param request
     * @return
     */
    @GetMapping("/list")
    public ServiceResponse<PermissionQueryResponse> allPermissionByPage(@ModelAttribute PermissionPageQueryRequest request) {
        return permissionApi.allPermission(request);
    }

    /**
     * 获取所有的权限集合
     *
     * @return
     */
    @GetMapping("/all")
    public ServiceResponse<List<PermissionVO>> getAllPermissions() {
        return permissionApi.allPermissions();
    }


    /**
     * 添加角色
     *
     * @param roleAddRequest
     * {@link InnerRoleEnum}
     * @return
     */
    @SaCheckRole(value = {"ADMIN"})
    @PostMapping("/role/add")
    public ServiceResponse<String> addRole(@RequestBody RoleAddRequest roleAddRequest) {
        return permissionApi.addRole(roleAddRequest);
    }

    /**
     * 用户-角色绑定
     *
     * @param relaRequest
     * @return
     */
    @PostMapping("/role/rela/add")
    public ServiceResponse<String> addRoleRel(@RequestBody UserRoleRelaRequest relaRequest) {
        return permissionApi.addRoleRela(relaRequest);
    }
}

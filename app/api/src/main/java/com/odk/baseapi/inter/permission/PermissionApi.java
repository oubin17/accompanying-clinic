package com.odk.baseapi.inter.permission;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.baseapi.request.role.PermissionPageQueryRequest;
import com.odk.baseapi.request.role.RoleAddRequest;
import com.odk.baseapi.request.role.UserRoleRelaRequest;
import com.odk.baseapi.response.PermissionQueryResponse;
import com.odk.baseapi.vo.PermissionVO;

import java.util.List;

/**
 * PermissionApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/8
 */
public interface PermissionApi {


    /**
     * 用户权限
     *
     * @param userId
     * @return
     */
    ServiceResponse<PermissionQueryResponse> userPermission(Long userId);


    /**
     * 用户权限
     *
     * @param request
     * @return
     */
    ServiceResponse<PermissionQueryResponse> userPermissionByPage(PermissionPageQueryRequest request);


    /**
     * 用户权限
     *
     * @param request
     * @return
     */
    ServiceResponse<PermissionQueryResponse> allPermission(PermissionPageQueryRequest request);


    /**
     * 所有权限集合
     *
     * @return
     */
    ServiceResponse<List<PermissionVO>> allPermissions();



    /**
     * 添加角色
     *
     * @param roleAddRequest
     * @return
     */
    ServiceResponse<Long> addRole(RoleAddRequest roleAddRequest);

    /**
     * 添加用户角色
     *
     * @param relaRequest
     * @return
     */
    ServiceResponse<Long> addRoleRela(UserRoleRelaRequest relaRequest);

}

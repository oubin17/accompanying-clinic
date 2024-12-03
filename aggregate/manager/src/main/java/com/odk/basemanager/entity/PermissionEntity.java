package com.odk.basemanager.entity;

import com.google.common.collect.Lists;
import com.odk.basedomain.domain.permission.PermissionDO;
import com.odk.basedomain.domain.permission.UserRoleDO;
import lombok.Data;

import java.util.List;

/**
 * PermissionEntity
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/8
 */
@Data
public class PermissionEntity {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色列表
     */
    private List<UserRoleDO> roles = Lists.newArrayList();

    /**
     * 权限列表
     */
    private List<PermissionDO> permissions = Lists.newArrayList();

    /**
     * 分页总数
     */
    private int totalCount;
}

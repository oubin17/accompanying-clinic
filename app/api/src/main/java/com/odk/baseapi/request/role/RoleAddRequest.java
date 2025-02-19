package com.odk.baseapi.request.role;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

/**
 * RoleAddRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleAddRequest extends BaseRequest {

    @Serial
    private static final long serialVersionUID = 3836916230018832242L;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限id列表
     */
    private List<String> permissionIds;
}

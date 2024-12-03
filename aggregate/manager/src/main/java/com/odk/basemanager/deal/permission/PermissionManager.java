package com.odk.basemanager.deal.permission;

import cn.dev33.satoken.stp.StpUtil;
import com.google.common.collect.Lists;
import com.odk.base.enums.common.CommonStatusEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.basedomain.domain.permission.PermissionDO;
import com.odk.basedomain.domain.permission.RolePermissionRelDO;
import com.odk.basedomain.domain.permission.UserRoleDO;
import com.odk.basedomain.domain.permission.UserRoleRelDO;
import com.odk.basedomain.domain.user.UserBaseDO;
import com.odk.basedomain.repository.permission.PermissionRepository;
import com.odk.basedomain.repository.permission.RolePermissionRelRepository;
import com.odk.basedomain.repository.permission.UserRoleRelRepository;
import com.odk.basedomain.repository.permission.UserRoleRepository;
import com.odk.basedomain.repository.user.UserBaseRepository;
import com.odk.basemanager.entity.PermissionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * PermissionManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/8
 */
@Slf4j
@Service
public class PermissionManager {

    private UserBaseRepository userBaseRepository;

    private UserRoleRepository userRoleRepository;

    private PermissionRepository permissionRepository;

    private UserRoleRelRepository relRepository;

    private RolePermissionRelRepository rolePermissionRelRepository;

    private TransactionTemplate transactionTemplate;

    /**
     * 查找用户权限
     *
     * @param userId
     * @return
     */
    public PermissionEntity getAllPermissions(Long userId) {
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setUserId(userId);
        List<UserRoleDO> userRoleDOS = userRoleRepository.findAllUserRole(userId);
        permissionEntity.setRoles(userRoleDOS);
        if (!CollectionUtils.isEmpty(userRoleDOS)) {
            List<Long> roleIds = userRoleDOS.stream().map(UserRoleDO::getId).collect(Collectors.toList());
            List<PermissionDO> allRolePermission = permissionRepository.findAllRolePermission(roleIds);
            permissionEntity.setPermissions(allRolePermission);
        } else {
            permissionEntity.setPermissions(Lists.newArrayList());
        }
        return permissionEntity;
    }

    public PermissionEntity getPermissionsByPage(int pageNum, int pageSize) {
        PermissionEntity permissionEntity = new PermissionEntity();

        int allUserRoleCount = userRoleRepository.findAllUserRoleCount(StpUtil.getLoginIdAsLong());
        permissionEntity.setTotalCount(allUserRoleCount);
        if (allUserRoleCount > 0) {
            List<UserRoleDO> userRoleByPage = userRoleRepository.findUserRoleByPage(StpUtil.getLoginIdAsLong(), pageSize, pageNum * pageSize);
            permissionEntity.setRoles(userRoleByPage);
            if (!CollectionUtils.isEmpty(userRoleByPage)) {
                List<Long> roleIds = userRoleByPage.stream().map(UserRoleDO::getId).collect(Collectors.toList());
                List<PermissionDO> allRolePermission = permissionRepository.findAllRolePermission(roleIds);
                permissionEntity.setPermissions(allRolePermission);
            } else {
                permissionEntity.setPermissions(Lists.newArrayList());
            }
        }
        return permissionEntity;
    }

    public PermissionEntity getAllPermissionsByPage(int pageNum, int pageSize) {
        PermissionEntity permissionEntity = new PermissionEntity();
        List<UserRoleDO> userRoleByPage =userRoleRepository.findUserRoleByPage(pageSize, pageNum * pageSize);
        permissionEntity.setRoles(userRoleByPage);
        if (!CollectionUtils.isEmpty(userRoleByPage)) {
            List<Long> roleIds = userRoleByPage.stream().map(UserRoleDO::getId).collect(Collectors.toList());
            List<PermissionDO> allRolePermission = permissionRepository.findAllRolePermission(roleIds);
            permissionEntity.setPermissions(allRolePermission);
        } else {
            permissionEntity.setPermissions(Lists.newArrayList());
        }
        return permissionEntity;
    }

    public List<PermissionDO> getAllPermissions() {
        return permissionRepository.findByStatus(CommonStatusEnum.NORMAL.getCode());
    }

    /**
     * 添加角色
     *
     * @param roleCode
     * @param roleName
     * @return
     */
    public Long addRole(String roleCode, String roleName, List<Long> permissionIds) {
        UserRoleDO userRoleDO = userRoleRepository.findByRoleCode(roleCode);
        AssertUtil.isNull(userRoleDO, BizErrorCode.PARAM_ILLEGAL, "角色码重复，添加角色失败");
        UserRoleDO addRole = new UserRoleDO();
        addRole.setRoleCode(roleCode);
        addRole.setRoleName(roleName);
        addRole.setStatus(CommonStatusEnum.NORMAL.getCode());
        return transactionTemplate.execute(status -> {
            UserRoleDO save = userRoleRepository.save(addRole);
            List<PermissionDO> allByIdIn = permissionRepository.findAllByIdIn(permissionIds);
            allByIdIn.forEach(permissionDO -> {
                RolePermissionRelDO rolePermissionRelDO = new RolePermissionRelDO();
                rolePermissionRelDO.setRoleId(save.getId());
                rolePermissionRelDO.setPermissionId(permissionDO.getId());
                rolePermissionRelRepository.save(rolePermissionRelDO);
            });
            return save.getId();
        } );
    }

    public Long addUserRoleRela(Long roleId, Long userId) {
        UserRoleRelDO userRoleRelDO = relRepository.findByUserIdAndRoleId(userId, roleId);
        AssertUtil.isNull(userRoleRelDO, BizErrorCode.PARAM_ILLEGAL, "用户已具备该权限");

        Optional<UserRoleDO> userRoleDO = userRoleRepository.findById(roleId);
        AssertUtil.isTrue(userRoleDO.isPresent(), BizErrorCode.PARAM_ILLEGAL, "角色不存在");
        Optional<UserBaseDO> userBaseDO = userBaseRepository.findById(userId);
        AssertUtil.isTrue(userBaseDO.isPresent(), BizErrorCode.PARAM_ILLEGAL, "用户不存在");

        UserRoleRelDO roleRelDO = new UserRoleRelDO();
        roleRelDO.setUserId(userId);
        roleRelDO.setRoleId(roleId);
        UserRoleRelDO save = relRepository.save(roleRelDO);
        return save.getId();

    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Autowired
    public void setUserBaseRepository(UserBaseRepository userBaseRepository) {
        this.userBaseRepository = userBaseRepository;
    }

    @Autowired
    public void setRelRepository(UserRoleRelRepository relRepository) {
        this.relRepository = relRepository;
    }

    @Autowired
    public void setRolePermissionRelRepository(RolePermissionRelRepository rolePermissionRelRepository) {
        this.rolePermissionRelRepository = rolePermissionRelRepository;
    }

    @Autowired
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}

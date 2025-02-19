package com.odk.basedomain.repository.permission;

import com.odk.basedomain.domain.permission.UserRoleDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * UserRoleRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/8
 */
public interface UserRoleRepository extends JpaRepository<UserRoleDO, String> {

    @Query(value = "select * from t_user_role where id IN ( SELECT role_id FROM t_user_role_rel WHERE user_id = :userId ) AND STATUS = '0'", nativeQuery = true)
    List<UserRoleDO> findAllUserRole(@Param("userId") String userId);

    /**
     * 根据角色码查询角色
     *
     * @param roleCode
     * @return
     */
    UserRoleDO findByRoleCode(String roleCode);

    @Query(value = "select count(1) from t_user_role where id IN ( SELECT role_id FROM t_user_role_rel WHERE user_id = :userId ) AND STATUS = '0'", nativeQuery = true)
    int findAllUserRoleCount(@Param("userId") String userId);

    /**
     * 分页查找
     *
     * @param userId
     * @param pageLimit
     * @param pageOffset
     * @return
     */
    @Query(value = "select * from t_user_role where id IN ( SELECT role_id FROM t_user_role_rel WHERE user_id = :userId ) AND STATUS = '0' limit :pageLimit offset :pageOffset", nativeQuery = true)
    List<UserRoleDO> findUserRoleByPage(@Param("userId") Long userId, @Param("pageLimit") int pageLimit, @Param("pageOffset") int pageOffset);


    /**
     * 分页查找
     *
     * @param pageLimit
     * @param pageOffset
     * @return
     */
    @Query(value = "select * from t_user_role limit :pageLimit offset :pageOffset", nativeQuery = true)
    List<UserRoleDO> findUserRoleByPage(@Param("pageLimit") int pageLimit, @Param("pageOffset") int pageOffset);


}

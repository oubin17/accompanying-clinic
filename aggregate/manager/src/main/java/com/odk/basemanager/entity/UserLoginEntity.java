package com.odk.basemanager.entity;

import lombok.Data;

/**
 * UserLoginEntity
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/5
 */
@Data
public class UserLoginEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录token
     */
    private String token;

}

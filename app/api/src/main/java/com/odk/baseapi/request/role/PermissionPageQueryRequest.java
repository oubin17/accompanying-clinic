package com.odk.baseapi.request.role;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * PermissionPageQueryRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/2
 */
@Data
@AllArgsConstructor
public class PermissionPageQueryRequest {

    private int pageNum;

    private int pageSize;
}

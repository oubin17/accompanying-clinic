package com.odk.baseapi.request.role;

import com.odk.base.vo.request.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;

/**
 * PermissionPageQueryRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/12/2
 */
@Data
@AllArgsConstructor
public class PermissionPageQueryRequest extends PageRequest {

    @Serial
    private static final long serialVersionUID = 6002235311838207119L;

}

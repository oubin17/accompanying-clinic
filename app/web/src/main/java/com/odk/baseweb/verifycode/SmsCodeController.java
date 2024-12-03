package com.odk.baseweb.verifycode;

import com.odk.base.vo.response.ServiceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * SmsCodeController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/11/28
 */
@RestController
@RequestMapping("/sms")
public class SmsCodeController {

    /**
     * 发送验证码
     *
     * @param mobileNo
     * @return
     */
    @GetMapping("/code")
    public ServiceResponse<Long> userRegister(@RequestParam("mobileNo") String mobileNo) {
        return ServiceResponse.valueOfSuccess(111111L);
    }
}

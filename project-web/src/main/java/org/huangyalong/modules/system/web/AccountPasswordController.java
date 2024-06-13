package org.huangyalong.modules.system.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.huangyalong.core.commons.info.ApiResponse;
import org.huangyalong.core.web.impl.BaseControllerImpl;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.request.AccountPasswordBO;
import org.huangyalong.modules.system.service.AccountPasswordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/account/password")
@Tag(name = "帐号管理")
public class AccountPasswordController extends BaseControllerImpl<AccountPasswordService, Account> {

    @SaCheckLogin
    @PutMapping
    @Operation(summary = "修改当前用户密码")
    public ApiResponse<Boolean> update(@RequestBody @Validated AccountPasswordBO payload) {
        return ApiResponse.ok(getService()
                .update(payload));
    }
}

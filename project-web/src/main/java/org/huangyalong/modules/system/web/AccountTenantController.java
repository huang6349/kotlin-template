package org.huangyalong.modules.system.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.huangyalong.core.commons.info.ApiResponse;
import org.huangyalong.core.web.impl.BaseControllerImpl;
import org.huangyalong.modules.system.domain.Tenant;
import org.huangyalong.modules.system.request.AccountTenantBO;
import org.huangyalong.modules.system.service.AccountTenantService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/account/tenant")
@Tag(name = "帐号管理")
public class AccountTenantController extends BaseControllerImpl<AccountTenantService, Tenant> {

    @SaCheckLogin
    @PutMapping
    @Operation(summary = "修改当前用户租户")
    public ApiResponse<Boolean> update(@RequestBody @Validated AccountTenantBO payload) {
        return ApiResponse.ok(getService()
                .updateByAccountId(payload));
    }

    @SaCheckLogin
    @GetMapping("/_query")
    @Operation(summary = "获取当前用户租户（列表）")
    public ApiResponse<List<Tenant>> query() {
        return ApiResponse.ok(getService()
                .getByAccountId());
    }
}

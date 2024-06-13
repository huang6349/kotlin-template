package org.huangyalong.modules.system.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.info.ApiResponse;
import org.huangyalong.core.web.impl.BaseControllerImpl;
import org.huangyalong.modules.system.domain.Perm;
import org.huangyalong.modules.system.service.AccountPermService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/account/perm")
@Tag(name = "帐号管理")
public class AccountPermController extends BaseControllerImpl<AccountPermService, Perm> {

    @SaCheckLogin
    @GetMapping("/_query")
    @Operation(summary = "获取当前用户权限（列表）")
    public ApiResponse<List<String>> query() {
        val data = StpUtil.getPermissionList();
        return ApiResponse.ok(data);
    }
}

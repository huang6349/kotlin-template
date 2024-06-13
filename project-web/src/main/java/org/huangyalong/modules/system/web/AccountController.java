package org.huangyalong.modules.system.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.info.ApiResponse;
import org.huangyalong.core.web.impl.BaseControllerImpl;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.request.AccountQueriesUtil;
import org.huangyalong.modules.system.response.AccountVO;
import org.huangyalong.modules.system.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "帐号管理")
public class AccountController extends BaseControllerImpl<AccountService, Account> {

    @SaCheckLogin
    @GetMapping("/account")
    @Operation(summary = "获取当前用户信息")
    public ApiResponse<AccountVO> account() {
        val id = StpUtil.getLoginIdAsLong();
        val query = AccountQueriesUtil.getQueryWrapper(id);
        return ApiResponse.ok(getService()
                .getOneAs(AccountQueriesUtil.getQueryWrapper(query),
                        AccountVO.class));
    }

    @SaCheckLogin
    @GetMapping("/account/_perm")
    @Operation(summary = "获取当前用户权限")
    public ApiResponse<List<String>> perm() {
        val data = StpUtil.getPermissionList();
        return ApiResponse.ok(data);
    }

    @SaCheckLogin
    @GetMapping("/account/_role")
    @Operation(summary = "获取当前用户角色")
    public ApiResponse<List<String>> role() {
        val data = StpUtil.getRoleList();
        return ApiResponse.ok(data);
    }
}

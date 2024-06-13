package org.huangyalong.modules.system.web;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.info.ApiResponse;
import org.huangyalong.core.web.impl.BaseControllerImpl;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.exception.AuthorizeNotExistException;
import org.huangyalong.modules.system.request.LoginQueries;
import org.huangyalong.modules.system.request.LoginQueriesUtil;
import org.huangyalong.modules.system.response.JWTToken;
import org.huangyalong.modules.system.service.AccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Tag(name = "授权管理")
public class UserJWTController extends BaseControllerImpl<AccountService, Account> {

    @PostMapping("/authenticate")
    @Operation(summary = "获取授权令牌")
    public ApiResponse<JWTToken> authorize(@RequestBody @Validated LoginQueries queries) {
        val query = LoginQueriesUtil.getQueryWrapper(queries);
        val data = getService()
                .getOneOpt(LoginQueriesUtil.getQueryWrapper(query))
                .orElseThrow(AuthorizeNotExistException::new);
        if (BCrypt.checkpw(queries.getPassword(), data.getPassword()))
            StpUtil.login(data.getId());
        else throw new AuthorizeNotExistException();
        val idToken = StpUtil.getTokenValue();
        return ApiResponse.ok(new JWTToken(idToken));
    }
}

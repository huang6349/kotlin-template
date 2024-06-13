package org.huangyalong.modules.system.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Opt;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.exception.DataNotExistException;
import org.huangyalong.core.commons.info.ApiResponse;
import org.huangyalong.core.request.PageQueries;
import org.huangyalong.core.web.impl.BaseControllerImpl;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.request.UserBO;
import org.huangyalong.modules.system.request.UserQueries;
import org.huangyalong.modules.system.request.UserQueriesUtil;
import org.huangyalong.modules.system.response.UserVO;
import org.huangyalong.modules.system.service.AccountService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController extends BaseControllerImpl<AccountService, Account> {

    @SaCheckLogin
    @SaCheckPermission({"user:add"})
    @PostMapping
    @Operation(summary = "新增单个数据")
    public ApiResponse<Boolean> add(@RequestBody @Validated UserBO payload) {
        return ApiResponse.ok(getService()
                .add(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"user:update"})
    @PutMapping
    @Operation(summary = "修改单个数据")
    public ApiResponse<Boolean> update(@RequestBody @Validated UserBO payload) {
        return ApiResponse.ok(getService()
                .update(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"user:query"})
    @GetMapping("/_query/paging")
    @Operation(summary = "根据条件查询（分页）")
    public ApiResponse<Page<UserVO>> queryPage(UserQueries queries, PageQueries pageQueries) {
        Page<UserVO> page = Page.of(Opt.ofNullable(pageQueries.getPageNumber())
                .orElse(PageQueries.DEFAULT_PAGE_NUMBER), Opt.ofNullable(pageQueries.getPageSize())
                .orElse(PageQueries.DEFAULT_PAGE_SIZE));
        val query = UserQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .pageAs(page, UserQueriesUtil.getQueryWrapper(query),
                        UserVO.class));
    }

    @SaCheckLogin
    @SaCheckPermission({"user:query"})
    @GetMapping("/_query")
    @Operation(summary = "根据条件查询（列表）")
    public ApiResponse<List<UserVO>> query(UserQueries queries) {
        val query = UserQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .listAs(UserQueriesUtil.getQueryWrapper(query),
                        UserVO.class));
    }

    @SaCheckLogin
    @SaCheckPermission({"user:query"})
    @GetMapping("/_count")
    @Operation(summary = "根据条件查询（总数）")
    public ApiResponse<Long> count(UserQueries queries) {
        val query = UserQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .count(query));
    }

    @SaCheckLogin
    @GetMapping("/{id:.+}")
    @Operation(summary = "根据主键查询")
    public ApiResponse<UserVO> getById(@PathVariable Serializable id) {
        val query = UserQueriesUtil.getQueryWrapper(id);
        return ApiResponse.ok(getService()
                .getOneAs(UserQueriesUtil.getQueryWrapper(query),
                        UserVO.class));
    }

    @SaCheckLogin
    @SaCheckPermission({"user:delete"})
    @DeleteMapping("/{id:.+}")
    @Operation(summary = "根据主键删除")
    public ApiResponse<UserVO> delete(@PathVariable Serializable id) {
        val query = UserQueriesUtil.getQueryWrapper(id);
        val data = getService()
                .getOneAsOpt(UserQueriesUtil.getQueryWrapper(query),
                        UserVO.class)
                .orElseThrow(DataNotExistException::new);
        getService()
                .removeById(id);
        return ApiResponse.ok(data);
    }
}

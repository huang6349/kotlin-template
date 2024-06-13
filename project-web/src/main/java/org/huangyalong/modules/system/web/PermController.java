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
import org.huangyalong.modules.system.domain.Perm;
import org.huangyalong.modules.system.request.PermBO;
import org.huangyalong.modules.system.request.PermQueries;
import org.huangyalong.modules.system.request.PermQueriesUtil;
import org.huangyalong.modules.system.service.PermService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/perm")
@Tag(name = "权限管理")
public class PermController extends BaseControllerImpl<PermService, Perm> {

    @SaCheckLogin
    @SaCheckPermission({"perm:add"})
    @PostMapping
    @Operation(summary = "新增单个数据")
    public ApiResponse<Boolean> add(@RequestBody @Validated PermBO payload) {
        return ApiResponse.ok(getService()
                .add(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"perm:update"})
    @PutMapping
    @Operation(summary = "修改单个数据")
    public ApiResponse<Boolean> update(@RequestBody @Validated PermBO payload) {
        return ApiResponse.ok(getService()
                .update(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"perm:query"})
    @GetMapping("/_query/paging")
    @Operation(summary = "根据条件查询（分页）")
    public ApiResponse<Page<Perm>> queryPage(PermQueries queries, PageQueries pageQueries) {
        Page<Perm> page = Page.of(Opt.ofNullable(pageQueries.getPageNumber())
                .orElse(PageQueries.DEFAULT_PAGE_NUMBER), Opt.ofNullable(pageQueries.getPageSize())
                .orElse(PageQueries.DEFAULT_PAGE_SIZE));
        val query = PermQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .page(page, query));
    }

    @SaCheckLogin
    @SaCheckPermission({"perm:query"})
    @GetMapping("/_query")
    @Operation(summary = "根据条件查询（列表）")
    public ApiResponse<List<Perm>> query(PermQueries queries) {
        val query = PermQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .list(query));
    }

    @SaCheckLogin
    @SaCheckPermission({"perm:query"})
    @GetMapping("/_count")
    @Operation(summary = "根据条件查询（总数）")
    public ApiResponse<Long> count(PermQueries queries) {
        val query = PermQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .count(query));
    }

    @SaCheckLogin
    @GetMapping("/{id:.+}")
    @Operation(summary = "根据主键查询")
    public ApiResponse<Perm> getById(@PathVariable Serializable id) {
        return ApiResponse.ok(getService()
                .getById(id));
    }

    @SaCheckLogin
    @SaCheckPermission({"perm:delete"})
    @DeleteMapping("/{id:.+}")
    @Operation(summary = "根据主键删除")
    public ApiResponse<Perm> delete(@PathVariable Serializable id) {
        val data = getService()
                .getByIdOpt(id)
                .orElseThrow(DataNotExistException::new);
        getService()
                .removeById(id);
        return ApiResponse.ok(data);
    }
}

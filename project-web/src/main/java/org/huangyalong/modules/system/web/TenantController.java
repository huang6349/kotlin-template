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
import org.huangyalong.modules.system.domain.Tenant;
import org.huangyalong.modules.system.request.TenantBO;
import org.huangyalong.modules.system.request.TenantQueries;
import org.huangyalong.modules.system.request.TenantQueriesUtil;
import org.huangyalong.modules.system.response.TenantVO;
import org.huangyalong.modules.system.service.TenantService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tenant")
@Tag(name = "租户管理")
public class TenantController extends BaseControllerImpl<TenantService, Tenant> {

    @SaCheckLogin
    @SaCheckPermission({"tenant:add"})
    @PostMapping
    @Operation(summary = "新增单个数据")
    public ApiResponse<Boolean> add(@RequestBody @Validated TenantBO payload) {
        return ApiResponse.ok(getService()
                .add(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"tenant:update"})
    @PutMapping
    @Operation(summary = "修改单个数据")
    public ApiResponse<Boolean> update(@RequestBody @Validated TenantBO payload) {
        return ApiResponse.ok(getService()
                .update(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"tenant:query"})
    @GetMapping("/_query/paging")
    @Operation(summary = "根据条件查询（分页）")
    public ApiResponse<Page<TenantVO>> queryPage(TenantQueries queries, PageQueries pageQueries) {
        Page<TenantVO> page = Page.of(Opt.ofNullable(pageQueries.getPageNumber())
                .orElse(PageQueries.DEFAULT_PAGE_NUMBER), Opt.ofNullable(pageQueries.getPageSize())
                .orElse(PageQueries.DEFAULT_PAGE_SIZE));
        val query = TenantQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .pageAs(page, TenantQueriesUtil.getQueryWrapper(query),
                        TenantVO.class));
    }

    @SaCheckLogin
    @SaCheckPermission({"tenant:query"})
    @GetMapping("/_query")
    @Operation(summary = "根据条件查询（列表）")
    public ApiResponse<List<TenantVO>> query(TenantQueries queries) {
        val query = TenantQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .listAs(TenantQueriesUtil.getQueryWrapper(query),
                        TenantVO.class));
    }

    @SaCheckLogin
    @SaCheckPermission({"tenant:query"})
    @GetMapping("/_count")
    @Operation(summary = "根据条件查询（总数）")
    public ApiResponse<Long> count(TenantQueries queries) {
        val query = TenantQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .count(query));
    }

    @SaCheckLogin
    @GetMapping("/{id:.+}")
    @Operation(summary = "根据主键查询")
    public ApiResponse<TenantVO> getById(@PathVariable Serializable id) {
        val query = TenantQueriesUtil.getQueryWrapper(id);
        return ApiResponse.ok(getService()
                .getOneAs(TenantQueriesUtil.getQueryWrapper(query),
                        TenantVO.class));
    }

    @SaCheckLogin
    @SaCheckPermission({"tenant:delete"})
    @DeleteMapping("/{id:.+}")
    @Operation(summary = "根据主键删除")
    public ApiResponse<Tenant> delete(@PathVariable Serializable id) {
        val data = getService()
                .getByIdOpt(id)
                .orElseThrow(DataNotExistException::new);
        getService()
                .removeById(id);
        return ApiResponse.ok(data);
    }
}

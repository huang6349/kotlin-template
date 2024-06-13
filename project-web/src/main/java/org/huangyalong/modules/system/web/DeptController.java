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
import org.huangyalong.core.tree.TreeQueries;
import org.huangyalong.core.web.impl.BaseControllerImpl;
import org.huangyalong.modules.system.domain.Dept;
import org.huangyalong.modules.system.request.DeptBO;
import org.huangyalong.modules.system.request.DeptQueries;
import org.huangyalong.modules.system.request.DeptQueriesUtil;
import org.huangyalong.modules.system.service.DeptService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/dept")
@Tag(name = "部门管理")
public class DeptController extends BaseControllerImpl<DeptService, Dept> {

    @SaCheckLogin
    @SaCheckPermission({"dept:add"})
    @PostMapping
    @Operation(summary = "新增单个数据")
    public ApiResponse<Boolean> add(@RequestBody @Validated DeptBO payload) {
        return ApiResponse.ok(getService()
                .add(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:update"})
    @PutMapping
    @Operation(summary = "修改单个数据")
    public ApiResponse<Boolean> update(@RequestBody @Validated DeptBO payload) {
        return ApiResponse.ok(getService()
                .update(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:query"})
    @GetMapping("/_query/paging")
    @Operation(summary = "根据条件查询（分页）")
    public ApiResponse<Page<Dept>> queryPage(DeptQueries queries, PageQueries pageQueries) {
        Page<Dept> page = Page.of(Opt.ofNullable(pageQueries.getPageNumber())
                .orElse(PageQueries.DEFAULT_PAGE_NUMBER), Opt.ofNullable(pageQueries.getPageSize())
                .orElse(PageQueries.DEFAULT_PAGE_SIZE));
        val query = DeptQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .page(page, query));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:query"})
    @GetMapping("/_query")
    @Operation(summary = "根据条件查询（列表）")
    public ApiResponse<List<Dept>> query(DeptQueries queries) {
        val query = DeptQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .list(query));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:query"})
    @GetMapping("/_query/tree")
    @Operation(summary = "根据条件查询（树形）")
    public ApiResponse<List<Dept>> query(TreeQueries treeQueries) {
        val parentId = treeQueries.getParentId();
        return ApiResponse.ok(getService()
                .listToTree(parentId));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:query"})
    @GetMapping("/_count")
    @Operation(summary = "根据条件查询（总数）")
    public ApiResponse<Long> count(DeptQueries queries) {
        val query = DeptQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .count(query));
    }

    @SaCheckLogin
    @GetMapping("/{id:.+}")
    @Operation(summary = "根据主键查询")
    public ApiResponse<Dept> getById(@PathVariable Serializable id) {
        return ApiResponse.ok(getService()
                .getById(id));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:delete"})
    @DeleteMapping("/{id:.+}")
    @Operation(summary = "根据主键删除")
    public ApiResponse<Dept> delete(@PathVariable Serializable id) {
        val data = getService()
                .getByIdOpt(id)
                .orElseThrow(DataNotExistException::new);
        getService()
                .removeById(id);
        return ApiResponse.ok(data);
    }
}

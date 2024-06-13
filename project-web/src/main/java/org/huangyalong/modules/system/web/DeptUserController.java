package org.huangyalong.modules.system.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Opt;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.huangyalong.core.commons.info.ApiResponse;
import org.huangyalong.core.request.PageQueries;
import org.huangyalong.core.web.impl.BaseControllerImpl;
import org.huangyalong.modules.system.domain.Dept;
import org.huangyalong.modules.system.request.DeptUserBO;
import org.huangyalong.modules.system.request.DeptUserQueries;
import org.huangyalong.modules.system.response.DeptUserVO;
import org.huangyalong.modules.system.service.DeptUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/dept/user")
@Tag(name = "部门管理（部门用户）")
public class DeptUserController extends BaseControllerImpl<DeptUserService, Dept> {

    @SaCheckLogin
    @SaCheckPermission({"dept:add"})
    @PostMapping
    @Operation(summary = "新增单个数据")
    public ApiResponse<Boolean> add(@RequestBody @Validated DeptUserBO payload) {
        return ApiResponse.ok(getService()
                .add(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:update"})
    @PutMapping
    @Operation(summary = "修改单个数据")
    public ApiResponse<Boolean> update(@RequestBody @Validated DeptUserBO payload) {
        return ApiResponse.ok(getService()
                .update(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:query"})
    @GetMapping("/_query/paging")
    @Operation(summary = "根据条件查询（分页）")
    public ApiResponse<Page<DeptUserVO>> queryPage(DeptUserQueries queries, PageQueries pageQueries) {
        Page<DeptUserVO> page = Page.of(Opt.ofNullable(pageQueries.getPageNumber())
                .orElse(PageQueries.DEFAULT_PAGE_NUMBER), Opt.ofNullable(pageQueries.getPageSize())
                .orElse(PageQueries.DEFAULT_PAGE_SIZE));
        return ApiResponse.ok(getService()
                .pageVO(queries, page));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:query"})
    @GetMapping("/_query")
    @Operation(summary = "根据条件查询（列表）")
    public ApiResponse<List<DeptUserVO>> query(DeptUserQueries queries) {
        return ApiResponse.ok(getService()
                .listVO(queries));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:query"})
    @GetMapping("/_count")
    @Operation(summary = "根据条件查询（总数）")
    public ApiResponse<Long> count(DeptUserQueries queries) {
        return ApiResponse.ok(getService()
                .countVO(queries));
    }

    @SaCheckLogin
    @GetMapping("/{id:.+}")
    @Operation(summary = "根据主键查询")
    public ApiResponse<DeptUserVO> getById(@PathVariable Serializable id) {
        return ApiResponse.ok(getService()
                .getVO(id));
    }

    @SaCheckLogin
    @SaCheckPermission({"dept:delete"})
    @DeleteMapping("/{id:.+}")
    @Operation(summary = "根据主键删除")
    public ApiResponse<Boolean> delete(@PathVariable Serializable id) {
        return ApiResponse.ok(getService()
                .delete(id));
    }
}

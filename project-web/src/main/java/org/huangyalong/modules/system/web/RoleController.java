package org.huangyalong.modules.system.web;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.Opt;
import cn.hutool.extra.spring.SpringUtil;
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
import org.huangyalong.modules.system.domain.Role;
import org.huangyalong.modules.system.request.PermAssocBO;
import org.huangyalong.modules.system.request.RoleBO;
import org.huangyalong.modules.system.request.RoleQueries;
import org.huangyalong.modules.system.request.RoleQueriesUtil;
import org.huangyalong.modules.system.service.PermAssocService;
import org.huangyalong.modules.system.service.PermService;
import org.huangyalong.modules.system.service.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

import static org.huangyalong.modules.system.domain.table.RoleTableDef.ROLE;

@AllArgsConstructor
@RestController
@RequestMapping("/role")
@Tag(name = "角色管理")
public class RoleController extends BaseControllerImpl<RoleService, Role> {

    @SaCheckLogin
    @SaCheckPermission({"role:perm"})
    @GetMapping("/{id:.+}/_perm")
    @Operation(summary = "根据主键查询（权限列表）")
    public ApiResponse<List<Perm>> getPermById(@PathVariable Serializable id) {
        return ApiResponse.ok(SpringUtil.getBean(PermService.class)
                .getByRoleId(id));
    }

    @SaCheckLogin
    @SaCheckPermission({"role:add"})
    @PostMapping
    @Operation(summary = "新增单个数据")
    public ApiResponse<Boolean> add(@RequestBody @Validated RoleBO payload) {
        return ApiResponse.ok(getService()
                .add(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"role:update"})
    @PutMapping
    @Operation(summary = "修改单个数据")
    public ApiResponse<Boolean> update(@RequestBody @Validated RoleBO payload) {
        return ApiResponse.ok(getService()
                .update(payload));
    }

    @SaCheckLogin
    @SaCheckPermission({"role:query"})
    @GetMapping("/_query/paging")
    @Operation(summary = "根据条件查询（分页）")
    public ApiResponse<Page<Role>> queryPage(RoleQueries queries, PageQueries pageQueries) {
        Page<Role> page = Page.of(Opt.ofNullable(pageQueries.getPageNumber())
                .orElse(PageQueries.DEFAULT_PAGE_NUMBER), Opt.ofNullable(pageQueries.getPageSize())
                .orElse(PageQueries.DEFAULT_PAGE_SIZE));
        val query = RoleQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .page(page, query));
    }

    @SaCheckLogin
    @SaCheckPermission({"role:query"})
    @GetMapping("/_query")
    @Operation(summary = "根据条件查询（列表）")
    public ApiResponse<List<Role>> query(RoleQueries queries) {
        val query = RoleQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .list(query));
    }

    @SaCheckLogin
    @SaCheckPermission({"role:query"})
    @GetMapping("/_count")
    @Operation(summary = "根据条件查询（总数）")
    public ApiResponse<Long> count(RoleQueries queries) {
        val query = RoleQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .count(query));
    }

    @SaCheckLogin
    @GetMapping("/{id:.+}")
    @Operation(summary = "根据主键查询")
    public ApiResponse<Role> getById(@PathVariable Serializable id) {
        return ApiResponse.ok(getService()
                .getById(id));
    }

    @SaCheckLogin
    @SaCheckPermission({"role:delete"})
    @DeleteMapping("/{id:.+}")
    @Operation(summary = "根据主键删除")
    public ApiResponse<Role> delete(@PathVariable Serializable id) {
        val data = getService()
                .getByIdOpt(id)
                .orElseThrow(DataNotExistException::new);
        getService()
                .removeById(id);
        return ApiResponse.ok(data);
    }

    @SaCheckLogin
    @SaCheckPermission({"role:perm"})
    @PostMapping("/perm")
    @Operation(summary = "分配权限")
    public ApiResponse<Boolean> distribute(@RequestBody @Validated PermAssocBO payload) {
        val assoc = ROLE.getTableName();
        return ApiResponse.ok(SpringUtil.getBean(PermAssocService.class)
                .assoc(payload, assoc));
    }
}

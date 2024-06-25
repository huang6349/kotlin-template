#set(controllerPackage = packageConfig.controllerPackage)
#set(servicePackage = packageConfig.servicePackage)
#set(entityPackage = packageConfig.entityPackage)
#set(basePackage = packageConfig.basePackage)
#set(queriesPackage = basePackage.concat(".request"))
#set(queriesUtilPackage = basePackage.concat(".request"))
#set(boPackage = basePackage.concat(".request"))
#set(controllerClassName = table.buildControllerClassName())
#set(serviceClassName = table.buildServiceClassName())
#set(entityClassName = table.buildEntityClassName())
#set(queriesClassName = entityClassName.concat("Queries"))
#set(queriesUtilClassName = entityClassName.concat("QueriesUtil"))
#set(boClassName = entityClassName.concat("BO"))
#set(serviceVarName = firstCharToLowerCase(serviceClassName))
#set(entityVarName = firstCharToLowerCase(entityClassName))
#set(comment = table.getComment().replace("信息", "管理"))
package #(controllerPackage);

import cn.dev33.satoken.annotation.SaCheckLogin;
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
import #(entityPackage).#(entityClassName);
import #(boPackage).#(boClassName);
import #(queriesPackage).#(queriesClassName);
import #(queriesUtilPackage).#(queriesUtilClassName);
import #(servicePackage).#(serviceClassName);
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/#(entityVarName)")
@Tag(name = "#(comment)")
public class #(controllerClassName) extends BaseControllerImpl<#(serviceClassName), #(entityClassName)> {

    @SaCheckLogin
    @PostMapping
    @Operation(summary = "新增单个数据")
    public ApiResponse<Boolean> add(@RequestBody @Validated #(boClassName) payload) {
        return ApiResponse.ok(getService()
                .add(payload));
    }

    @SaCheckLogin
    @PutMapping
    @Operation(summary = "修改单个数据")
    public ApiResponse<Boolean> update(@RequestBody @Validated #(boClassName) payload) {
        return ApiResponse.ok(getService()
                .update(payload));
    }

    @SaCheckLogin
    @GetMapping("/_query/paging")
    @Operation(summary = "根据条件查询（分页）")
    public ApiResponse<Page<#(entityClassName)>> queryPage(#(queriesClassName) queries, PageQueries pageQueries) {
        Page<#(entityClassName)> page = Page.of(Opt.ofNullable(pageQueries.getPageNumber())
                .orElse(PageQueries.DEFAULT_PAGE_NUMBER), Opt.ofNullable(pageQueries.getPageSize())
                .orElse(PageQueries.DEFAULT_PAGE_SIZE));
        val query = #(queriesUtilClassName).getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .page(page, query));
    }

    @SaCheckLogin
    @GetMapping("/_query")
    @Operation(summary = "根据条件查询（列表）")
    public ApiResponse<List<#(entityClassName)>> query(#(queriesClassName) queries) {
        val query = #(queriesUtilClassName).getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .list(query));
    }

    @SaCheckLogin
    @GetMapping("/_count")
    @Operation(summary = "根据条件查询（总数）")
    public ApiResponse<Long> count(#(queriesClassName) queries) {
        val query = #(queriesUtilClassName).getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .count(query));
    }

    @SaCheckLogin
    @GetMapping("/{id:.+}")
    @Operation(summary = "根据主键查询")
    public ApiResponse<#(entityClassName)> getById(@PathVariable Serializable id) {
        return ApiResponse.ok(getService()
                .getById(id));
    }

    @SaCheckLogin
    @DeleteMapping("/{id:.+}")
    @Operation(summary = "根据主键删除")
    public ApiResponse<#(entityClassName)> delete(@PathVariable Serializable id) {
        val data = getService()
                .getByIdOpt(id)
                .orElseThrow(DataNotExistException::new);
        getService()
                .removeById(id);
        return ApiResponse.ok(data);
    }
}

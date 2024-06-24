package org.huangyalong.modules.example.web;

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
import org.huangyalong.modules.example.domain.Example;
import org.huangyalong.modules.example.request.ExampleBO;
import org.huangyalong.modules.example.request.ExampleQueries;
import org.huangyalong.modules.example.request.ExampleQueriesUtil;
import org.huangyalong.modules.example.service.ExampleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/example")
@Tag(name = "示例管理")
public class ExampleController extends BaseControllerImpl<ExampleService, Example> {

    @SaCheckLogin
    @PostMapping
    @Operation(summary = "新增单个数据")
    public ApiResponse<Boolean> add(@RequestBody @Validated ExampleBO payload) {
        return ApiResponse.ok(getService()
                .add(payload));
    }

    @SaCheckLogin
    @PutMapping
    @Operation(summary = "修改单个数据")
    public ApiResponse<Boolean> update(@RequestBody @Validated ExampleBO payload) {
        return ApiResponse.ok(getService()
                .update(payload));
    }

    @SaCheckLogin
    @GetMapping("/_query/paging")
    @Operation(summary = "根据条件查询（分页）")
    public ApiResponse<Page<Example>> queryPage(ExampleQueries queries, PageQueries pageQueries) {
        Page<Example> page = Page.of(Opt.ofNullable(pageQueries.getPageNumber())
                .orElse(PageQueries.DEFAULT_PAGE_NUMBER), Opt.ofNullable(pageQueries.getPageSize())
                .orElse(PageQueries.DEFAULT_PAGE_SIZE));
        val query = ExampleQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .page(page, query));
    }

    @SaCheckLogin
    @GetMapping("/_query")
    @Operation(summary = "根据条件查询（列表）")
    public ApiResponse<List<Example>> query(ExampleQueries queries) {
        val query = ExampleQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .list(query));
    }

    @SaCheckLogin
    @GetMapping("/_count")
    @Operation(summary = "根据条件查询（总数）")
    public ApiResponse<Long> count(ExampleQueries queries) {
        val query = ExampleQueriesUtil.getQueryWrapper(queries);
        return ApiResponse.ok(getService()
                .count(query));
    }

    @SaCheckLogin
    @GetMapping("/{id:.+}")
    @Operation(summary = "根据主键查询")
    public ApiResponse<Example> getById(@PathVariable Serializable id) {
        return ApiResponse.ok(getService()
                .getById(id));
    }

    @SaCheckLogin
    @DeleteMapping("/{id:.+}")
    @Operation(summary = "根据主键删除")
    public ApiResponse<Example> delete(@PathVariable Serializable id) {
        val data = getService()
                .getByIdOpt(id)
                .orElseThrow(DataNotExistException::new);
        getService()
                .removeById(id);
        return ApiResponse.ok(data);
    }
}

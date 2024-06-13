package org.huangyalong.modules.system.web;

import cn.hutool.core.lang.Opt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.exception.DataNotExistException;
import org.huangyalong.core.commons.info.ApiResponse;
import org.huangyalong.core.dict.DictCache;
import org.huangyalong.core.dict.DictDefine;
import org.huangyalong.core.dict.ItemDefine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/dict")
@Tag(name = "字典管理")
public class DictController {

    @GetMapping("/{category:.+}/items")
    @Operation(summary = "根据主键查询（选项）")
    public ApiResponse<List<ItemDefine>> queryItems(@PathVariable String category) {
        val data = Opt.ofNullable(DictCache
                        .query(category))
                .map(DictDefine::getItems)
                .orElseThrow(DataNotExistException::new);
        return ApiResponse.ok(data);
    }

    @GetMapping("/{category:.+}")
    @Operation(summary = "根据主键查询")
    public ApiResponse<DictDefine> query(@PathVariable String category) {
        val data = Opt.ofNullable(DictCache
                        .query(category))
                .orElseThrow(DataNotExistException::new);
        return ApiResponse.ok(data);
    }
}

package org.huangyalong.modules.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.request.BaseQueries;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "部门查询")
public class DeptQueries implements BaseQueries {

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "部门代码")
    private String code;
}

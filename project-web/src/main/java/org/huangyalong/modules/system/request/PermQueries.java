package org.huangyalong.modules.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.request.BaseQueries;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "权限查询")
public class PermQueries implements BaseQueries {

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限代码")
    private String code;
}

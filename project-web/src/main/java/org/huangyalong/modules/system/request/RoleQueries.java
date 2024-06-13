package org.huangyalong.modules.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.request.BaseQueries;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "角色查询")
public class RoleQueries implements BaseQueries {

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色代码")
    private String code;
}

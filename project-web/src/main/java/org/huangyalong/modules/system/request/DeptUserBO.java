package org.huangyalong.modules.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.request.AbstractBaseBO;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "用户信息（部门用户）")
public class DeptUserBO extends AbstractBaseBO {

    @NotNull(message = "帐号不能为空")
    @Schema(description = "所属帐号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long accountId;

    @NotNull(message = "部门不能为空")
    @Schema(description = "所属部门", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deptId;

    @Schema(description = "备注")
    private String desc;
}

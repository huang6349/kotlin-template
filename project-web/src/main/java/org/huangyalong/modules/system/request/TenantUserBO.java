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
@Schema(name = "用户信息（租户用户）")
public class TenantUserBO extends AbstractBaseBO {

    @NotNull(message = "帐号不能为空")
    @Schema(description = "所属帐号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long accountId;

    @NotNull(message = "租户不能为空")
    @Schema(description = "所属租户", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long tenantId;

    @NotNull(message = "角色不能为空")
    @Schema(description = "所属角色", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long roleId;

    @Schema(description = "备注")
    private String desc;
}

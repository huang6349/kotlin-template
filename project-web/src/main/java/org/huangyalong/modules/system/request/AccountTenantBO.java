package org.huangyalong.modules.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "租户切换")
public class AccountTenantBO implements Serializable {

    @NotNull(message = "租户不能为空")
    @Schema(description = "所属租户", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long tenantId;
}

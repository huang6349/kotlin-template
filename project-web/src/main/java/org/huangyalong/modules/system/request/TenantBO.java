package org.huangyalong.modules.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.request.AbstractBaseBO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "租户信息")
public class TenantBO extends AbstractBaseBO {

    @NotBlank(message = "名称不能为空")
    @Size(max = 50, message = "名称的长度只能小于50个字符")
    @Schema(description = "租户名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "简称不能为空")
    @Size(max = 4, message = "简称的长度只能小于4个字符")
    @Schema(description = "租户简称")
    private String abbr;

    @NotBlank(message = "类别不能为空")
    @Schema(description = "租户类别")
    private String category;

    @NotBlank(message = "地区不能为空")
    @Schema(description = "租户地区")
    private String area;

    @Schema(description = "租户地址")
    private String address;

    @Schema(description = "备注")
    private String desc;
}

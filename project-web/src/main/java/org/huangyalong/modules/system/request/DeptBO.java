package org.huangyalong.modules.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.tree.AbstractTreeBO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "部门信息")
public class DeptBO extends AbstractTreeBO {

    @NotBlank(message = "名称不能为空")
    @Size(max = 50, message = "名称的长度只能小于50个字符")
    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "代码不能为空")
    @Pattern(regexp = RegexpConstants.CODE, message = "错误的代码格式")
    @Size(max = 50, message = "代码的长度只能小于50个字符")
    @Schema(description = "部门代码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "备注")
    private String desc;
}

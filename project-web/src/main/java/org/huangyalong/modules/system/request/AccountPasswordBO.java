package org.huangyalong.modules.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "修改密码")
public class AccountPasswordBO implements Serializable {

    @NotBlank(message = "旧的密码不能为空")
    @Schema(description = "旧的密码", requiredMode = RequiredMode.REQUIRED)
    private String oldPassword;

    @NotBlank(message = "新的密码不能为空")
    @Pattern(regexp = RegexpConstants.PASSWORD, message = "错误的密码格式")
    @Schema(description = "新的密码", requiredMode = RequiredMode.REQUIRED)
    private String newPassword;

    @Schema(description = "确认密码")
    private String confirm;
}

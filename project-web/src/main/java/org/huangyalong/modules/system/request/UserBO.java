package org.huangyalong.modules.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.request.AbstractBaseBO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "用户信息")
public class UserBO extends AbstractBaseBO {

    @NotBlank(message = "帐号不能为空")
    @Pattern(regexp = RegexpConstants.USERNAME, message = "错误的帐号格式")
    @Schema(description = "用户帐号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = RegexpConstants.PASSWORD, message = "错误的密码格式")
    @Schema(description = "用户密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password1;

    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password2;

    @NotBlank(message = "昵称不能为空")
    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickname;

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = RegexpConstants.MOBILE, message = "错误的手机号码格式")
    @Schema(description = "手机号码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;

    @Email(message = "错误的邮箱格式")
    @Schema(description = "用户邮箱")
    private String email;

    @Schema(description = "用户性别")
    private String gender;

    @PastOrPresent(message = "错误的生日格式")
    @Schema(description = "用户生日")
    private Date birthday;

    @Schema(description = "用户地址")
    private String address;

    @NotBlank(message = "管理密码不能为空")
    @Pattern(regexp = RegexpConstants.PASSWORD, message = "错误的管理密码格式")
    @Schema(description = "管理密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password3;
}

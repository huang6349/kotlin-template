package org.huangyalong.modules.system.domain;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.lang.Opt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.huangyalong.core.dict.JKDictFormat;
import org.huangyalong.core.domain.AbstractBaseEntity;
import org.huangyalong.modules.system.enums.AccountStatus;
import org.huangyalong.modules.system.request.UserBO;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@Table(value = "tb_account")
@Schema(name = "帐号信息")
public class Account extends AbstractBaseEntity<Account> {

    @Schema(description = "用户帐号")
    private String username;

    @Schema(description = "用户密码")
    private String password;

    @JsonIgnore
    @Schema(description = "盐")
    private String salt;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "用户邮箱")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "登录时间")
    private Date loginTime;

    @JKDictFormat
    @Schema(description = "帐号状态")
    private AccountStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "租户主键")
    private Long tenantId;

    /****************** with ******************/

    @SuppressWarnings("DuplicatedCode")
    public Account with(UserBO userBO) {
        setSalt(BCrypt.gensalt());
        Opt.ofNullable(userBO)
                .map(UserBO::getPassword1)
                .map(password -> BCrypt.hashpw(password, getSalt()))
                .ifPresent(this::setPassword);
        Opt.ofNullable(userBO)
                .map(UserBO::getMobile)
                .ifPresent(this::setMobile);
        Opt.ofNullable(userBO)
                .map(UserBO::getEmail)
                .ifPresent(this::setEmail);
        return this;
    }
}

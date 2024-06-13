package org.huangyalong.modules.system.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.dict.JKDictFormat;
import org.huangyalong.modules.system.enums.AccountStatus;
import org.huangyalong.modules.system.enums.UserGender;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "帐号信息")
public class UserVO implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "数据主键")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "租户主键")
    private Long tenantId;

    @Schema(description = "用户帐号")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "手机号码")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "真实姓名")
    private String realname;

    @Schema(description = "身份证号")
    private String idCard;

    @Schema(description = "租户名称")
    private String tenantName;

    @JKDictFormat
    @Schema(description = "用户性别")
    private UserGender gender;

    @Schema(description = "用户生日")
    private Date birthday;

    @Schema(description = "用户地址")
    private String address;

    @Schema(description = "备注")
    private String desc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "登录时间")
    private Date loginTime;

    @JKDictFormat
    @Schema(description = "帐号状态")
    private AccountStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private Date updateTime;
}

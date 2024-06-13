package org.huangyalong.modules.system.domain;

import cn.hutool.core.lang.Opt;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.huangyalong.core.dict.JKDictFormat;
import org.huangyalong.core.domain.AbstractBaseEntity;
import org.huangyalong.modules.system.enums.UserGender;
import org.huangyalong.modules.system.request.UserBO;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@Table(value = "tb_user")
@Schema(name = "用户信息")
public class User extends AbstractBaseEntity<User> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "帐号主键")
    private Long accountId;

    @Schema(description = "真实姓名")
    private String realname;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "身份证号")
    private String idCard;

    @JKDictFormat
    @Schema(description = "用户性别")
    private UserGender gender;

    @Schema(description = "用户生日")
    private Date birthday;

    @Schema(description = "用户地址")
    private String address;

    @Schema(description = "备注")
    private String desc;

    /****************** with ******************/

    @SuppressWarnings("DuplicatedCode")
    public User with(UserBO userBO) {
        Opt.ofNullable(userBO)
                .map(UserBO::getNickname)
                .ifPresent(this::setNickname);
        Opt.ofNullable(userBO)
                .map(UserBO::getGender)
                .map(UserGender::getEnumDict)
                .ifPresent(this::setGender);
        Opt.ofNullable(userBO)
                .map(UserBO::getBirthday)
                .ifPresent(this::setBirthday);
        Opt.ofNullable(userBO)
                .map(UserBO::getAddress)
                .ifPresent(this::setAddress);
        return this;
    }
}

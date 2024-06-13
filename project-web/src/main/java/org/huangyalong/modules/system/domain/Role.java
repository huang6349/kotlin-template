package org.huangyalong.modules.system.domain;

import cn.hutool.core.lang.Opt;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.huangyalong.core.dict.JKDictFormat;
import org.huangyalong.core.domain.AbstractBaseEntity;
import org.huangyalong.modules.system.enums.RoleStatus;
import org.huangyalong.modules.system.request.RoleBO;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@Table(value = "tb_role")
@Schema(name = "角色信息")
public class Role extends AbstractBaseEntity<Role> {

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色代码")
    private String code;

    @Schema(description = "备注")
    private String desc;

    @JKDictFormat
    @Schema(description = "角色状态")
    private RoleStatus status;

    /****************** with ******************/

    @SuppressWarnings("DuplicatedCode")
    public Role with(RoleBO roleBO) {
        Opt.ofNullable(roleBO)
                .map(RoleBO::getName)
                .ifPresent(this::setName);
        Opt.ofNullable(roleBO)
                .map(RoleBO::getCode)
                .ifPresent(this::setCode);
        Opt.ofNullable(roleBO)
                .map(RoleBO::getDesc)
                .ifPresent(this::setDesc);
        return this;
    }
}

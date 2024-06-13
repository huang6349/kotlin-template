package org.huangyalong.modules.system.domain;

import cn.hutool.core.lang.Opt;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.huangyalong.core.dict.JKDictFormat;
import org.huangyalong.core.domain.AbstractBaseEntity;
import org.huangyalong.modules.system.enums.PermStatus;
import org.huangyalong.modules.system.request.PermBO;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@Table(value = "tb_perm")
@Schema(name = "权限信息")
public class Perm extends AbstractBaseEntity<Perm> {

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限代码")
    private String code;

    @Schema(description = "备注")
    private String desc;

    @JKDictFormat
    @Schema(description = "权限状态")
    private PermStatus status;

    /****************** with ******************/

    @SuppressWarnings("DuplicatedCode")
    public Perm with(PermBO permBO) {
        Opt.ofNullable(permBO)
                .map(PermBO::getName)
                .ifPresent(this::setName);
        Opt.ofNullable(permBO)
                .map(PermBO::getCode)
                .ifPresent(this::setCode);
        Opt.ofNullable(permBO)
                .map(PermBO::getDesc)
                .ifPresent(this::setDesc);
        return this;
    }
}

package org.huangyalong.modules.system.domain;

import cn.hutool.core.lang.Opt;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.huangyalong.core.dict.JKDictFormat;
import org.huangyalong.core.domain.AbstractBaseEntity;
import org.huangyalong.modules.system.enums.TenantCategory;
import org.huangyalong.modules.system.enums.TenantStatus;
import org.huangyalong.modules.system.request.TenantBO;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@Table(value = "tb_tenant")
@Schema(name = "租户信息")
public class Tenant extends AbstractBaseEntity<Tenant> {

    @Schema(description = "租户名称")
    private String name;

    @Schema(description = "租户头像")
    private String avatar;

    @JKDictFormat
    @Schema(description = "租户类别")
    private TenantCategory category;

    @Schema(description = "租户地址")
    private String address;

    @Schema(description = "备注")
    private String desc;

    @JKDictFormat
    @Schema(description = "租户状态")
    private TenantStatus status;

    /****************** with ******************/

    @SuppressWarnings("DuplicatedCode")
    public Tenant with(TenantBO tenantBO) {
        Opt.ofNullable(tenantBO)
                .map(TenantBO::getName)
                .ifPresent(this::setName);
        Opt.ofNullable(tenantBO)
                .map(TenantBO::getCategory)
                .map(TenantCategory::getEnumDict)
                .ifPresent(this::setCategory);
        Opt.ofNullable(tenantBO)
                .map(TenantBO::getAddress)
                .ifPresent(this::setAddress);
        Opt.ofNullable(tenantBO)
                .map(TenantBO::getDesc)
                .ifPresent(this::setDesc);
        return this;
    }
}

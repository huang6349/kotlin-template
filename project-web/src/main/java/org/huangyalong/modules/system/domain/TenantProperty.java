package org.huangyalong.modules.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@Table(value = "tb_tenant_property")
@Schema(name = "租户属性")
public class TenantProperty extends Model<TenantProperty> {

    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "数据主键")
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "租户主键")
    private Long tenantId;

    @Schema(description = "属性分组")
    private String group;

    @Schema(description = "属性名称")
    private String name;

    @Schema(description = "属性数据")
    private String data;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private Date updateTime;
}

package org.huangyalong.modules.system.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.dict.JKDictFormat;
import org.huangyalong.modules.system.enums.TenantCategory;
import org.huangyalong.modules.system.enums.TenantStatus;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "租户信息")
public class TenantVO implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "数据主键")
    private Long id;

    @Schema(description = "租户名称")
    private String name;

    @Schema(description = "租户简称")
    private String abbr;

    @Schema(description = "租户头像")
    private String avatar;

    @JKDictFormat
    @Schema(description = "租户类别")
    private TenantCategory category;

    @Schema(description = "租户地区")
    private String area;

    @Schema(description = "租户地址")
    private String address;

    @Schema(description = "备注")
    private String desc;

    @JKDictFormat
    @Schema(description = "租户状态")
    private TenantStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private Date updateTime;
}

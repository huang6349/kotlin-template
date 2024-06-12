package org.huangyalong.core.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "字典信息")
public class ItemDefine implements Serializable {

    @Schema(description = "字典名称")
    private String label;

    @Schema(description = "字典键值")
    private Object value;

    @Schema(description = "是否缺省")
    private Boolean isDefault;
}

package org.huangyalong.modules.system.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.huangyalong.core.dict.EnumDict;

import static org.huangyalong.core.dict.IsDefault.NO;
import static org.huangyalong.core.dict.IsDefault.YES;
import static org.huangyalong.core.dict.Style.PRIMARY;
import static org.huangyalong.core.dict.Style.WARNING;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum RoleStatus implements EnumDict<String> {
    TYPE0("0", "启用", 0, YES.getValue(), PRIMARY.getValue()),
    TYPE1("1", "禁用", 0, NO.getValue(), WARNING.getValue());

    @EnumValue
    @JsonValue
    private String value;

    private String label;

    private Integer sort;

    private Integer isDefault;

    private Integer style;
}

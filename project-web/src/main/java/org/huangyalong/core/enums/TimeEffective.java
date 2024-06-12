package org.huangyalong.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.huangyalong.core.dict.Dict;
import org.huangyalong.core.dict.EnumDict;

import static org.huangyalong.core.dict.IsDefault.NO;
import static org.huangyalong.core.dict.IsDefault.YES;
import static org.huangyalong.core.dict.Style.PRIMARY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Dict(name = "限制时间")
public enum TimeEffective implements EnumDict<String> {
    TYPE0("0", "不限制", 0, YES.getValue(), PRIMARY.getValue()),
    TYPE1("1", "限制", 0, NO.getValue(), PRIMARY.getValue());

    @EnumValue
    @JsonValue
    private String value;

    private String label;

    private Integer sort;

    private Integer isDefault;

    private Integer style;
}

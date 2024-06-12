package org.huangyalong.core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.huangyalong.core.dict.Dict;
import org.huangyalong.core.dict.EnumDict;

import static org.huangyalong.core.dict.IsDefault.YES;
import static org.huangyalong.core.dict.Style.PRIMARY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Dict(name = "关联类别")
public enum AssocCategory implements EnumDict<String> {
    TYPE0("0", "默认类别", 0, YES.getValue(), PRIMARY.getValue());

    @EnumValue
    @JsonValue
    private String value;

    private String label;

    private Integer sort;

    private Integer isDefault;

    private Integer style;
}

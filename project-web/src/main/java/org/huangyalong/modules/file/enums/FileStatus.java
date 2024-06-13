package org.huangyalong.modules.file.enums;

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
public enum FileStatus implements EnumDict<String> {
    TYPE0("0", "未使用", 0, YES.getValue(), WARNING.getValue()),
    TYPE1("1", "已使用", 0, NO.getValue(), PRIMARY.getValue());

    @EnumValue
    @JsonValue
    private String value;

    private String label;

    private Integer sort;

    private Integer isDefault;

    private Integer style;
}

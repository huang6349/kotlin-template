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
import static org.huangyalong.core.dict.Style.ERROR;
import static org.huangyalong.core.dict.Style.PRIMARY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Dict(name = "是否删除")
public enum IsDeleted implements EnumDict<String> {
    TYPE0("0", "未删", 0, YES.getValue(), PRIMARY.getValue()),
    TYPE1("1", "已删", 0, NO.getValue(), ERROR.getValue());

    @EnumValue
    @JsonValue
    private String value;

    private String label;

    private Integer sort;

    private Integer isDefault;

    private Integer style;
}

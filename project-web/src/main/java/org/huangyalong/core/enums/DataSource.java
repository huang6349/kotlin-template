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
@Dict(name = "数据来源")
public enum DataSource implements EnumDict<String> {
    TYPE0("0", "人工录入", 0, YES.getValue(), PRIMARY.getValue()),
    TYPE1("1", "人工上传", 0, NO.getValue(), PRIMARY.getValue());

    @EnumValue
    @JsonValue
    private String value;

    private String label;

    private Integer sort;

    private Integer isDefault;

    private Integer style;
}

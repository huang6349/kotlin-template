package org.huangyalong.modules.system.enums;

import cn.hutool.core.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mybatisflex.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.huangyalong.core.dict.Dict;
import org.huangyalong.core.dict.EnumDict;
import org.huangyalong.core.dict.IsDefault;

import static org.huangyalong.core.dict.IsDefault.NO;
import static org.huangyalong.core.dict.Style.DEFAULT;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Dict(name = "用户性别")
public enum UserGender implements EnumDict<String> {
    TYPE0("0", "男", 0, NO.getValue(), DEFAULT.getValue()),
    TYPE1("1", "女", 0, NO.getValue(), DEFAULT.getValue()),
    TYPE2("2", "其他", 1, NO.getValue(), DEFAULT.getValue());

    @EnumValue
    @JsonValue
    private String value;

    private String label;

    private Integer sort;

    private Integer isDefault;

    private Integer style;

    public static UserGender getEnumDict(String value) {
        return EnumUtil.getBy(UserGender::getValue,
                value,
                EnumUtil.getBy(UserGender::getIsDefault,
                        IsDefault.YES.getValue()));
    }
}

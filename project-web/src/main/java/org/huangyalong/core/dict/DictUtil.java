package org.huangyalong.core.dict;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.val;

import java.util.ArrayList;
import java.util.Comparator;

public class DictUtil {

    public static DictDefine parse(Class<?> clazz) {
        val dict = AnnotationUtil.getAnnotation(clazz, Dict.class);
        val items = new ArrayList<ItemDefine>();
        CollUtil.newArrayList(clazz.getEnumConstants()).stream()
                .filter(enumConstant -> enumConstant instanceof EnumDict)
                .map(enumConstant -> (EnumDict<?>) enumConstant)
                .sorted(Comparator.comparing(EnumDict::getSort))
                .map(DictUtil::parse)
                .forEach(items::add);
        val define = new DictDefine();
        define.setName(dict.name());
        define.setCategory(StrUtil.isEmpty(dict.category()) ?
                StrUtil.toSymbolCase(clazz.getSimpleName(), '-') :
                dict.category());
        define.setItems(items);
        return define;
    }

    public static ItemDefine parse(EnumDict<?> enumDict) {
        val itemDefine = new ItemDefine();
        itemDefine.setLabel(enumDict.getLabel());
        itemDefine.setValue(enumDict.getValue());
        val isDefault = ObjectUtil.equal(IsDefault.YES.getValue(),
                enumDict.getIsDefault());
        itemDefine.setIsDefault(isDefault);
        return itemDefine;
    }
}

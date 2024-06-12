package org.huangyalong.core.dict;

public interface EnumDict<V> {

    String getLabel();

    V getValue();

    Integer getSort();

    Integer getIsDefault();

    Integer getStyle();
}

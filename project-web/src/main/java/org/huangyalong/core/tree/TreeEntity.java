package org.huangyalong.core.tree;

import org.huangyalong.core.domain.BaseEntity;

import java.util.List;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface TreeEntity<T extends TreeEntity<T>>
        extends BaseEntity<T> {

    Long getParentId();

    T setParentId(Long parentId);

    List<T> getChildren();

    T setChildren(List<T> children);

    String getPath();

    T setPath(String path);

    Integer getSort();

    T setSort(Integer sort);
}

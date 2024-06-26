package org.huangyalong.core.tree;

import org.huangyalong.core.request.BaseBO;

@SuppressWarnings("unused")
public interface TreeBO extends BaseBO {

    Long getParentId();

    void setParentId(Long parentId);

    Integer getSort();

    void setSort(Integer sort);
}

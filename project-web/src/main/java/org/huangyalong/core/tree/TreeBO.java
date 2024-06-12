package org.huangyalong.core.tree;

import org.huangyalong.core.request.BaseBO;

public interface TreeBO extends BaseBO {

    Long getParentId();

    void setParentId(Long parentId);

    Integer getSort();

    void setSort(Integer sort);
}

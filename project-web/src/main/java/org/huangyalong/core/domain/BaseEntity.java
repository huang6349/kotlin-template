package org.huangyalong.core.domain;

import org.huangyalong.core.enums.IsDeleted;

import java.util.Date;

@SuppressWarnings("unused")
public interface BaseEntity<T extends BaseEntity<T>> extends Entity<T> {

    Long getId();

    T setId(Long id);

    Date getCreateTime();

    T setCreateTime(Date createTime);

    Date getUpdateTime();

    T setUpdateTime(Date updateTime);

    Long getVersion();

    T setVersion(Long version);

    IsDeleted getIsDeleted();

    T setIsDeleted(IsDeleted isDeleted);
}

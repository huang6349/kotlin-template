package org.huangyalong.core.domain;

import lombok.val;

import java.io.Serializable;

@SuppressWarnings("unused")
public interface Entity<T extends Entity<T>> extends Serializable {

    default T self() {
        @SuppressWarnings("unchecked")
        val $lombok$self = (T) this;
        return $lombok$self;
    }
}

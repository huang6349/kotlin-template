package org.huangyalong.core.web;

import com.mybatisflex.core.service.IService;

public interface BaseController<E> {

    IService<E> getService();
}

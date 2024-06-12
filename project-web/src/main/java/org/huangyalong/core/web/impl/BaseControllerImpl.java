package org.huangyalong.core.web.impl;

import com.mybatisflex.core.service.IService;
import org.huangyalong.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection"})
public class BaseControllerImpl<S extends IService<E>, E>
        implements BaseController<E> {

    @Autowired
    protected S service;

    @Override
    public S getService() {
        return service;
    }
}

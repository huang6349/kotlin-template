package org.huangyalong.core.web;

import com.mybatisflex.core.service.IService;
import org.huangyalong.core.AbstractIntegrationTest;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection"})
public class AbstractControllerTest<S extends IService<E>, E> extends AbstractIntegrationTest
        implements BaseController<E> {

    @Autowired
    protected S service;

    @NotNull
    public S getService() {
        return service;
    }
}

package org.huangyalong.core.web;

import com.mybatisflex.core.service.IService;
import org.huangyalong.core.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class AbstractControllerTest<S extends IService<E>, E> extends AbstractIntegrationTest
        implements BaseController<E> {

    @Autowired
    protected S service;

    @Override
    public S getService() {
        return service;
    }
}

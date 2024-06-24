package org.huangyalong.modules.example.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.example.domain.Example;
import org.huangyalong.modules.example.request.ExampleBO;

public interface ExampleService extends IService<Example> {

    boolean add(ExampleBO payload);

    boolean update(ExampleBO payload);
}

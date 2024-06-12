package org.huangyalong.core.tree;

import com.mybatisflex.core.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class TreeServiceImpl<M extends BaseMapper<T>, T extends TreeEntity<T>>
        implements TreeIService<T> {

    @Autowired
    protected M mapper;

    @Override
    public BaseMapper<T> getMapper() {
        return mapper;
    }
}

package org.huangyalong.modules.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.modules.example.domain.Example;
import org.huangyalong.modules.example.mapper.ExampleMapper;
import org.huangyalong.modules.example.request.ExampleBO;
import org.huangyalong.modules.example.service.ExampleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ExampleServiceImpl extends ServiceImpl<ExampleMapper, Example>
        implements ExampleService {

    @Transactional(rollbackFor = Exception.class)
    public boolean add(ExampleBO payload) {
        val data = BeanUtil.copyProperties(payload, Example.class);
        return super.save(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(ExampleBO payload) {
        val data = BeanUtil.copyProperties(payload, Example.class);
        return super.updateById(data);
    }
}

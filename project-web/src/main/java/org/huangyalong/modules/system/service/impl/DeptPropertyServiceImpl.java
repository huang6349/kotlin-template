package org.huangyalong.modules.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.huangyalong.modules.system.domain.DeptProperty;
import org.huangyalong.modules.system.mapper.DeptPropertyMapper;
import org.huangyalong.modules.system.service.DeptPropertyService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeptPropertyServiceImpl extends ServiceImpl<DeptPropertyMapper, DeptProperty>
        implements DeptPropertyService {
}

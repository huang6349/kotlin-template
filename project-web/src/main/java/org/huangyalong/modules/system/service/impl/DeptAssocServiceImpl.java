package org.huangyalong.modules.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.huangyalong.modules.system.domain.DeptAssoc;
import org.huangyalong.modules.system.mapper.DeptAssocMapper;
import org.huangyalong.modules.system.service.DeptAssocService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DeptAssocServiceImpl extends ServiceImpl<DeptAssocMapper, DeptAssoc>
        implements DeptAssocService {
}

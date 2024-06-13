package org.huangyalong.modules.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.huangyalong.modules.system.domain.RoleAssoc;
import org.huangyalong.modules.system.mapper.RoleAssocMapper;
import org.huangyalong.modules.system.service.RoleAssocService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleAssocServiceImpl extends ServiceImpl<RoleAssocMapper, RoleAssoc>
        implements RoleAssocService {
}

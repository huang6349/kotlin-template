package org.huangyalong.modules.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.huangyalong.modules.system.domain.Role;
import org.huangyalong.modules.system.mapper.RoleMapper;
import org.huangyalong.modules.system.service.AccountRoleService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountRoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements AccountRoleService {
}

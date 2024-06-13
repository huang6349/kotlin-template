package org.huangyalong.modules.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.huangyalong.modules.system.domain.Perm;
import org.huangyalong.modules.system.mapper.PermMapper;
import org.huangyalong.modules.system.service.AccountPermService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountPermServiceImpl extends ServiceImpl<PermMapper, Perm>
        implements AccountPermService {
}

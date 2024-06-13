package org.huangyalong.modules.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.huangyalong.modules.system.domain.TenantAssoc;
import org.huangyalong.modules.system.mapper.TenantAssocMapper;
import org.huangyalong.modules.system.service.TenantAssocService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TenantAssocServiceImpl extends ServiceImpl<TenantAssocMapper, TenantAssoc>
        implements TenantAssocService {
}

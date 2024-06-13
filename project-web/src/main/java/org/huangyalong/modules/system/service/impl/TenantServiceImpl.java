package org.huangyalong.modules.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.exception.DataNotExistException;
import org.huangyalong.modules.system.domain.Tenant;
import org.huangyalong.modules.system.mapper.TenantMapper;
import org.huangyalong.modules.system.request.TenantBO;
import org.huangyalong.modules.system.service.TenantPropertyService;
import org.huangyalong.modules.system.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant>
        implements TenantService {

    private final TenantPropertyService propertyService;

    @Transactional(rollbackFor = Exception.class)
    public boolean add(TenantBO payload) {
        val data = Tenant.create()
                .with(payload);
        super.save(data);
        propertyService.add(payload,
                data.getId());
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(TenantBO payload) {
        val data = super.getByIdOpt(payload.getId())
                .orElseThrow(DataNotExistException::new)
                .with(payload);
        super.updateById(data);
        propertyService.add(payload);
        return Boolean.TRUE;
    }
}

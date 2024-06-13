package org.huangyalong.modules.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.modules.system.domain.TenantPropertiesData;
import org.huangyalong.modules.system.domain.TenantProperty;
import org.huangyalong.modules.system.mapper.TenantPropertyMapper;
import org.huangyalong.modules.system.request.TenantBO;
import org.huangyalong.modules.system.service.TenantPropertyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.huangyalong.modules.system.domain.table.TenantPropertyTableDef.TENANT_PROPERTY;

@AllArgsConstructor
@Service
public class TenantPropertyServiceImpl extends ServiceImpl<TenantPropertyMapper, TenantProperty>
        implements TenantPropertyService {

    @Transactional(rollbackFor = Exception.class)
    public void add(TenantBO payload, Long tenantId) {
        super.remove(QueryWrapper.create()
                .where(TENANT_PROPERTY.TENANT_ID.eq(tenantId))
                .and(TENANT_PROPERTY.GROUP.eq(TenantPropertiesData.GROUP_TENANT)));
        val properties = TenantPropertiesData.create()
                .setTenantId(tenantId)
                .addAbbr(payload.getAbbr())
                .addArea(payload.getArea())
                .getProperties();
        super.saveBatch(properties);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(TenantBO payload) {
        add(payload, payload.getId());
    }
}

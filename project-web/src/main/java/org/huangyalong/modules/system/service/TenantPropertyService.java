package org.huangyalong.modules.system.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.TenantProperty;
import org.huangyalong.modules.system.request.TenantBO;

public interface TenantPropertyService extends IService<TenantProperty> {

    void add(TenantBO payload, Long tenantId);

    void add(TenantBO payload);
}

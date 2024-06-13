package org.huangyalong.modules.system.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.Tenant;
import org.huangyalong.modules.system.request.TenantBO;

public interface TenantService extends IService<Tenant> {

    boolean add(TenantBO payload);

    boolean update(TenantBO payload);
}

package org.huangyalong.modules.system.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.Tenant;
import org.huangyalong.modules.system.request.AccountTenantBO;

import java.io.Serializable;
import java.util.List;

public interface AccountTenantService extends IService<Tenant> {

    boolean updateByAccountId(AccountTenantBO payload, Serializable id);

    boolean updateByAccountId(AccountTenantBO payload, Object id);

    boolean updateByAccountId(AccountTenantBO payload);

    List<Tenant> getByAccountId(Serializable id);

    List<Tenant> getByAccountId(Object id);

    List<Tenant> getByAccountId();
}

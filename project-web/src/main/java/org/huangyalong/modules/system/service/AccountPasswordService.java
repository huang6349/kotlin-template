package org.huangyalong.modules.system.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.request.AccountPasswordBO;

public interface AccountPasswordService extends IService<Account> {

    boolean update(AccountPasswordBO payload);
}

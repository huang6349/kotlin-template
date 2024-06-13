package org.huangyalong.modules.system.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.request.UserBO;

public interface AccountService extends IService<Account> {

    String loadNickname(Object id);

    String loadNickname();

    String loadLoginIdAsString();

    Long loadLoginId();

    boolean add(UserBO payload);

    boolean update(UserBO payload);
}

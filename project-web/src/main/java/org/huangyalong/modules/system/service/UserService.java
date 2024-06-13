package org.huangyalong.modules.system.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.User;

import java.io.Serializable;
import java.util.Optional;

public interface UserService extends IService<User> {

    Optional<User> getByAccountIdOpt(Serializable id);

    User getByAccountId(Serializable id);
}

package org.huangyalong.modules.system.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.Perm;
import org.huangyalong.modules.system.request.PermBO;

import java.io.Serializable;
import java.util.List;

public interface PermService extends IService<Perm> {

    List<Perm> getByAccountId(Serializable id);

    List<Perm> getByRoleId(Serializable id);

    boolean add(PermBO payload);

    boolean update(PermBO payload);
}

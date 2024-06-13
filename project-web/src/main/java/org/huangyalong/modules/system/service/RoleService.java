package org.huangyalong.modules.system.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.Role;
import org.huangyalong.modules.system.request.RoleBO;

import java.io.Serializable;
import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> getByAccountId(Serializable id);

    List<Role> getByAccountId(Object id);

    boolean add(RoleBO payload);

    boolean update(RoleBO payload);
}

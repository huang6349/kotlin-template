package org.huangyalong.modules.system.service;

import org.huangyalong.core.tree.TreeIService;
import org.huangyalong.modules.system.domain.Dept;
import org.huangyalong.modules.system.request.DeptBO;

import java.io.Serializable;
import java.util.List;

public interface DeptService extends TreeIService<Dept> {

    List<Dept> getByAccountId(Serializable id);

    List<Dept> getByAccountId(Object id);

    boolean add(DeptBO payload);

    boolean update(DeptBO payload);
}

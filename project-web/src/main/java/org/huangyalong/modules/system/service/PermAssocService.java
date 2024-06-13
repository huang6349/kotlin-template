package org.huangyalong.modules.system.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.PermAssoc;
import org.huangyalong.modules.system.request.PermAssocBO;

public interface PermAssocService extends IService<PermAssoc> {

    boolean assoc(PermAssocBO payload, String assoc);
}

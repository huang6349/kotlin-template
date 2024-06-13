package org.huangyalong.modules.system.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.Tenant;
import org.huangyalong.modules.system.request.TenantUserBO;
import org.huangyalong.modules.system.request.TenantUserQueries;
import org.huangyalong.modules.system.response.TenantUserVO;

import java.io.Serializable;
import java.util.List;

public interface TenantUserService extends IService<Tenant> {

    boolean add(TenantUserBO payload);

    boolean update(TenantUserBO payload);

    boolean delete(Serializable id);

    TenantUserVO getVO(Serializable id);

    List<TenantUserVO> listVO();

    List<TenantUserVO> listVO(TenantUserQueries queries);

    long countVO();

    long countVO(TenantUserQueries queries);

    Page<TenantUserVO> pageVO(TenantUserQueries queries, Page<TenantUserVO> page);
}

package org.huangyalong.modules.system.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.system.domain.Dept;
import org.huangyalong.modules.system.request.DeptUserBO;
import org.huangyalong.modules.system.request.DeptUserQueries;
import org.huangyalong.modules.system.response.DeptUserVO;

import java.io.Serializable;
import java.util.List;

public interface DeptUserService extends IService<Dept> {

    boolean add(DeptUserBO payload);

    boolean update(DeptUserBO payload);

    boolean delete(Serializable id);

    DeptUserVO getVO(Serializable id);

    List<DeptUserVO> listVO();

    List<DeptUserVO> listVO(DeptUserQueries queries);

    long countVO();

    long countVO(DeptUserQueries queries);

    Page<DeptUserVO> pageVO(DeptUserQueries queries, Page<DeptUserVO> page);
}

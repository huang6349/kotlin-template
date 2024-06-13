package org.huangyalong.modules.system.service.impl;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.exception.BadRequestException;
import org.huangyalong.core.commons.exception.DataAlreadyExistException;
import org.huangyalong.core.commons.exception.DataNotExistException;
import org.huangyalong.core.enums.AssocCategory;
import org.huangyalong.core.enums.TimeEffective;
import org.huangyalong.modules.system.domain.Dept;
import org.huangyalong.modules.system.domain.DeptAssoc;
import org.huangyalong.modules.system.mapper.DeptMapper;
import org.huangyalong.modules.system.request.DeptUserBO;
import org.huangyalong.modules.system.request.DeptUserQueries;
import org.huangyalong.modules.system.request.DeptUserQueriesUtil;
import org.huangyalong.modules.system.response.DeptUserVO;
import org.huangyalong.modules.system.service.AccountService;
import org.huangyalong.modules.system.service.DeptAssocService;
import org.huangyalong.modules.system.service.DeptUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.DeptAssocTableDef.DEPT_ASSOC;

@AllArgsConstructor
@Service
public class DeptUserServiceImpl extends ServiceImpl<DeptMapper, Dept>
        implements DeptUserService {

    private final DeptAssocService deptAssocService;

    private final AccountService accountService;

    @Transactional(rollbackFor = Exception.class)
    public boolean add(DeptUserBO payload) {
        if (Opt.ofNullable(payload).filter(deptUserBO ->
                        ObjectUtil.isNotEmpty(deptUserBO.getAccountId())
                                && ObjectUtil.isNotEmpty(deptUserBO.getDeptId()))
                .map(deptUserBO -> deptAssocService.exists(QueryWrapper.create()
                        .where(DEPT_ASSOC.DEPT_ID.eq(deptUserBO.getDeptId()))
                        .and(DEPT_ASSOC.ASSOC.eq(ACCOUNT.getTableName()))
                        .and(DEPT_ASSOC.ASSOC_ID.eq(deptUserBO.getAccountId()))
                        .and(DEPT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0))
                        .and(DEPT_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("帐号已存在");
        val account = accountService.getByIdOpt(payload.getAccountId())
                .orElseThrow(() -> new DataNotExistException("所属帐号不存在"));
        val dept = super.getByIdOpt(payload.getDeptId())
                .orElseThrow(() -> new DataNotExistException("所属部门不存在"));
        val deptAssoc = DeptAssoc.create()
                .setDeptId(dept.getId())
                .setAssoc(ACCOUNT.getTableName())
                .setAssocId(account.getId())
                .setEffective(TimeEffective.TYPE0)
                .setCategory(AssocCategory.TYPE0)
                .setDesc(payload.getDesc());
        deptAssocService.save(deptAssoc);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(DeptUserBO payload) {
        val account = accountService.getByIdOpt(payload.getAccountId())
                .orElseThrow(() -> new DataNotExistException("所属帐号不存在"));
        val dept = super.getByIdOpt(payload.getDeptId())
                .orElseThrow(() -> new DataNotExistException("所属部门不存在"));
        val deptAssoc = deptAssocService.getByIdOpt(payload.getId())
                .orElseThrow(DataNotExistException::new)
                .setEffective(TimeEffective.TYPE0)
                .setCategory(AssocCategory.TYPE0)
                .setDesc(payload.getDesc());
        if (ObjectUtil.notEqual(account.getId(), deptAssoc.getAssocId()))
            throw new BadRequestException("所属帐号不允许修改");
        if (ObjectUtil.notEqual(dept.getId(), deptAssoc.getDeptId()))
            throw new BadRequestException("所属部门不允许修改");
        deptAssocService.updateById(deptAssoc);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Serializable id) {
        val deptAssoc = deptAssocService.getByIdOpt(id)
                .orElseThrow(DataNotExistException::new);
        deptAssocService.removeById(deptAssoc);
        return Boolean.TRUE;
    }

    @Override
    public DeptUserVO getVO(Serializable id) {
        val query = DeptUserQueriesUtil.getQueryWrapper(id);
        return super.getOneAs(DeptUserQueriesUtil.getQueryWrapper(query),
                DeptUserVO.class);
    }

    @Override
    public List<DeptUserVO> listVO() {
        val query = QueryWrapper.create();
        return super.listAs(DeptUserQueriesUtil.getQueryWrapper(query),
                DeptUserVO.class);
    }

    @Override
    public List<DeptUserVO> listVO(DeptUserQueries queries) {
        val query = DeptUserQueriesUtil.getQueryWrapper(queries);
        return super.listAs(DeptUserQueriesUtil.getQueryWrapper(query),
                DeptUserVO.class);
    }

    @Override
    public long countVO() {
        val query = QueryWrapper.create();
        return super.count(DeptUserQueriesUtil.getQueryWrapper(query));
    }

    @Override
    public long countVO(DeptUserQueries queries) {
        val query = DeptUserQueriesUtil.getQueryWrapper(queries);
        return super.count(DeptUserQueriesUtil.getQueryWrapper(query));
    }

    @Override
    public Page<DeptUserVO> pageVO(DeptUserQueries queries, Page<DeptUserVO> page) {
        val query = DeptUserQueriesUtil.getQueryWrapper(queries);
        return super.pageAs(page, DeptUserQueriesUtil.getQueryWrapper(query),
                DeptUserVO.class);
    }
}

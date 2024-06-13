package org.huangyalong.modules.system.service.impl;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.exception.BadRequestException;
import org.huangyalong.core.commons.exception.DataAlreadyExistException;
import org.huangyalong.core.commons.exception.DataNotExistException;
import org.huangyalong.core.enums.AssocCategory;
import org.huangyalong.core.enums.TimeEffective;
import org.huangyalong.core.tree.TreeServiceImpl;
import org.huangyalong.modules.system.domain.Dept;
import org.huangyalong.modules.system.mapper.DeptMapper;
import org.huangyalong.modules.system.request.DeptBO;
import org.huangyalong.modules.system.service.DeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.now;
import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.DeptAssocTableDef.DEPT_ASSOC;
import static org.huangyalong.modules.system.domain.table.DeptTableDef.DEPT;

@AllArgsConstructor
@Service
public class DeptServiceImpl extends TreeServiceImpl<DeptMapper, Dept>
        implements DeptService {

    @Override
    public List<Dept> getByAccountId(Serializable id) {
        val query = QueryWrapper.create()
                .select(DEPT.ALL_COLUMNS)
                .from(DEPT)
                .leftJoin(DEPT_ASSOC)
                .on(DEPT.ID.eq(DEPT_ASSOC.DEPT_ID))
                .where(DEPT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0)
                        .or(DEPT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE1)
                                .and(DEPT_ASSOC.EFFECTIVE_TIME.ge(now()))))
                .and(DEPT_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))
                .and(DEPT_ASSOC.ASSOC.eq(ACCOUNT.getTableName()))
                .and(DEPT_ASSOC.ASSOC_ID.eq(id));
        return super.list(query);
    }

    @Override
    public List<Dept> getByAccountId(Object id) {
        val convert = (Serializable) id;
        return this.getByAccountId(convert);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean add(DeptBO payload) {
        if (Opt.ofNullable(payload).filter(deptBO ->
                        ObjectUtil.isNotEmpty(deptBO.getCode()))
                .map(deptBO -> super.exists(QueryWrapper.create()
                        .where(DEPT.CODE.eq(deptBO.getCode()))
                        .and(DEPT.CODE.isNotNull())))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("代码已存在");
        val data = Dept.create()
                .setParentId(payload.getParentId())
                .setPath(getPath(payload.getParentId()))
                .with(payload);
        return super.save(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(DeptBO payload) {
        if (Opt.ofNullable(payload).filter(deptBO ->
                        ObjectUtil.isNotEmpty(deptBO.getCode()))
                .map(deptBO -> super.exists(QueryWrapper.create()
                        .where(DEPT.CODE.eq(deptBO.getCode()))
                        .and(DEPT.CODE.isNotNull())
                        .and(DEPT.ID.ne(deptBO.getId()))))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("代码已存在");
        val data = super.getByIdOpt(payload.getId())
                .orElseThrow(DataNotExistException::new)
                .with(payload);
        if (ObjectUtil.notEqual(Opt.ofNullable(payload.getParentId())
                .orElse(0L), data.getParentId()))
            throw new BadRequestException("父级节点不允许修改");
        return super.updateById(data);
    }
}

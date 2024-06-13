package org.huangyalong.modules.system.service.impl;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.core.commons.exception.DataAlreadyExistException;
import org.huangyalong.core.commons.exception.DataNotExistException;
import org.huangyalong.core.enums.AssocCategory;
import org.huangyalong.core.enums.TimeEffective;
import org.huangyalong.modules.system.domain.Perm;
import org.huangyalong.modules.system.mapper.PermMapper;
import org.huangyalong.modules.system.request.PermBO;
import org.huangyalong.modules.system.service.PermService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.now;
import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.PermAssocTableDef.PERM_ASSOC;
import static org.huangyalong.modules.system.domain.table.PermTableDef.PERM;
import static org.huangyalong.modules.system.domain.table.RoleTableDef.ROLE;

@AllArgsConstructor
@Service
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm>
        implements PermService {

    @Override
    public List<Perm> getByAccountId(Serializable id) {
        val query = QueryWrapper.create()
                .select(PERM.ALL_COLUMNS)
                .from(PERM)
                .leftJoin(PERM_ASSOC)
                .on(PERM.ID.eq(PERM_ASSOC.PERM_ID))
                .where(PERM_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0)
                        .or(PERM_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE1)
                                .and(PERM_ASSOC.EFFECTIVE_TIME.ge(now()))))
                .and(PERM_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))
                .and(PERM_ASSOC.ASSOC.eq(ACCOUNT.getTableName()))
                .and(PERM_ASSOC.ASSOC_ID.eq(id));
        return super.list(query);
    }

    @Override
    public List<Perm> getByRoleId(Serializable id) {
        val query = QueryWrapper.create()
                .select(PERM.ALL_COLUMNS)
                .from(PERM)
                .leftJoin(PERM_ASSOC)
                .on(PERM.ID.eq(PERM_ASSOC.PERM_ID))
                .where(PERM_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0)
                        .or(PERM_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE1)
                                .and(PERM_ASSOC.EFFECTIVE_TIME.ge(now()))))
                .and(PERM_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))
                .and(PERM_ASSOC.ASSOC.eq(ROLE.getTableName()))
                .and(PERM_ASSOC.ASSOC_ID.eq(id));
        return super.list(query);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean add(PermBO payload) {
        if (Opt.ofNullable(payload).filter(permBO ->
                        ObjectUtil.isNotEmpty(permBO.getCode()))
                .map(permBO -> super.exists(QueryWrapper.create()
                        .where(PERM.CODE.eq(permBO.getCode()))
                        .and(PERM.CODE.isNotNull())))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("代码已存在");
        val data = Perm.create()
                .with(payload);
        return super.save(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(PermBO payload) {
        if (Opt.ofNullable(payload).filter(permBO ->
                        ObjectUtil.isNotEmpty(permBO.getCode()))
                .map(permBO -> super.exists(QueryWrapper.create()
                        .where(PERM.CODE.eq(permBO.getCode()))
                        .and(PERM.CODE.isNotNull())
                        .and(PERM.ID.ne(permBO.getId()))))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("代码已存在");
        val data = super.getByIdOpt(payload.getId())
                .orElseThrow(DataNotExistException::new)
                .with(payload);
        return super.updateById(data);
    }
}

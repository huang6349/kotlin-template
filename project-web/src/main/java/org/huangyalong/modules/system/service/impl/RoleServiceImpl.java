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
import org.huangyalong.modules.system.domain.Role;
import org.huangyalong.modules.system.mapper.RoleMapper;
import org.huangyalong.modules.system.request.RoleBO;
import org.huangyalong.modules.system.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.now;
import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.RoleAssocTableDef.ROLE_ASSOC;
import static org.huangyalong.modules.system.domain.table.RoleTableDef.ROLE;

@AllArgsConstructor
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Override
    public List<Role> getByAccountId(Serializable id) {
        val query = QueryWrapper.create()
                .select(ROLE.ALL_COLUMNS)
                .from(ROLE)
                .rightJoin(ROLE_ASSOC)
                .on(ROLE.ID.eq(ROLE_ASSOC.ROLE_ID))
                .leftJoin(ACCOUNT)
                .on(ACCOUNT.ID.eq(ROLE_ASSOC.ASSOC_ID))
                .where(ROLE_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0)
                        .or(ROLE_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE1)
                                .and(ROLE_ASSOC.EFFECTIVE_TIME.ge(now()))))
                .and(ROLE_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))
                .and(ROLE_ASSOC.ASSOC.eq(ACCOUNT.getTableName()))
                .and(ACCOUNT.TENANT_ID.eq(ROLE_ASSOC.TENANT_ID))
                .and(ACCOUNT.ID.eq(id));
        return super.list(query);
    }

    @Override
    public List<Role> getByAccountId(Object id) {
        val convert = (Serializable) id;
        return this.getByAccountId(convert);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean add(RoleBO payload) {
        if (Opt.ofNullable(payload).filter(roleBO ->
                        ObjectUtil.isNotEmpty(roleBO.getCode()))
                .map(roleBO -> super.exists(QueryWrapper.create()
                        .where(ROLE.CODE.eq(roleBO.getCode()))
                        .and(ROLE.CODE.isNotNull())))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("代码已存在");
        val data = Role.create()
                .with(payload);
        return super.save(data);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(RoleBO payload) {
        if (Opt.ofNullable(payload).filter(roleBO ->
                        ObjectUtil.isNotEmpty(roleBO.getCode()))
                .map(roleBO -> super.exists(QueryWrapper.create()
                        .where(ROLE.CODE.eq(roleBO.getCode()))
                        .and(ROLE.CODE.isNotNull())
                        .and(ROLE.ID.ne(roleBO.getId()))))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("代码已存在");
        val data = super.getByIdOpt(payload.getId())
                .orElseThrow(DataNotExistException::new)
                .with(payload);
        return super.updateById(data);
    }
}

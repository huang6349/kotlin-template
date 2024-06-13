package org.huangyalong.modules.system.service.impl;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.BooleanUtil;
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
import org.huangyalong.modules.system.domain.RoleAssoc;
import org.huangyalong.modules.system.domain.Tenant;
import org.huangyalong.modules.system.domain.TenantAssoc;
import org.huangyalong.modules.system.mapper.TenantMapper;
import org.huangyalong.modules.system.request.TenantUserBO;
import org.huangyalong.modules.system.request.TenantUserQueries;
import org.huangyalong.modules.system.request.TenantUserQueriesUtil;
import org.huangyalong.modules.system.response.TenantUserVO;
import org.huangyalong.modules.system.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.RoleAssocTableDef.ROLE_ASSOC;
import static org.huangyalong.modules.system.domain.table.TenantAssocTableDef.TENANT_ASSOC;

@AllArgsConstructor
@Service
public class TenantUserServiceImpl extends ServiceImpl<TenantMapper, Tenant>
        implements TenantUserService {

    private final TenantAssocService tenantAssocService;

    private final RoleAssocService roleAssocService;

    private final AccountService accountService;

    private final RoleService roleService;

    @Transactional(rollbackFor = Exception.class)
    public boolean add(TenantUserBO payload) {
        if (Opt.ofNullable(payload).filter(tenantUserBO ->
                        ObjectUtil.isNotEmpty(tenantUserBO.getAccountId())
                                && ObjectUtil.isNotEmpty(tenantUserBO.getTenantId()))
                .map(tenantUserBO -> tenantAssocService.exists(QueryWrapper.create()
                        .where(TENANT_ASSOC.TENANT_ID.eq(tenantUserBO.getTenantId()))
                        .and(TENANT_ASSOC.ASSOC.eq(ACCOUNT.getTableName()))
                        .and(TENANT_ASSOC.ASSOC_ID.eq(tenantUserBO.getAccountId()))
                        .and(TENANT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0))
                        .and(TENANT_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))))
                .orElse(Boolean.FALSE))
            throw new DataAlreadyExistException("帐号已存在");
        val account = accountService.getByIdOpt(payload.getAccountId())
                .orElseThrow(() -> new DataNotExistException("所属帐号不存在"));
        val tenant = super.getByIdOpt(payload.getTenantId())
                .orElseThrow(() -> new DataNotExistException("所属租户不存在"));
        val role = roleService.getByIdOpt(payload.getRoleId())
                .orElseThrow(() -> new DataNotExistException("所属角色不存在"));
        if (BooleanUtil.negate(tenantAssocService.exists(QueryWrapper.create()
                .where(TENANT_ASSOC.ASSOC.eq(ACCOUNT.getTableName()))
                .and(TENANT_ASSOC.ASSOC_ID.eq(account.getId()))
                .and(TENANT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0))
                .and(TENANT_ASSOC.CATEGORY.eq(AssocCategory.TYPE0)))))
            account.setTenantId(tenant.getId());
        accountService.updateById(account);
        val tenantAssoc = TenantAssoc.create()
                .setTenantId(tenant.getId())
                .setAssoc(ACCOUNT.getTableName())
                .setAssocId(account.getId())
                .setEffective(TimeEffective.TYPE0)
                .setCategory(AssocCategory.TYPE0)
                .setDesc(payload.getDesc());
        roleAssocService.remove(QueryWrapper.create()
                .where(ROLE_ASSOC.TENANT_ID.eq(tenantAssoc.getTenantId()))
                .and(ROLE_ASSOC.ASSOC.eq(tenantAssoc.getAssoc()))
                .and(ROLE_ASSOC.ASSOC_ID.eq(tenantAssoc.getAssocId()))
                .and(ROLE_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0))
                .and(ROLE_ASSOC.CATEGORY.eq(AssocCategory.TYPE0)));
        tenantAssocService.save(tenantAssoc);
        val roleAssoc = RoleAssoc.create()
                .setTenantId(tenantAssoc.getTenantId())
                .setRoleId(role.getId())
                .setAssoc(tenantAssoc.getAssoc())
                .setAssocId(tenantAssoc.getAssocId())
                .setEffective(TimeEffective.TYPE0)
                .setCategory(AssocCategory.TYPE0)
                .setDesc(payload.getDesc());
        roleAssocService.save(roleAssoc);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(TenantUserBO payload) {
        val tenantAssoc = tenantAssocService.getByIdOpt(payload.getId())
                .orElseThrow(DataNotExistException::new)
                .setEffective(TimeEffective.TYPE0)
                .setCategory(AssocCategory.TYPE0)
                .setDesc(payload.getDesc());
        val account = accountService.getByIdOpt(payload.getAccountId())
                .orElseThrow(() -> new DataNotExistException("所属帐号不存在"));
        val tenant = super.getByIdOpt(payload.getTenantId())
                .orElseThrow(() -> new DataNotExistException("所属租户不存在"));
        val role = roleService.getByIdOpt(payload.getRoleId())
                .orElseThrow(() -> new DataNotExistException("所属角色不存在"));
        if (ObjectUtil.notEqual(account.getId(), tenantAssoc.getAssocId()))
            throw new BadRequestException("所属帐号不允许修改");
        if (ObjectUtil.notEqual(tenant.getId(), tenantAssoc.getTenantId()))
            throw new BadRequestException("所属租户不允许修改");
        roleAssocService.remove(QueryWrapper.create()
                .where(ROLE_ASSOC.TENANT_ID.eq(tenantAssoc.getTenantId()))
                .and(ROLE_ASSOC.ASSOC.eq(tenantAssoc.getAssoc()))
                .and(ROLE_ASSOC.ASSOC_ID.eq(tenantAssoc.getAssocId()))
                .and(ROLE_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0))
                .and(ROLE_ASSOC.CATEGORY.eq(AssocCategory.TYPE0)));
        tenantAssocService.updateById(tenantAssoc);
        val roleAssoc = RoleAssoc.create()
                .setTenantId(tenantAssoc.getTenantId())
                .setRoleId(role.getId())
                .setAssoc(tenantAssoc.getAssoc())
                .setAssocId(tenantAssoc.getAssocId())
                .setEffective(TimeEffective.TYPE0)
                .setCategory(AssocCategory.TYPE0)
                .setDesc(payload.getDesc());
        roleAssocService.save(roleAssoc);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Serializable id) {
        val tenantAssoc = tenantAssocService.getByIdOpt(id)
                .orElseThrow(DataNotExistException::new);
        roleAssocService.remove(QueryWrapper.create()
                .where(ROLE_ASSOC.TENANT_ID.eq(tenantAssoc.getTenantId()))
                .and(ROLE_ASSOC.ASSOC.eq(tenantAssoc.getAssoc()))
                .and(ROLE_ASSOC.ASSOC_ID.eq(tenantAssoc.getAssocId()))
                .and(ROLE_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0))
                .and(ROLE_ASSOC.CATEGORY.eq(AssocCategory.TYPE0)));
        tenantAssocService.removeById(tenantAssoc);
        return Boolean.TRUE;
    }

    @Override
    public TenantUserVO getVO(Serializable id) {
        val query = TenantUserQueriesUtil.getQueryWrapper(id);
        return super.getOneAs(TenantUserQueriesUtil.getQueryWrapper(query),
                TenantUserVO.class);
    }

    @Override
    public List<TenantUserVO> listVO() {
        val query = QueryWrapper.create();
        return super.listAs(TenantUserQueriesUtil.getQueryWrapper(query),
                TenantUserVO.class);
    }

    @Override
    public List<TenantUserVO> listVO(TenantUserQueries queries) {
        val query = TenantUserQueriesUtil.getQueryWrapper(queries);
        return super.listAs(TenantUserQueriesUtil.getQueryWrapper(query),
                TenantUserVO.class);
    }

    @Override
    public long countVO() {
        val query = QueryWrapper.create();
        return super.count(TenantUserQueriesUtil.getQueryWrapper(query));
    }

    @Override
    public long countVO(TenantUserQueries queries) {
        val query = TenantUserQueriesUtil.getQueryWrapper(queries);
        return super.count(TenantUserQueriesUtil.getQueryWrapper(query));
    }

    @Override
    public Page<TenantUserVO> pageVO(TenantUserQueries queries, Page<TenantUserVO> page) {
        val query = TenantUserQueriesUtil.getQueryWrapper(queries);
        return super.pageAs(page, TenantUserQueriesUtil.getQueryWrapper(query),
                TenantUserVO.class);
    }
}

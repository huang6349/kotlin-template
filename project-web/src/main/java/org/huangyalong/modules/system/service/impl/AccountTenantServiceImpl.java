package org.huangyalong.modules.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.camel.ProducerTemplate;
import org.huangyalong.core.commons.exception.DataNotExistException;
import org.huangyalong.core.enums.AssocCategory;
import org.huangyalong.core.enums.TimeEffective;
import org.huangyalong.modules.system.domain.Tenant;
import org.huangyalong.modules.system.mapper.TenantMapper;
import org.huangyalong.modules.system.request.AccountTenantBO;
import org.huangyalong.modules.system.service.AccountService;
import org.huangyalong.modules.system.service.AccountTenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

import static com.mybatisflex.core.query.QueryMethods.now;
import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.TenantAssocTableDef.TENANT_ASSOC;
import static org.huangyalong.modules.system.domain.table.TenantTableDef.TENANT;

@AllArgsConstructor
@Service
public class AccountTenantServiceImpl extends ServiceImpl<TenantMapper, Tenant>
        implements AccountTenantService {

    private final ProducerTemplate producerTemplate;

    private final AccountService accountService;

    @Transactional(rollbackFor = Exception.class)
    public boolean updateByAccountId(AccountTenantBO payload, Serializable id) {
        val account = accountService.getByIdOpt(id)
                .orElseThrow(() -> new DataNotExistException("所属帐号不存在"));
        val tenant = super.getOneOpt(QueryWrapper.create()
                        .select(TENANT.ALL_COLUMNS)
                        .from(TENANT)
                        .rightJoin(TENANT_ASSOC)
                        .on(TENANT.ID.eq(TENANT_ASSOC.TENANT_ID)
                                .and(TENANT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0)
                                        .or(TENANT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE1)
                                                .and(TENANT_ASSOC.EFFECTIVE_TIME.ge(now()))))
                                .and(TENANT_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))
                                .and(TENANT_ASSOC.ASSOC.eq(ACCOUNT.getTableName()))
                                .and(TENANT_ASSOC.ASSOC_ID.eq(account.getId())))
                        .where(TENANT.ID.eq(payload.getTenantId())))
                .orElseThrow(() -> new DataNotExistException("所属租户不存在"));
        account.setTenantId(tenant.getId());
        accountService.updateById(account);
        val body = JSONUtil.createObj()
                .set("loginId", id);
        val uri = "direct://account/redis/sync";
        producerTemplate.sendBody(uri, body);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateByAccountId(AccountTenantBO payload, Object id) {
        val convert = (Serializable) id;
        return this.updateByAccountId(payload, convert);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateByAccountId(AccountTenantBO payload) {
        val loginId = StpUtil.getLoginId();
        return this.updateByAccountId(payload, loginId);
    }

    @Override
    public List<Tenant> getByAccountId(Serializable id) {
        val query = QueryWrapper.create()
                .select(TENANT.ALL_COLUMNS)
                .from(TENANT)
                .rightJoin(TENANT_ASSOC)
                .on(TENANT.ID.eq(TENANT_ASSOC.TENANT_ID)
                        .and(TENANT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0)
                                .or(TENANT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE1)
                                        .and(TENANT_ASSOC.EFFECTIVE_TIME.ge(now()))))
                        .and(TENANT_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))
                        .and(TENANT_ASSOC.ASSOC.eq(ACCOUNT.getTableName())))
                .leftJoin(ACCOUNT)
                .on(ACCOUNT.ID.eq(TENANT_ASSOC.ASSOC_ID))
                .where(ACCOUNT.ID.eq(id));
        return super.list(query);
    }

    @Override
    public List<Tenant> getByAccountId(Object id) {
        val convert = (Serializable) id;
        return this.getByAccountId(convert);
    }

    @Override
    public List<Tenant> getByAccountId() {
        val loginId = StpUtil.getLoginId();
        return this.getByAccountId(loginId);
    }
}

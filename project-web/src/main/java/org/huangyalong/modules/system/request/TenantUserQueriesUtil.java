package org.huangyalong.modules.system.request;

import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.huangyalong.core.enums.AssocCategory;
import org.huangyalong.core.enums.TimeEffective;
import org.huangyalong.modules.system.response.TenantUserVO;

import java.io.Serializable;

import static com.mybatisflex.core.query.QueryMethods.now;
import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.RoleAssocTableDef.ROLE_ASSOC;
import static org.huangyalong.modules.system.domain.table.RoleTableDef.ROLE;
import static org.huangyalong.modules.system.domain.table.TenantAssocTableDef.TENANT_ASSOC;
import static org.huangyalong.modules.system.domain.table.TenantTableDef.TENANT;
import static org.huangyalong.modules.system.domain.table.UserTableDef.USER;

public final class TenantUserQueriesUtil {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") TenantUserQueries queries,
                                               QueryWrapper query) {
        query.where(TENANT.ID.eq(queries.getTenantId(), If::notNull));
        query.where(ACCOUNT.USERNAME.like(queries.getUsername(), If::hasText));
        query.where(USER.NICKNAME.like(queries.getNickname(), If::hasText));
        query.where(ACCOUNT.MOBILE.like(queries.getMobile(), If::hasText));
        return query;
    }

    public static QueryWrapper getQueryWrapper(TenantUserQueries queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }

    public static QueryWrapper getQueryWrapper(Serializable id,
                                               QueryWrapper query) {
        query.where(TENANT_ASSOC.ID.eq(id));
        return query;
    }

    public static QueryWrapper getQueryWrapper(Serializable id) {
        val query = QueryWrapper.create();
        return getQueryWrapper(id, query);
    }

    public static QueryWrapper getQueryWrapper(QueryWrapper query) {
        return query.select(TENANT_ASSOC.ID.as(TenantUserVO::getId),
                        TENANT_ASSOC.DESC.as(TenantUserVO::getDesc),
                        TENANT_ASSOC.CREATE_TIME.as(TenantUserVO::getCreateTime),
                        TENANT_ASSOC.UPDATE_TIME.as(TenantUserVO::getUpdateTime))
                .select(ACCOUNT.ID.as(TenantUserVO::getAccountId),
                        ACCOUNT.USERNAME.as(TenantUserVO::getUsername),
                        ACCOUNT.MOBILE.as(TenantUserVO::getMobile))
                .select(USER.NICKNAME.as(TenantUserVO::getNickname))
                .select(TENANT.ID.as(TenantUserVO::getTenantId),
                        TENANT.NAME.as(TenantUserVO::getTenantName))
                .select(ROLE.ID.as(TenantUserVO::getRoleId),
                        ROLE.NAME.as(TenantUserVO::getRoleName))
                .from(TENANT)
                .rightJoin(TENANT_ASSOC)
                .on(TENANT.ID.eq(TENANT_ASSOC.TENANT_ID))
                .leftJoin(ACCOUNT)
                .on(ACCOUNT.ID.eq(TENANT_ASSOC.ASSOC_ID))
                .leftJoin(USER)
                .on(ACCOUNT.ID.eq(USER.ACCOUNT_ID))
                .leftJoin(ROLE_ASSOC)
                .on(ACCOUNT.ID.eq(ROLE_ASSOC.ASSOC_ID))
                .leftJoin(ROLE)
                .on(ROLE.ID.eq(ROLE_ASSOC.ROLE_ID))
                .where(TENANT_ASSOC.ASSOC.eq(ACCOUNT.getTableName()))
                .and(TENANT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0)
                        .or(TENANT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE1)
                                .and(TENANT_ASSOC.EFFECTIVE_TIME.ge(now()))))
                .and(TENANT_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))
                .and(ROLE_ASSOC.TENANT_ID.eq(TENANT.ID))
                .and(ROLE_ASSOC.ASSOC.eq(ACCOUNT.getTableName()))
                .and(ROLE_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0)
                        .or(ROLE_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE1)
                                .and(ROLE_ASSOC.EFFECTIVE_TIME.ge(now()))))
                .and(ROLE_ASSOC.CATEGORY.eq(AssocCategory.TYPE0))
                .orderBy(TENANT_ASSOC.ID.desc());
    }
}

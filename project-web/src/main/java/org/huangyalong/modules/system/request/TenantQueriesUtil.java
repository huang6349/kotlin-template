package org.huangyalong.modules.system.request;

import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.huangyalong.modules.system.domain.TenantPropertiesData;
import org.huangyalong.modules.system.response.TenantVO;

import java.io.Serializable;

import static com.mybatisflex.core.query.QueryMethods.*;
import static org.huangyalong.modules.system.domain.table.TenantPropertyTableDef.TENANT_PROPERTY;
import static org.huangyalong.modules.system.domain.table.TenantTableDef.TENANT;

public final class TenantQueriesUtil {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") TenantQueries queries,
                                               QueryWrapper query) {
        query.where(TENANT.NAME.like(queries.getName(), If::hasText));
        return query;
    }

    public static QueryWrapper getQueryWrapper(TenantQueries queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }

    public static QueryWrapper getQueryWrapper(Serializable id,
                                               QueryWrapper query) {
        query.where(TENANT.ID.eq(id));
        return query;
    }

    public static QueryWrapper getQueryWrapper(Serializable id) {
        val query = QueryWrapper.create();
        return getQueryWrapper(id, query);
    }

    public static QueryWrapper getQueryWrapper(QueryWrapper query) {
        return query.select(TENANT.ID,
                        TENANT.NAME,
                        TENANT.AVATAR,
                        TENANT.CATEGORY,
                        TENANT.ADDRESS,
                        TENANT.DESC.as(TenantVO::getDesc),
                        TENANT.STATUS,
                        TENANT.CREATE_TIME,
                        TENANT.UPDATE_TIME)
                .select(max(case_()
                        .when(TENANT_PROPERTY.NAME.eq(TenantPropertiesData.NAME_ABBR))
                        .then(TENANT_PROPERTY.DATA)
                        .end())
                        .as(TenantVO::getAbbr))
                .select(max(case_()
                        .when(TENANT_PROPERTY.NAME.eq(TenantPropertiesData.NAME_AREA))
                        .then(TENANT_PROPERTY.DATA)
                        .end())
                        .as(TenantVO::getArea))
                .from(TENANT)
                .leftJoin(TENANT_PROPERTY)
                .on(TENANT.ID.eq(TENANT_PROPERTY.TENANT_ID)
                        .and(TENANT_PROPERTY.ID.in(select(max(TENANT_PROPERTY.ID))
                                .from(TENANT_PROPERTY)
                                .where(TENANT.ID.eq(TENANT_PROPERTY.TENANT_ID))
                                .and(TENANT_PROPERTY.GROUP.eq(TenantPropertiesData.GROUP_TENANT))
                                .groupBy(TENANT_PROPERTY.GROUP,
                                        TENANT_PROPERTY.NAME))))
                .groupBy(TENANT.ID)
                .orderBy(TENANT.ID, Boolean.FALSE);
    }
}

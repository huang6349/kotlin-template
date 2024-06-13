package org.huangyalong.modules.system.request;

import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;

import static org.huangyalong.modules.system.domain.table.RoleTableDef.ROLE;

public final class RoleQueriesUtil {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") RoleQueries queries,
                                               QueryWrapper query) {
        query.where(ROLE.NAME.like(queries.getName(), If::hasText));
        query.where(ROLE.CODE.like(queries.getCode(), If::hasText));
        query.orderBy(ROLE.ID, Boolean.FALSE);
        return query;
    }

    public static QueryWrapper getQueryWrapper(RoleQueries queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }
}

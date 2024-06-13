package org.huangyalong.modules.system.request;

import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;

import static org.huangyalong.modules.system.domain.table.PermTableDef.PERM;

public final class PermQueriesUtil {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") PermQueries queries,
                                               QueryWrapper query) {
        query.where(PERM.NAME.like(queries.getName(), If::hasText));
        query.where(PERM.CODE.like(queries.getCode(), If::hasText));
        query.orderBy(PERM.ID, Boolean.FALSE);
        return query;
    }

    public static QueryWrapper getQueryWrapper(PermQueries queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }
}

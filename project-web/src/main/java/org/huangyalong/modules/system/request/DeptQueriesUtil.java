package org.huangyalong.modules.system.request;

import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;

import static org.huangyalong.modules.system.domain.table.DeptTableDef.DEPT;

public final class DeptQueriesUtil {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") DeptQueries queries,
                                               QueryWrapper query) {
        query.where(DEPT.NAME.like(queries.getName(), If::hasText));
        query.where(DEPT.CODE.like(queries.getCode(), If::hasText));
        query.orderBy(DEPT.ID, Boolean.FALSE);
        return query;
    }

    public static QueryWrapper getQueryWrapper(DeptQueries queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }
}

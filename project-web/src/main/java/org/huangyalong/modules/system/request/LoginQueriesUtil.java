package org.huangyalong.modules.system.request;

import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;

import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;

public final class LoginQueriesUtil {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") LoginQueries queries,
                                               QueryWrapper query) {
        query.where(ACCOUNT.USERNAME.eq(queries.getUsername(), If::hasText)
                .or(ACCOUNT.MOBILE.eq(queries.getUsername(), If::hasText))
                .or(ACCOUNT.EMAIL.eq(queries.getUsername(), If::hasText)));
        return query;
    }

    public static QueryWrapper getQueryWrapper(LoginQueries queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }

    public static QueryWrapper getQueryWrapper(QueryWrapper query) {
        return query.limit(1);
    }
}

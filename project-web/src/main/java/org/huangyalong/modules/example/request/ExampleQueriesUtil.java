package org.huangyalong.modules.example.request;

import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;

public final class ExampleQueriesUtil {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") ExampleQueries queries,
                                               QueryWrapper query) {
        return query;
    }

    public static QueryWrapper getQueryWrapper(ExampleQueries queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }
}

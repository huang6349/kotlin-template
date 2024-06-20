package #(queriesUtilPackage);

import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;

public final class #(queriesUtilClassName) {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") #(queriesClassName) queries,
                                               QueryWrapper query) {
        return query;
    }

    public static QueryWrapper getQueryWrapper(#(queriesClassName) queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }
}

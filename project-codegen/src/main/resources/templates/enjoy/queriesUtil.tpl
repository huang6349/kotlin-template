#set(entityClassName = table.buildEntityClassName())
#set(queriesClassName = entityClassName.concat("Queries"))
package #(packageName);

import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;

public final class #(className) {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") #(queriesClassName) queries,
                                               QueryWrapper query) {
        return query;
    }

    public static QueryWrapper getQueryWrapper(#(queriesClassName) queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }
}

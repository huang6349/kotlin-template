package org.huangyalong.modules.system.request;

import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.huangyalong.core.enums.AssocCategory;
import org.huangyalong.core.enums.TimeEffective;
import org.huangyalong.modules.system.response.DeptUserVO;

import java.io.Serializable;

import static com.mybatisflex.core.query.QueryMethods.now;
import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.DeptAssocTableDef.DEPT_ASSOC;
import static org.huangyalong.modules.system.domain.table.DeptTableDef.DEPT;
import static org.huangyalong.modules.system.domain.table.UserTableDef.USER;

public final class DeptUserQueriesUtil {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") DeptUserQueries queries,
                                               QueryWrapper query) {
        query.where(DEPT.ID.eq(queries.getDeptId(), If::notNull));
        query.where(ACCOUNT.USERNAME.like(queries.getUsername(), If::hasText));
        query.where(USER.NICKNAME.like(queries.getNickname(), If::hasText));
        query.where(ACCOUNT.MOBILE.like(queries.getMobile(), If::hasText));
        return query;
    }

    public static QueryWrapper getQueryWrapper(DeptUserQueries queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }

    public static QueryWrapper getQueryWrapper(Serializable id,
                                               QueryWrapper query) {
        query.where(DEPT_ASSOC.ID.eq(id));
        return query;
    }

    public static QueryWrapper getQueryWrapper(Serializable id) {
        val query = QueryWrapper.create();
        return getQueryWrapper(id, query);
    }

    public static QueryWrapper getQueryWrapper(QueryWrapper query) {
        return query.select(DEPT_ASSOC.ID.as(DeptUserVO::getId),
                        DEPT_ASSOC.DESC.as(DeptUserVO::getDesc),
                        DEPT_ASSOC.CREATE_TIME.as(DeptUserVO::getCreateTime),
                        DEPT_ASSOC.UPDATE_TIME.as(DeptUserVO::getUpdateTime))
                .select(ACCOUNT.ID.as(DeptUserVO::getAccountId),
                        ACCOUNT.USERNAME.as(DeptUserVO::getUsername),
                        ACCOUNT.MOBILE.as(DeptUserVO::getMobile))
                .select(USER.NICKNAME.as(DeptUserVO::getNickname))
                .select(DEPT.ID.as(DeptUserVO::getDeptId),
                        DEPT.NAME.as(DeptUserVO::getDeptName))
                .from(DEPT)
                .rightJoin(DEPT_ASSOC)
                .on(DEPT.ID.eq(DEPT_ASSOC.DEPT_ID)
                        .and(DEPT_ASSOC.ASSOC.eq(ACCOUNT.getTableName()))
                        .and(DEPT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE0)
                                .or(DEPT_ASSOC.EFFECTIVE.eq(TimeEffective.TYPE1)
                                        .and(DEPT_ASSOC.EFFECTIVE_TIME.ge(now()))))
                        .and(DEPT_ASSOC.CATEGORY.eq(AssocCategory.TYPE0)))
                .leftJoin(ACCOUNT)
                .on(ACCOUNT.ID.eq(DEPT_ASSOC.ASSOC_ID))
                .leftJoin(USER)
                .on(ACCOUNT.ID.eq(USER.ACCOUNT_ID))
                .orderBy(DEPT_ASSOC.ID, Boolean.FALSE);
    }
}

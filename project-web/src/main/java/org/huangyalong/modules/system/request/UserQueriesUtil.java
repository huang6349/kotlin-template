package org.huangyalong.modules.system.request;

import com.mybatisflex.core.query.If;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.huangyalong.modules.system.response.UserVO;

import java.io.Serializable;

import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.TenantTableDef.TENANT;
import static org.huangyalong.modules.system.domain.table.UserTableDef.USER;

public final class UserQueriesUtil {

    public static QueryWrapper getQueryWrapper(@SuppressWarnings("unused") UserQueries queries,
                                               QueryWrapper query) {
        query.where(ACCOUNT.USERNAME.like(queries.getUsername(), If::hasText));
        query.where(USER.NICKNAME.like(queries.getNickname(), If::hasText));
        query.where(ACCOUNT.MOBILE.like(queries.getMobile(), If::hasText));
        return query;
    }

    public static QueryWrapper getQueryWrapper(UserQueries queries) {
        val query = QueryWrapper.create();
        return getQueryWrapper(queries, query);
    }

    public static QueryWrapper getQueryWrapper(Serializable id,
                                               QueryWrapper query) {
        query.where(ACCOUNT.ID.eq(id));
        return query;
    }

    public static QueryWrapper getQueryWrapper(Serializable id) {
        val query = QueryWrapper.create();
        return getQueryWrapper(id, query);
    }

    public static QueryWrapper getQueryWrapper(QueryWrapper query) {
        return query.select(ACCOUNT.ID,
                        ACCOUNT.USERNAME,
                        ACCOUNT.MOBILE,
                        ACCOUNT.EMAIL,
                        ACCOUNT.LOGIN_TIME,
                        ACCOUNT.STATUS,
                        ACCOUNT.TENANT_ID.as(UserVO::getTenantId),
                        ACCOUNT.CREATE_TIME,
                        ACCOUNT.UPDATE_TIME)
                .select(USER.NICKNAME,
                        USER.AVATAR,
                        USER.REALNAME,
                        USER.ID_CARD,
                        USER.GENDER,
                        USER.BIRTHDAY,
                        USER.ADDRESS,
                        USER.DESC.as(UserVO::getDesc))
                .select(TENANT.NAME.as(UserVO::getTenantName))
                .from(ACCOUNT)
                .leftJoin(USER)
                .on(ACCOUNT.ID.eq(USER.ACCOUNT_ID))
                .leftJoin(TENANT)
                .on(TENANT.ID.eq(ACCOUNT.TENANT_ID))
                .orderBy(ACCOUNT.ID, Boolean.FALSE);
    }
}

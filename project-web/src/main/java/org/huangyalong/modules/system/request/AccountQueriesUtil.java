package org.huangyalong.modules.system.request;

import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.huangyalong.modules.system.response.AccountVO;

import java.io.Serializable;

import static org.huangyalong.modules.system.domain.table.AccountTableDef.ACCOUNT;
import static org.huangyalong.modules.system.domain.table.TenantTableDef.TENANT;
import static org.huangyalong.modules.system.domain.table.UserTableDef.USER;

public final class AccountQueriesUtil {

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
                        ACCOUNT.TENANT_ID.as(AccountVO::getTenantId))
                .select(USER.NICKNAME,
                        USER.AVATAR,
                        USER.REALNAME,
                        USER.ID_CARD,
                        USER.GENDER,
                        USER.BIRTHDAY,
                        USER.ADDRESS,
                        USER.DESC.as(AccountVO::getDesc))
                .select(TENANT.NAME.as(AccountVO::getTenantName))
                .from(ACCOUNT)
                .leftJoin(USER)
                .on(ACCOUNT.ID.eq(USER.ACCOUNT_ID))
                .leftJoin(TENANT)
                .on(TENANT.ID.eq(ACCOUNT.TENANT_ID))
                .orderBy(ACCOUNT.ID, Boolean.FALSE);
    }
}

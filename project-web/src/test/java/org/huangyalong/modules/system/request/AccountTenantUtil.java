package org.huangyalong.modules.system.request;

import cn.hutool.json.JSONObject;
import lombok.val;

public final class AccountTenantUtil {

    public static AccountTenantBO createBO(JSONObject jsonObject) {
        val accountTenantBO = new AccountTenantBO();
        val tenantId = jsonObject.getLong("tenantId");
        accountTenantBO.setTenantId(tenantId);
        return accountTenantBO;
    }

    public static AccountTenantBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

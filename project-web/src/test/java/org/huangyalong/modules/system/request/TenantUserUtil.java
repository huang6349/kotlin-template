package org.huangyalong.modules.system.request;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import lombok.val;

import java.util.Optional;

public final class TenantUserUtil {

    public static final String DEFAULT_DESC = RandomUtil.randomString(12);

    public static final String UPDATED_DESC = RandomUtil.randomString(12);

    public static TenantUserBO createBO(JSONObject jsonObject) {
        val tenantUserBO = new TenantUserBO();
        val id = jsonObject.getLong("id");
        val accountId = jsonObject.getLong("accountId");
        val tenantId = jsonObject.getLong("tenantId");
        val roleId = jsonObject.getLong("roleId");
        val desc = jsonObject.getStr("desc");
        tenantUserBO.setId(id);
        tenantUserBO.setAccountId(accountId);
        tenantUserBO.setTenantId(tenantId);
        tenantUserBO.setRoleId(roleId);
        tenantUserBO.setDesc(Optional.ofNullable(desc)
                .orElse(DEFAULT_DESC));
        return tenantUserBO;
    }

    public static TenantUserBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

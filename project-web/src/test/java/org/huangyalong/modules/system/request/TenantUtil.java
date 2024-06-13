package org.huangyalong.modules.system.request;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import lombok.val;
import org.huangyalong.modules.system.enums.TenantCategory;

import java.util.Optional;

public final class TenantUtil {

    public static final String DEFAULT_NAME = RandomUtil.randomString(12);
    public static final String DEFAULT_ABBR = RandomUtil.randomString(4);
    public static final String DEFAULT_AREA = RandomUtil.randomString(12);
    public static final String DEFAULT_ADDRESS = RandomUtil.randomString(12);
    public static final String DEFAULT_DESC = RandomUtil.randomString(12);

    public static final String UPDATED_NAME = RandomUtil.randomString(12);
    public static final String UPDATED_ABBR = RandomUtil.randomString(4);
    public static final String UPDATED_AREA = RandomUtil.randomString(12);
    public static final String UPDATED_ADDRESS = RandomUtil.randomString(12);
    public static final String UPDATED_DESC = RandomUtil.randomString(12);

    public static TenantBO createBO(JSONObject jsonObject) {
        val tenantBO = new TenantBO();
        val id = jsonObject.getLong("id");
        val name = jsonObject.getStr("name");
        val abbr = jsonObject.getStr("abbr");
        val category = jsonObject.getStr("category");
        val area = jsonObject.getStr("area");
        val address = jsonObject.getStr("address");
        val desc = jsonObject.getStr("desc");
        tenantBO.setId(id);
        tenantBO.setName(Optional.ofNullable(name)
                .orElse(DEFAULT_NAME));
        tenantBO.setAbbr(Optional.ofNullable(abbr)
                .orElse(DEFAULT_ABBR));
        tenantBO.setCategory(Optional.ofNullable(category)
                .orElse(TenantCategory.TYPE0.getValue()));
        tenantBO.setArea(Optional.ofNullable(area)
                .orElse(DEFAULT_AREA));
        tenantBO.setAddress(Optional.ofNullable(address)
                .orElse(DEFAULT_ADDRESS));
        tenantBO.setDesc(Optional.ofNullable(desc)
                .orElse(DEFAULT_DESC));
        return tenantBO;
    }

    public static TenantBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

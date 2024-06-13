package org.huangyalong.modules.system.request;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import lombok.val;

import java.util.Optional;

public final class RoleUtil {

    public static final String DEFAULT_NAME = RandomUtil.randomString(12);
    public static final String DEFAULT_CODE = RandomUtil.randomString(12);
    public static final String DEFAULT_DESC = RandomUtil.randomString(12);

    public static final String UPDATED_NAME = RandomUtil.randomString(12);
    public static final String UPDATED_CODE = RandomUtil.randomString(12);
    public static final String UPDATED_DESC = RandomUtil.randomString(12);

    public static RoleBO createBO(JSONObject jsonObject) {
        val roleBO = new RoleBO();
        val id = jsonObject.getLong("id");
        val name = jsonObject.getStr("name");
        val code = jsonObject.getStr("code");
        val desc = jsonObject.getStr("desc");
        roleBO.setId(id);
        roleBO.setName(Optional.ofNullable(name)
                .orElse(DEFAULT_NAME));
        roleBO.setCode(Optional.ofNullable(code)
                .orElse(DEFAULT_CODE));
        roleBO.setDesc(Optional.ofNullable(desc)
                .orElse(DEFAULT_DESC));
        return roleBO;
    }

    public static RoleBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

package org.huangyalong.modules.system.request;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import lombok.val;

import java.util.Optional;

public final class PermUtil {

    public static final String DEFAULT_NAME = RandomUtil.randomString(12);
    public static final String DEFAULT_CODE = RandomUtil.randomString(12);
    public static final String DEFAULT_DESC = RandomUtil.randomString(12);

    public static final String UPDATED_NAME = RandomUtil.randomString(12);
    public static final String UPDATED_CODE = RandomUtil.randomString(12);
    public static final String UPDATED_DESC = RandomUtil.randomString(12);

    public static PermBO createBO(JSONObject jsonObject) {
        val permBO = new PermBO();
        val id = jsonObject.getLong("id");
        val name = jsonObject.getStr("name");
        val code = jsonObject.getStr("code");
        val desc = jsonObject.getStr("desc");
        permBO.setId(id);
        permBO.setName(Optional.ofNullable(name)
                .orElse(DEFAULT_NAME));
        permBO.setCode(Optional.ofNullable(code)
                .orElse(DEFAULT_CODE));
        permBO.setDesc(Optional.ofNullable(desc)
                .orElse(DEFAULT_DESC));
        return permBO;
    }

    public static PermBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

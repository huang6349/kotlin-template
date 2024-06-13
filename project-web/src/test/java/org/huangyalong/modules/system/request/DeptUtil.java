package org.huangyalong.modules.system.request;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import lombok.val;

import java.util.Optional;

public final class DeptUtil {

    public static final String DEFAULT_NAME = RandomUtil.randomString(12);
    public static final String DEFAULT_CODE = RandomUtil.randomString(12);
    public static final String DEFAULT_DESC = RandomUtil.randomString(12);

    public static final String UPDATED_NAME = RandomUtil.randomString(12);
    public static final String UPDATED_CODE = RandomUtil.randomString(12);
    public static final String UPDATED_DESC = RandomUtil.randomString(12);

    public static DeptBO createBO(JSONObject jsonObject) {
        val deptBO = new DeptBO();
        val id = jsonObject.getLong("id");
        val name = jsonObject.getStr("name");
        val code = jsonObject.getStr("code");
        val desc = jsonObject.getStr("desc");
        deptBO.setId(id);
        deptBO.setName(Optional.ofNullable(name)
                .orElse(DEFAULT_NAME));
        deptBO.setCode(Optional.ofNullable(code)
                .orElse(DEFAULT_CODE));
        deptBO.setDesc(Optional.ofNullable(desc)
                .orElse(DEFAULT_DESC));
        return deptBO;
    }

    public static DeptBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

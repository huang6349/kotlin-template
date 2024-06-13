package org.huangyalong.modules.system.request;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import lombok.val;

import java.util.Optional;

public final class DeptUserUtil {

    public static final String DEFAULT_DESC = RandomUtil.randomString(12);

    public static final String UPDATED_DESC = RandomUtil.randomString(12);

    public static DeptUserBO createBO(JSONObject jsonObject) {
        val deptUserBO = new DeptUserBO();
        val id = jsonObject.getLong("id");
        val accountId = jsonObject.getLong("accountId");
        val deptId = jsonObject.getLong("deptId");
        val desc = jsonObject.getStr("desc");
        deptUserBO.setId(id);
        deptUserBO.setAccountId(accountId);
        deptUserBO.setDeptId(deptId);
        deptUserBO.setDesc(Optional.ofNullable(desc)
                .orElse(DEFAULT_DESC));
        return deptUserBO;
    }

    public static DeptUserBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

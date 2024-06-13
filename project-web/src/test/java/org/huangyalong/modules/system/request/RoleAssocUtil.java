package org.huangyalong.modules.system.request;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.mybatisflex.core.util.ConvertUtil;
import lombok.val;

import java.util.stream.Collectors;

public final class RoleAssocUtil {

    public static RoleAssocBO createBO(JSONObject jsonObject) {
        val roleAssocBO = new RoleAssocBO();
        val assocId = jsonObject.getLong("assocId");
        val roleId = jsonObject.getStr("roleId");
        val roleIds = StrUtil.split(roleId, ",")
                .stream().map(ConvertUtil::toLong)
                .collect(Collectors.toList());
        roleAssocBO.setAssocId(assocId);
        roleAssocBO.setRoleIds(roleIds);
        return roleAssocBO;
    }

    public static RoleAssocBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

package org.huangyalong.modules.system.request;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.mybatisflex.core.util.ConvertUtil;
import lombok.val;

import java.util.stream.Collectors;

public final class TenantAssocUtil {

    public static TenantAssocBO createBO(JSONObject jsonObject) {
        val tenantAssocBO = new TenantAssocBO();
        val assocId = jsonObject.getLong("assocId");
        val tenantId = jsonObject.getStr("tenantId");
        val tenantIds = StrUtil.split(tenantId, ",")
                .stream().map(ConvertUtil::toLong)
                .collect(Collectors.toList());
        tenantAssocBO.setAssocId(assocId);
        tenantAssocBO.setTenantIds(tenantIds);
        return tenantAssocBO;
    }

    public static TenantAssocBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

package org.huangyalong.modules.system.request;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.mybatisflex.core.util.ConvertUtil;
import lombok.val;

import java.util.stream.Collectors;

public final class PermAssocUtil {

    public static PermAssocBO createBO(JSONObject jsonObject) {
        val permAssocBO = new PermAssocBO();
        val assocId = jsonObject.getLong("assocId");
        val permId = jsonObject.getStr("permId");
        val permIds = StrUtil.split(permId, ",")
                .stream().map(ConvertUtil::toLong)
                .collect(Collectors.toList());
        permAssocBO.setAssocId(assocId);
        permAssocBO.setPermIds(permIds);
        return permAssocBO;
    }

    public static PermAssocBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

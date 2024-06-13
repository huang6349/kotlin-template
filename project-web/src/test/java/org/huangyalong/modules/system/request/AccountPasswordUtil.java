package org.huangyalong.modules.system.request;

import cn.hutool.json.JSONObject;
import lombok.val;

import java.util.Optional;

public final class AccountPasswordUtil {

    public static AccountPasswordBO createBO(JSONObject jsonObject) {
        val accountPasswordBO = new AccountPasswordBO();
        val oldPassword = jsonObject.getStr("oldPassword");
        val newPassword = jsonObject.getStr("newPassword");
        accountPasswordBO.setOldPassword(Optional.ofNullable(oldPassword)
                .orElse(UserUtil.DEFAULT_PASSWORD));
        accountPasswordBO.setNewPassword(Optional.ofNullable(newPassword)
                .orElse(UserUtil.UPDATED_PASSWORD));
        accountPasswordBO.setConfirm(Optional.ofNullable(newPassword)
                .orElse(UserUtil.UPDATED_PASSWORD));
        return accountPasswordBO;
    }

    public static AccountPasswordBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

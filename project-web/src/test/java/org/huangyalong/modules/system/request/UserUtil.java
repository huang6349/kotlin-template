package org.huangyalong.modules.system.request;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import lombok.val;
import org.huangyalong.modules.system.enums.UserGender;

import java.util.Optional;

public final class UserUtil {

    public static final String DEFAULT_USERNAME = RandomUtil.randomString(RandomUtil.BASE_CHAR, 12);
    public static final String DEFAULT_PASSWORD = "a12345";
    public static final String DEFAULT_NICKNAME = RandomUtil.randomString(12);
    public static final String DEFAULT_MOBILE = "13123456789";
    public static final String DEFAULT_EMAIL = "13123456789@qq.com";

    public static final String UPDATED_PASSWORD = "b12345";
    public static final String UPDATED_NICKNAME = RandomUtil.randomString(12);
    public static final String UPDATED_MOBILE = "15123456789";
    public static final String UPDATED_EMAIL = "15123456789@qq.com";

    public static UserBO createBO(JSONObject jsonObject) {
        val userBO = new UserBO();
        val id = jsonObject.getLong("id");
        val username = jsonObject.getStr("username");
        val password = jsonObject.getStr("password");
        val nickname = jsonObject.getStr("nickname");
        val mobile = jsonObject.getStr("mobile");
        val email = jsonObject.getStr("email");
        val gender = jsonObject.getStr("gender");
        userBO.setId(id);
        userBO.setUsername(Optional.ofNullable(username)
                .orElse(DEFAULT_USERNAME));
        userBO.setPassword1(Optional.ofNullable(password)
                .orElse(DEFAULT_PASSWORD));
        userBO.setPassword2(Optional.ofNullable(password)
                .orElse(DEFAULT_PASSWORD));
        userBO.setNickname(Optional.ofNullable(nickname)
                .orElse(DEFAULT_NICKNAME));
        userBO.setMobile(Optional.ofNullable(mobile)
                .orElse(DEFAULT_MOBILE));
        userBO.setEmail(Optional.ofNullable(email)
                .orElse(DEFAULT_EMAIL));
        userBO.setGender(Optional.ofNullable(gender)
                .orElse(UserGender.TYPE0.getValue()));
        userBO.setPassword3(Optional.ofNullable(password)
                .orElse(DEFAULT_PASSWORD));
        return userBO;
    }

    public static UserBO createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

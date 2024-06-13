package org.huangyalong.modules.system.camel.process;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import lombok.val;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.huangyalong.modules.system.domain.User;
import org.huangyalong.modules.system.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;

public class NicknameProcess implements Processor {

    @Override
    public void process(Exchange exchange) {
        val jsonObject = exchange.getIn()
                .getBody(JSONObject.class);
        if (ObjectUtil.isNull(jsonObject)) return;
        val loginId = jsonObject.getStr("loginId");
        if (ObjectUtil.isNull(loginId)) return;
        val redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        val userService = SpringUtil.getBean(UserService.class);
        val nickname = userService.getByAccountIdOpt(loginId)
                .map(User::getNickname)
                .orElse(null);
        val key = StrUtil.format("account_nickname_{}", loginId);
        redisTemplate.delete(key);
        if (ObjectUtil.isNull(nickname)) return;
        redisTemplate.opsForValue().set(key, nickname);
    }
}

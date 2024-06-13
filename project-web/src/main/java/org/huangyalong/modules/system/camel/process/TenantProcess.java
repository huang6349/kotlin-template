package org.huangyalong.modules.system.camel.process;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import lombok.val;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.huangyalong.modules.system.domain.Account;
import org.huangyalong.modules.system.service.AccountService;
import org.springframework.data.redis.core.StringRedisTemplate;

public class TenantProcess implements Processor {

    @Override
    public void process(Exchange exchange) {
        val jsonObject = exchange.getIn()
                .getBody(JSONObject.class);
        if (ObjectUtil.isNull(jsonObject)) return;
        val loginId = jsonObject.getLong("loginId");
        if (ObjectUtil.isNull(loginId)) return;
        val redisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
        val accountService = SpringUtil.getBean(AccountService.class);
        val tenantId = accountService.getByIdOpt(loginId)
                .map(Account::getTenantId)
                .map(Convert::toStr)
                .orElse(null);
        val key = StrUtil.format("account_tenant_{}", loginId);
        redisTemplate.delete(key);
        if (ObjectUtil.isNull(tenantId)) return;
        redisTemplate.opsForList().leftPushAll(key, tenantId);
    }
}

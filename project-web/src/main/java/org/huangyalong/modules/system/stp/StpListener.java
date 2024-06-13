package org.huangyalong.modules.system.stp;

import cn.dev33.satoken.listener.SaTokenListenerForSimple;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class StpListener extends SaTokenListenerForSimple {

    private final ProducerTemplate producerTemplate;

    @Override
    public void doLogin(String loginType,
                        Object loginId,
                        String tokenValue,
                        SaLoginModel loginModel) {
        val body = JSONUtil.createObj()
                .set("loginId", loginId);
        val uri = "direct://account/redis/sync";
        producerTemplate.sendBody(uri, body);
    }
}

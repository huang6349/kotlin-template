package org.huangyalong.config

import cn.hutool.extra.spring.SpringUtil
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.DisposableBean
import cn.hutool.cron.CronUtil
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(SpringUtil::class)
class HutoolConfiguration : InitializingBean, DisposableBean {

    override fun afterPropertiesSet() {
        CronUtil.setMatchSecond(false)
        CronUtil.start(true)
    }

    override fun destroy() {
        CronUtil.stop()
    }
}

package org.huangyalong.config

import cn.hutool.cron.CronUtil
import cn.hutool.extra.spring.SpringUtil
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import javax.annotation.PreDestroy

@Configuration
@Import(SpringUtil::class)
class HutoolConfiguration : CommandLineRunner {

    override fun run(vararg args: String) {
        CronUtil.setMatchSecond(false)
        CronUtil.start(true)
    }

    @PreDestroy
    fun destroy() {
        CronUtil.stop()
    }
}

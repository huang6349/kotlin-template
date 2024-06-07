package org.huangyalong.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SyncTaskExecutor
import java.util.concurrent.Executor

@Configuration
class AsyncSyncConfiguration {

    @Bean(name = ["taskExecutor"])
    fun taskExecutor(): Executor {
        return SyncTaskExecutor()
    }
}

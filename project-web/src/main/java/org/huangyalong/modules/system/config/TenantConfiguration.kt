package org.huangyalong.modules.system.config

import com.mybatisflex.core.tenant.TenantFactory
import org.huangyalong.modules.system.tenant.TenantFactoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TenantConfiguration {

    @Bean
    fun tenantFactory(): TenantFactory {
        return TenantFactoryImpl()
    }
}

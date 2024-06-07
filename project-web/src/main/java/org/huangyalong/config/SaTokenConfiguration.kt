package org.huangyalong.config

import cn.dev33.satoken.config.SaTokenConfig
import cn.dev33.satoken.interceptor.SaInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SaTokenConfiguration : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(SaInterceptor())
            .addPathPatterns("/**")
    }

    @Bean
    @Primary
    fun saTokenConfig(): SaTokenConfig {
        val config = SaTokenConfig()
        config.isWriteHeader = true
        config.isPrint = false
        return config
    }
}

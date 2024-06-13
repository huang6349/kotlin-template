package org.huangyalong.modules.system.camel

import org.apache.camel.builder.RouteBuilder
import org.huangyalong.modules.system.camel.process.NicknameProcess
import org.huangyalong.modules.system.camel.process.TenantProcess

class AccountRoute : RouteBuilder() {

    override fun configure() {
        val errorHandler = defaultErrorHandler()
            .logExhaustedMessageHistory(false)
            .logStackTrace(false)
        from("direct://account/redis/sync")
            .errorHandler(errorHandler)
            .transacted()
            .choice().`when`(simple("\${body[loginId]}"))
            .toD("direct://account/redis/sync/nickname")
            .toD("direct://account/redis/sync/tenant")
            .end()
        from("direct://account/redis/sync/nickname")
            .errorHandler(errorHandler)
            .transacted()
            .choice().`when`(simple("\${body[loginId]}"))
            .process(NicknameProcess())
            .end()
        from("direct://account/redis/sync/tenant")
            .errorHandler(errorHandler)
            .transacted()
            .choice().`when`(simple("\${body[loginId]}"))
            .process(TenantProcess())
            .end()
    }
}

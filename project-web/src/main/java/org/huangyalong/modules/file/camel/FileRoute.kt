package org.huangyalong.modules.file.camel

import org.apache.camel.builder.RouteBuilder
import org.huangyalong.modules.file.camel.process.ClearProcess
import org.huangyalong.modules.file.camel.process.FileProcess

class FileRoute : RouteBuilder() {

    override fun configure() {
        val errorHandler = defaultErrorHandler()
            .logExhaustedMessageHistory(false)
            .logStackTrace(false)
        from("cron://file?schedule=0+0+1+*+*+?")
            .errorHandler(errorHandler)
            .transacted()
            .process(FileProcess())
            .split(exchangeProperty("urls"))
            .streaming()
            .choice().`when`(simple("\${body}"))
            .process(ClearProcess())
            .end()
            .end()
            .end()
    }
}

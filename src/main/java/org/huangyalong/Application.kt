package org.huangyalong

import org.huangyalong.core.AbstractIntegration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application : AbstractIntegration()

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

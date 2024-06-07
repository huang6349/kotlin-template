package org.huangyalong

import org.huangyalong.core.AbstractIntegrationTest
import org.huangyalong.core.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc

@AutoConfigureMockMvc
@IntegrationTest
class ApplicationTest : AbstractIntegrationTest() {

    @Test
    fun contextLoads() {
    }
}

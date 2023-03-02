package org.huangyalong.core

import org.huangyalong.Application
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [Application::class])
@ActiveProfiles("test")
abstract class AbstractIntegrationTest

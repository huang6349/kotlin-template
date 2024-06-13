package org.huangyalong.modules.system.camel

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(AccountRoute::class)
class AccountConfiguration

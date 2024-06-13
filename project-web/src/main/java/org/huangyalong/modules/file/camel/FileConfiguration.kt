package org.huangyalong.modules.file.camel

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(FileRoute::class)
class FileConfiguration

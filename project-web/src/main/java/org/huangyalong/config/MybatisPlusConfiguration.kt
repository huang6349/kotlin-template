package org.huangyalong.config

import com.baomidou.mybatisplus.extension.ddl.SimpleDdl
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

@Configuration
@MapperScan("org.huangyalong.**.mapper")
class MybatisPlusConfiguration : SimpleDdl() {
    override fun getSqlFiles() = listOf("schema.sql", "data.sql")
}

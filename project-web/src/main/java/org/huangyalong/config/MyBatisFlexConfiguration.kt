package org.huangyalong.config

import com.mybatisflex.core.FlexGlobalConfig
import com.mybatisflex.core.audit.AuditManager
import com.mybatisflex.spring.boot.MyBatisFlexCustomizer
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

@Configuration
@MapperScan("org.huangyalong.**.mapper")
class MyBatisFlexConfiguration : MyBatisFlexCustomizer {

    override fun customize(globalConfig: FlexGlobalConfig) {
        AuditManager.setAuditEnable(true)
    }
}

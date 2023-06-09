package org.huangyalong.config

import apijson.framework.APIJSONApplication
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration

@Configuration
class APIJSONSQLConfiguration : InitializingBean {

    override fun afterPropertiesSet() {
        APIJSONApplication.init(false)
    }
}

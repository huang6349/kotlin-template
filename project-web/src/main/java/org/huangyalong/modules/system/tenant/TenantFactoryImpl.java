package org.huangyalong.modules.system.tenant;

import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.mybatisflex.core.tenant.TenantFactory;
import lombok.val;
import org.springframework.data.redis.core.StringRedisTemplate;

public class TenantFactoryImpl implements TenantFactory {

    @Override
    public Object[] getTenantIds() {
        if (!SpringMVCUtil.isWeb()) return null;
        val loginId = StpUtil.getLoginIdDefaultNull();
        if (ObjectUtil.isNull(loginId)) return null;
        val key = StrUtil.format("account_tenant_{}", loginId);
        val tenantIds = SpringUtil.getBean(StringRedisTemplate.class)
                .opsForList().range(key, 0, -1);
        return Convert.toLongArray(tenantIds);
    }
}

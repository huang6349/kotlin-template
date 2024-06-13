package org.huangyalong.modules.system.domain;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Data(staticConstructor = "create")
public class TenantPropertiesData implements Serializable {

    public static final String GROUP_TENANT = "tenant";

    public static final String NAME_ABBR = "abbr";

    public static final String NAME_AREA = "area";

    private Long tenantId;

    private List<TenantProperty> properties;

    public TenantPropertiesData addAbbr(String value) {
        val properties = TenantProperty.create()
                .setGroup(GROUP_TENANT)
                .setName(NAME_ABBR)
                .setData(value)
                .setTenantId(tenantId);
        add(properties);
        return this;
    }

    public TenantPropertiesData addArea(String value) {
        val properties = TenantProperty.create()
                .setGroup(GROUP_TENANT)
                .setName(NAME_AREA)
                .setData(value)
                .setTenantId(tenantId);
        add(properties);
        return this;
    }

    public TenantPropertiesData add(TenantProperty value) {
        if (ObjectUtil.isNull(properties))
            properties = new ArrayList<>();
        properties.add(value);
        return this;
    }
}

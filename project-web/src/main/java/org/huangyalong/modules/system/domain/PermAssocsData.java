package org.huangyalong.modules.system.domain;

import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Data(staticConstructor = "create")
public class PermAssocsData implements Serializable {

    private List<PermAssoc> permAssocs;

    private String assoc;

    private Long assocId;

    public PermAssocsData addPermIds(List<Long> permIds) {
        permIds.stream().map(permId -> PermAssoc.create()
                        .setPermId(permId)
                        .setAssoc(assoc)
                        .setAssocId(assocId))
                .forEach(this::add);
        return this;
    }

    public PermAssocsData add(PermAssoc value) {
        if (ObjectUtil.isNull(permAssocs))
            permAssocs = new ArrayList<>();
        permAssocs.add(value);
        return this;
    }
}

package org.huangyalong.core.tree;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.huangyalong.core.request.AbstractBaseBO;

@Getter
@Setter
public abstract class AbstractTreeBO extends AbstractBaseBO
        implements TreeBO {

    @Schema(description = "父级节点（父级主键）")
    private Long parentId;

    @Schema(description = "节点顺序")
    private Integer sort;
}

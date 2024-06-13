package org.huangyalong.core.tree;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.huangyalong.core.domain.AbstractBaseEntity;

import java.util.List;

@Getter
@Setter
public abstract class AbstractTreeEntity<T extends AbstractTreeEntity<T>>
        extends AbstractBaseEntity<T>
        implements TreeEntity<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "父级节点（父级主键）")
    private Long parentId;

    @Column(ignore = true)
    @Schema(description = "子级节点（子级数据）")
    private List<T> children;

    @JsonIgnore
    @Schema(description = "节点路径")
    private String path;

    @Schema(description = "节点顺序")
    private Integer sort;

    public T setParentId(Long parentId) {
        this.parentId = parentId;
        return self();
    }

    public T setChildren(List<T> children) {
        this.children = children;
        return self();
    }

    public T setPath(String path) {
        this.path = path;
        return self();
    }

    public T setSort(Integer sort) {
        this.sort = sort;
        return self();
    }
}

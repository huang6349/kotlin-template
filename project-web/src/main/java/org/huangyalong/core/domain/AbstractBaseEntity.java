package org.huangyalong.core.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.activerecord.Model;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.huangyalong.core.enums.IsDeleted;

import java.util.Date;

@Getter
@Setter
public abstract class AbstractBaseEntity<T extends AbstractBaseEntity<T>> extends Model<T>
        implements BaseEntity<T> {

    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "数据主键")
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    private Date updateTime;

    @Column(version = true)
    @JsonIgnore
    @Schema(description = "更新版本")
    private Long version;

    @Column(isLogicDelete = true)
    @JsonIgnore
    @Schema(description = "是否删除")
    private IsDeleted isDeleted;

    public T setId(Long id) {
        this.id = id;
        return self();
    }

    public T setCreateTime(Date createTime) {
        this.createTime = createTime;
        return self();
    }

    public T setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return self();
    }

    public T setVersion(Long version) {
        this.version = version;
        return self();
    }

    public T setIsDeleted(IsDeleted isDeleted) {
        this.isDeleted = isDeleted;
        return self();
    }
}

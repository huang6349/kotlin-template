package org.huangyalong.modules.system.domain;

import cn.hutool.core.lang.Opt;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.huangyalong.core.dict.JKDictFormat;
import org.huangyalong.core.tree.AbstractTreeEntity;
import org.huangyalong.modules.system.enums.DeptStatus;
import org.huangyalong.modules.system.request.DeptBO;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@Table(value = "tb_dept")
@Schema(name = "部门信息")
public class Dept extends AbstractTreeEntity<Dept> {

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "部门代码")
    private String code;

    @Schema(description = "备注")
    private String desc;

    @JKDictFormat
    @Schema(description = "部门状态")
    private DeptStatus status;

    @Column(tenantId = true)
    @JsonIgnore
    @Schema(description = "租户主键")
    private Long tenantId;

    /****************** with ******************/

    @SuppressWarnings("DuplicatedCode")
    public Dept with(DeptBO deptBO) {
        Opt.ofNullable(deptBO)
                .map(DeptBO::getSort)
                .ifPresent(this::setSort);
        Opt.ofNullable(deptBO)
                .map(DeptBO::getName)
                .ifPresent(this::setName);
        Opt.ofNullable(deptBO)
                .map(DeptBO::getCode)
                .ifPresent(this::setCode);
        Opt.ofNullable(deptBO)
                .map(DeptBO::getDesc)
                .ifPresent(this::setDesc);
        return this;
    }
}

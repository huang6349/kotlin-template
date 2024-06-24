package org.huangyalong.modules.example.domain;

import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.huangyalong.core.dict.JKDictFormat;
import org.huangyalong.core.domain.AbstractBaseEntity;
import org.huangyalong.modules.example.enums.ExampleStatus;

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data(staticConstructor = "create")
@Table(value = "tb_example")
@Schema(name = "示例信息")
public class Example extends AbstractBaseEntity<Example> {

    @Schema(description = "示例名称")
    private String name;

    @Schema(description = "示例代码")
    private String code;

    @Schema(description = "备注")
    private String desc;

    @JKDictFormat
    @Schema(description = "示例状态")
    private ExampleStatus status;
}

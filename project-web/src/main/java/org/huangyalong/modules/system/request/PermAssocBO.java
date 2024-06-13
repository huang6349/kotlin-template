package org.huangyalong.modules.system.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "权限关联")
public class PermAssocBO implements Serializable {

    @NotNull(message = "关联主键不能为空")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "关联主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long assocId;

    @NotNull(message = "权限不能为空")
    @Schema(description = "权限主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> permIds;
}

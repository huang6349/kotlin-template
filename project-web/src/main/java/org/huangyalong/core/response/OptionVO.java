package org.huangyalong.core.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "选项信息")
public class OptionVO implements Serializable {

    @Schema(description = "选项名称")
    private String label;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "选项键值")
    private Long value;
}

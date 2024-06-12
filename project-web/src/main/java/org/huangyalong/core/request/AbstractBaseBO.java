package org.huangyalong.core.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractBaseBO implements BaseBO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(description = "数据主键")
    private Long id;
}

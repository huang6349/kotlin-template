package org.huangyalong.modules.example.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.request.AbstractBaseBO;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "示例信息")
public class ExampleBO extends AbstractBaseBO {
}

package #(packageName);

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.request.AbstractBaseBO;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "#(comment)")
public class #(className) extends AbstractBaseBO {
}

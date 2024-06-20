package #(queriesPackage);

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.request.BaseQueries;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "#(queriesComment)")
public class #(queriesClassName) implements BaseQueries {
}

package org.huangyalong.modules.system.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.huangyalong.core.request.BaseQueries;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "用户查询")
public class UserQueries implements BaseQueries {

    @Schema(description = "用户帐号")
    private String username;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "手机号码")
    private String mobile;
}

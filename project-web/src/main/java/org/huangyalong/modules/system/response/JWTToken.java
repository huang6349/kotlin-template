package org.huangyalong.modules.system.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "令牌信息")
public class JWTToken implements Serializable {

    @JsonProperty("id_token")
    @Schema(description = "授权令牌")
    private String idToken;

    public JWTToken(String idToken) {
        this.idToken = idToken;
    }
}

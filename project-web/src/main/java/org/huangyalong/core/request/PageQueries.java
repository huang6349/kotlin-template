package org.huangyalong.core.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Schema(name = "分页查询")
public class PageQueries implements Serializable {

    public static final long DEFAULT_PAGE_NUMBER = 1;

    public static final long DEFAULT_PAGE_SIZE = 10;

    @Schema(description = "当前页码")
    private Long pageNumber;

    @Schema(description = "每页数量")
    private Long pageSize;
}

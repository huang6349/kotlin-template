package org.huangyalong.core.commons.info

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(name = "响应信息")
open class ApiResponse<T> : Serializable {

    @Schema(description = "响应状态")
    var success: Boolean? = null

    @Schema(description = "响应数据")
    var data: T? = null

    @Schema(description = "响应信息")
    var message: String? = null

    @Schema(description = "响应代码")
    var code: Int? = null

    @Schema(description = "响应方式")
    var showType: Int? = null

    @Schema(description = "异常描述")
    var e: String? = null

    @Schema(description = "异常编号")
    var traceId: String? = null

    @Schema(description = "主机地址")
    var host: String? = null

    constructor(
        success: Boolean,
        data: T,
    ) {
        this.success = success
        this.data = data
    }

    constructor(
        success: Boolean,
        data: T,
        message: String?,
        showType: Int?,
    ) {
        this.success = success
        this.data = data
        this.message = message
        this.showType = showType
    }

    constructor(
        success: Boolean,
        message: String?,
        code: Int?,
        showType: Int?,
        e: String?,
    ) {
        this.success = success
        this.message = message
        this.code = code
        this.showType = showType
        this.e = e
    }

    constructor(
        success: Boolean,
        message: String?,
        code: Int?,
        showType: Int?,
        e: String?,
        traceId: String?,
        host: String?,
    ) {
        this.success = success
        this.message = message
        this.code = code
        this.showType = showType
        this.e = e
        this.traceId = traceId
        this.host = host
    }

    companion object {

        fun <T> fail(
            message: String?,
            code: Int?,
            e:
            String?,
        ): ApiResponse<T> = ApiResponse(
            false,
            message,
            code,
            ShowType.ERROR_MESSAGE.showType,
            e
        )

        fun <T> fail(
            message: String?,
            code: Int?,
            e: String?,
            showType: Int?,
        ): ApiResponse<T> = ApiResponse(
            false,
            message,
            code,
            showType,
            e
        )

        fun <T> fail(
            message: String?,
            code: Int?,
            e: String?,
            traceId: String?,
            host: String?,
        ): ApiResponse<T> = ApiResponse(
            false,
            message,
            code,
            ShowType.ERROR_MESSAGE.showType,
            e,
            traceId,
            host
        )

        fun <T> fail(
            message: String?,
            code: Int?,
            e: String?,
            showType: Int?,
            traceId: String?,
            host: String?,
        ): ApiResponse<T> = ApiResponse(
            false,
            message,
            code,
            showType,
            e,
            traceId,
            host
        )

        fun <T> ok(data: T): ApiResponse<T> = ApiResponse(
            true,
            data
        )

        fun <T> ok(
            data: T,
            message: String?,
        ): ApiResponse<T> = ApiResponse(
            true,
            data,
            message,
            ShowType.WARN_MESSAGE.showType
        )

        fun <T> ok(
            data: T,
            message: String?,
            showType: Int?,
        ): ApiResponse<T> = ApiResponse(
            true,
            data,
            message,
            showType
        )
    }
}

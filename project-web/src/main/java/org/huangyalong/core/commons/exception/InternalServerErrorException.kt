package org.huangyalong.core.commons.exception

import java.lang.RuntimeException
import org.springframework.http.HttpStatus
import org.huangyalong.core.commons.info.ShowType

class InternalServerErrorException : RuntimeException {

    var errorCode: Int? = HttpStatus.INTERNAL_SERVER_ERROR.value()

    var showType: ShowType? = ShowType.ERROR_MESSAGE

    var traceId: String? = null

    var host: String? = null

    constructor(
        message: String?,
    ) : super(message)

    constructor(
        message: String?,
        errorCode: Int?,
    ) : super(message) {
        this.errorCode = errorCode
    }

    constructor(
        message: String?,
        errorCode: Int?,
        showType: ShowType,
    ) : super(message) {
        this.errorCode = errorCode
        this.showType = showType
    }

    constructor(
        message: String?,
        traceId: String?,
        host: String?,
    ) : super(message) {
        this.traceId = traceId
        this.host = host
    }

    constructor(
        message: String?,
        errorCode: Int?,
        traceId: String?,
        host: String?,
    ) : super(message) {
        this.errorCode = errorCode
        this.traceId = traceId
        this.host = host
    }

    constructor(
        message: String?,
        showType: ShowType?,
        traceId: String?,
        host: String?,
    ) : super(message) {
        this.showType = showType
        this.traceId = traceId
        this.host = host
    }

    constructor(
        message: String?,
        errorCode: Int?,
        showType: ShowType?,
        traceId: String?,
        host: String?,
    ) : super(message) {
        this.errorCode = errorCode
        this.showType = showType
        this.traceId = traceId
        this.host = host
    }
}

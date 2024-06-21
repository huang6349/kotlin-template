package org.huangyalong.core.commons.exception

import org.huangyalong.core.commons.info.ShowType
import org.springframework.http.HttpStatus

open class BadRequestException : InternalServerErrorException {

    constructor(
        message: String,
    ) : super(
        message,
        Companion.errorCode
    )

    constructor(
        message: String,
        showType: ShowType,
    ) : super(
        message,
        Companion.errorCode,
        showType
    )

    constructor(
        message: String,
        traceId: String?,
        host: String?,
    ) : super(
        message,
        Companion.errorCode,
        traceId,
        host
    )

    constructor(
        message: String,
        showType: ShowType,
        traceId: String?,
        host: String?,
    ) : super(
        message,
        Companion.errorCode,
        showType,
        traceId,
        host
    )

    companion object {

        var errorCode: Int = HttpStatus.BAD_REQUEST.value()
    }
}

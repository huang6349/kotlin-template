package org.huangyalong.core.commons.exception

import org.huangyalong.core.commons.info.ShowType
import org.springframework.http.HttpStatus

open class DataNotExistException : InternalServerErrorException {

    constructor() : super(
        Companion.message,
        Companion.errorCode,
        Companion.showType
    )

    constructor(
        message: String?,
    ) : super(
        message,
        Companion.errorCode,
        Companion.showType
    )

    constructor(
        showType: ShowType?,
    ) : super(
        Companion.message,
        Companion.errorCode,
        showType
    )

    constructor(
        message: String?,
        showType: ShowType?,
    ) : super(
        message,
        Companion.errorCode,
        showType
    )


    constructor(
        traceId: String?,
        host: String?,
    ) : super(
        Companion.message,
        Companion.errorCode,
        Companion.showType,
        traceId,
        host
    )

    constructor(
        message: String?,
        traceId: String?,
        host: String?,
    ) : super(
        message,
        Companion.errorCode,
        Companion.showType,
        traceId,
        host
    )

    constructor(
        showType: ShowType?,
        traceId: String?,
        host: String?,
    ) : super(
        Companion.message,
        Companion.errorCode,
        showType,
        traceId,
        host
    )

    constructor(
        message: String?,
        showType: ShowType?,
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

        var errorCode: Int? = HttpStatus.NOT_FOUND.value()

        var message: String? = "数据不存在"

        var showType: ShowType? = ShowType.WARN_MESSAGE
    }
}

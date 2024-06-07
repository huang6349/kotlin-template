package org.huangyalong.core.commons.info

import org.huangyalong.core.commons.info.ApiResponseTestUtil.Companion.code
import org.huangyalong.core.commons.info.ApiResponseTestUtil.Companion.compare
import org.huangyalong.core.commons.info.ApiResponseTestUtil.Companion.data
import org.huangyalong.core.commons.info.ApiResponseTestUtil.Companion.e
import org.huangyalong.core.commons.info.ApiResponseTestUtil.Companion.host
import org.huangyalong.core.commons.info.ApiResponseTestUtil.Companion.message
import org.huangyalong.core.commons.info.ApiResponseTestUtil.Companion.traceId
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(OrderAnnotation::class)
class ApiResponseTest {

    @Order(1)
    @Test
    fun fail1() = compare(
        ApiResponse.fail(
            message,
            code,
            e
        ), ApiResponse(
            false,
            message,
            code,
            ShowType.ERROR_MESSAGE.showType,
            e
        )
    )

    @Order(2)
    @Test
    fun fail2() = compare(
        ApiResponse.fail(
            message,
            code,
            e,
            ShowType.SILENT.showType
        ), ApiResponse(
            false,
            message,
            code,
            ShowType.SILENT.showType,
            e
        )
    )

    @Order(3)
    @Test
    fun fail3() = compare(
        ApiResponse.fail(
            message,
            code,
            e,
            traceId,
            host
        ), ApiResponse(
            false,
            message,
            code,
            ShowType.ERROR_MESSAGE.showType,
            e,
            traceId,
            host
        )
    )

    @Order(4)
    @Test
    fun fail4() = compare(
        ApiResponse.fail(
            message,
            code,
            e,
            ShowType.NOTIFICATION.showType,
            traceId,
            host
        ), ApiResponse(
            false,
            message,
            code,
            ShowType.NOTIFICATION.showType,
            e,
            traceId,
            host
        )
    )

    @Order(5)
    @Test
    fun ok1() = compare(
        ApiResponse.ok(
            data
        ), ApiResponse(
            true,
            data
        )
    )

    @Order(6)
    @Test
    fun ok2() = compare(
        ApiResponse.ok(
            data,
            message
        ), ApiResponse(
            true,
            data,
            message,
            ShowType.WARN_MESSAGE.showType
        )
    )

    @Order(7)
    @Test
    fun ok3() = compare(
        ApiResponse.ok(
            data,
            message,
            ShowType.REDIRECT.showType
        ), ApiResponse(
            true,
            data,
            message,
            ShowType.REDIRECT.showType
        )
    )
}

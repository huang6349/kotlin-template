package org.huangyalong.core.commons.exception

import org.huangyalong.core.commons.exception.BadRequestExceptionTestUtil.Companion.errorMessage
import org.huangyalong.core.commons.exception.BadRequestExceptionTestUtil.Companion.execute1
import org.huangyalong.core.commons.exception.BadRequestExceptionTestUtil.Companion.execute2
import org.huangyalong.core.commons.exception.BadRequestExceptionTestUtil.Companion.execute3
import org.huangyalong.core.commons.exception.BadRequestExceptionTestUtil.Companion.execute4
import org.huangyalong.core.commons.exception.BadRequestExceptionTestUtil.Companion.host
import org.huangyalong.core.commons.exception.BadRequestExceptionTestUtil.Companion.showType
import org.huangyalong.core.commons.exception.BadRequestExceptionTestUtil.Companion.traceId
import org.huangyalong.core.commons.info.ShowType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.http.HttpStatus

@TestMethodOrder(OrderAnnotation::class)
class BadRequestExceptionTest {

    @Order(1)
    @Test
    fun test1() {
        val exception = assertThrows(
            BadRequestException::class.java
        ) { execute1() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.BAD_REQUEST.value())
        assertEquals(exception.showType, ShowType.ERROR_MESSAGE)
        assertNull(exception.traceId)
        assertNull(exception.host)
    }

    @Order(2)
    @Test
    fun test2() {
        val exception = assertThrows(
            BadRequestException::class.java
        ) { execute2() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.BAD_REQUEST.value())
        assertEquals(exception.showType, showType)
        assertNull(exception.traceId)
        assertNull(exception.host)
    }

    @Order(3)
    @Test
    fun test3() {
        val exception = assertThrows(
            BadRequestException::class.java
        ) { execute3() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.BAD_REQUEST.value())
        assertEquals(exception.showType, ShowType.ERROR_MESSAGE)
        assertEquals(exception.traceId, traceId)
        assertEquals(exception.host, host)
    }

    @Order(4)
    @Test
    fun test4() {
        val exception = assertThrows(
            BadRequestException::class.java
        ) { execute4() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.BAD_REQUEST.value())
        assertEquals(exception.showType, showType)
        assertEquals(exception.traceId, traceId)
        assertEquals(exception.host, host)
    }
}

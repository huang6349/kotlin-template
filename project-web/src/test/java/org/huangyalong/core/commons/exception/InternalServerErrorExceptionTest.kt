package org.huangyalong.core.commons.exception

import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.errorCode
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.errorMessage
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.execute1
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.execute2
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.execute3
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.execute4
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.execute5
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.execute6
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.execute7
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.host
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.showType
import org.huangyalong.core.commons.exception.InternalServerErrorExceptionTestUtil.Companion.traceId
import org.huangyalong.core.commons.info.ShowType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.http.HttpStatus

@TestMethodOrder(OrderAnnotation::class)
class InternalServerErrorExceptionTest {

    @Order(1)
    @Test
    fun test1() {
        val exception = assertThrows(
            InternalServerErrorException::class.java
        ) { execute1() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.INTERNAL_SERVER_ERROR.value())
        assertEquals(exception.showType, ShowType.ERROR_MESSAGE)
        assertNull(exception.traceId)
        assertNull(exception.host)
    }

    @Order(2)
    @Test
    fun test2() {
        val exception = assertThrows(
            InternalServerErrorException::class.java
        ) { execute2() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, errorCode)
        assertEquals(exception.showType, ShowType.ERROR_MESSAGE)
        assertNull(exception.traceId)
        assertNull(exception.host)
    }

    @Order(3)
    @Test
    fun test3() {
        val exception = assertThrows(
            InternalServerErrorException::class.java
        ) { execute3() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, errorCode)
        assertEquals(exception.showType, showType)
        assertNull(exception.traceId)
        assertNull(exception.host)
    }

    @Order(4)
    @Test
    fun test4() {
        val exception = assertThrows(
            InternalServerErrorException::class.java
        ) { execute4() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.INTERNAL_SERVER_ERROR.value())
        assertEquals(exception.showType, ShowType.ERROR_MESSAGE)
        assertEquals(exception.traceId, traceId)
        assertEquals(exception.host, host)
    }

    @Order(5)
    @Test
    fun test5() {
        val exception = assertThrows(
            InternalServerErrorException::class.java
        ) { execute5() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, errorCode)
        assertEquals(exception.showType, ShowType.ERROR_MESSAGE)
        assertEquals(exception.traceId, traceId)
        assertEquals(exception.host, host)
    }

    @Order(6)
    @Test
    fun test6() {
        val exception = assertThrows(
            InternalServerErrorException::class.java
        ) { execute6() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.INTERNAL_SERVER_ERROR.value())
        assertEquals(exception.showType, showType)
        assertEquals(exception.traceId, traceId)
        assertEquals(exception.host, host)
    }

    @Order(7)
    @Test
    fun test7() {
        val exception = assertThrows(
            InternalServerErrorException::class.java
        ) { execute7() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, errorCode)
        assertEquals(exception.showType, showType)
        assertEquals(exception.traceId, traceId)
        assertEquals(exception.host, host)
    }
}

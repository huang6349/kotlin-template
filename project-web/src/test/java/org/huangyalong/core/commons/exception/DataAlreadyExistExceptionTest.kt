package org.huangyalong.core.commons.exception

import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.errorMessage
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.execute1
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.execute2
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.execute3
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.execute4
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.execute5
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.execute6
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.execute7
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.execute8
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.host
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.showType
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.traceId
import org.huangyalong.core.commons.info.ShowType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.http.HttpStatus

@TestMethodOrder(OrderAnnotation::class)
class DataAlreadyExistExceptionTest {

    @Order(1)
    @Test
    fun test1() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { execute1() }
        assertEquals(exception.message, "数据已存在")
        assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        assertEquals(exception.showType, ShowType.WARN_MESSAGE)
        assertNull(exception.traceId)
        assertNull(exception.host)
    }

    @Order(2)
    @Test
    fun test2() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { execute2() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        assertEquals(exception.showType, ShowType.WARN_MESSAGE)
        assertNull(exception.traceId)
        assertNull(exception.host)
    }

    @Order(3)
    @Test
    fun test3() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { execute3() }
        assertEquals(exception.message, "数据已存在")
        assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        assertEquals(exception.showType, showType)
        assertNull(exception.traceId)
        assertNull(exception.host)
    }

    @Order(4)
    @Test
    fun test4() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { execute4() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        assertEquals(exception.showType, showType)
        assertNull(exception.traceId)
        assertNull(exception.host)
    }

    @Order(5)
    @Test
    fun test5() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { execute5() }
        assertEquals(exception.message, "数据已存在")
        assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        assertEquals(exception.showType, ShowType.WARN_MESSAGE)
        assertEquals(exception.traceId, traceId)
        assertEquals(exception.host, host)
    }

    @Order(6)
    @Test
    fun test6() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { execute6() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        assertEquals(exception.showType, ShowType.WARN_MESSAGE)
        assertEquals(exception.traceId, traceId)
        assertEquals(exception.host, host)
    }

    @Order(7)
    @Test
    fun test7() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { execute7() }
        assertEquals(exception.message, "数据已存在")
        assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        assertEquals(exception.showType, showType)
        assertEquals(exception.traceId, traceId)
        assertEquals(exception.host, host)
    }

    @Order(8)
    @Test
    fun test8() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { execute8() }
        assertEquals(exception.message, errorMessage)
        assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        assertEquals(exception.showType, showType)
        assertEquals(exception.traceId, traceId)
        assertEquals(exception.host, host)
    }
}

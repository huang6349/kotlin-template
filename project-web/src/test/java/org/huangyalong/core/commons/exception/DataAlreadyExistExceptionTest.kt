package org.huangyalong.core.commons.exception

import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.errorMessage
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.host
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.showType
import org.huangyalong.core.commons.exception.DataAlreadyExistExceptionTestUtil.Companion.traceId
import org.huangyalong.core.commons.info.ShowType
import org.junit.jupiter.api.Assertions
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
        ) { DataAlreadyExistExceptionTestUtil.execute1() }
        Assertions.assertEquals(exception.message, "数据已存在")
        Assertions.assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        Assertions.assertEquals(exception.showType, ShowType.WARN_MESSAGE)
        Assertions.assertNull(exception.traceId)
        Assertions.assertNull(exception.host)
    }

    @Order(2)
    @Test
    fun test2() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { DataAlreadyExistExceptionTestUtil.execute2() }
        Assertions.assertEquals(exception.message, errorMessage)
        Assertions.assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        Assertions.assertEquals(exception.showType, ShowType.WARN_MESSAGE)
        Assertions.assertNull(exception.traceId)
        Assertions.assertNull(exception.host)
    }

    @Order(3)
    @Test
    fun test3() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { DataAlreadyExistExceptionTestUtil.execute3() }
        Assertions.assertEquals(exception.message, "数据已存在")
        Assertions.assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        Assertions.assertEquals(exception.showType, showType)
        Assertions.assertNull(exception.traceId)
        Assertions.assertNull(exception.host)
    }

    @Order(4)
    @Test
    fun test4() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { DataAlreadyExistExceptionTestUtil.execute4() }
        Assertions.assertEquals(exception.message, errorMessage)
        Assertions.assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        Assertions.assertEquals(exception.showType, showType)
        Assertions.assertNull(exception.traceId)
        Assertions.assertNull(exception.host)
    }

    @Order(5)
    @Test
    fun test5() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { DataAlreadyExistExceptionTestUtil.execute5() }
        Assertions.assertEquals(exception.message, "数据已存在")
        Assertions.assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        Assertions.assertEquals(exception.showType, ShowType.WARN_MESSAGE)
        Assertions.assertEquals(exception.traceId, traceId)
        Assertions.assertEquals(exception.host, host)
    }

    @Order(6)
    @Test
    fun test6() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { DataAlreadyExistExceptionTestUtil.execute6() }
        Assertions.assertEquals(exception.message, errorMessage)
        Assertions.assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        Assertions.assertEquals(exception.showType, ShowType.WARN_MESSAGE)
        Assertions.assertEquals(exception.traceId, traceId)
        Assertions.assertEquals(exception.host, host)
    }

    @Order(7)
    @Test
    fun test7() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { DataAlreadyExistExceptionTestUtil.execute7() }
        Assertions.assertEquals(exception.message, "数据已存在")
        Assertions.assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        Assertions.assertEquals(exception.showType, showType)
        Assertions.assertEquals(exception.traceId, traceId)
        Assertions.assertEquals(exception.host, host)
    }

    @Order(8)
    @Test
    fun test8() {
        val exception = Assertions.assertThrows(
            DataAlreadyExistException::class.java
        ) { DataAlreadyExistExceptionTestUtil.execute8() }
        Assertions.assertEquals(exception.message, errorMessage)
        Assertions.assertEquals(exception.errorCode, HttpStatus.CONFLICT.value())
        Assertions.assertEquals(exception.showType, showType)
        Assertions.assertEquals(exception.traceId, traceId)
        Assertions.assertEquals(exception.host, host)
    }
}

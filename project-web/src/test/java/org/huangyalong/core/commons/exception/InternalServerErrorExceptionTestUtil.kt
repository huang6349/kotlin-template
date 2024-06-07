package org.huangyalong.core.commons.exception

import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.RandomUtil
import org.huangyalong.core.commons.info.ShowType
import org.springframework.http.HttpStatus

class InternalServerErrorExceptionTestUtil {

    companion object {

        val errorMessage: String = RandomUtil.randomStringUpper(15)

        val errorCode: Int = HttpStatus.INTERNAL_SERVER_ERROR.value()

        val showType: ShowType = ShowType.NOTIFICATION

        val traceId: String = IdUtil.randomUUID()

        val host: String = RandomUtil.randomStringUpper(15)

        fun execute1(): Unit = throw InternalServerErrorException(errorMessage)

        fun execute2(): Unit = throw InternalServerErrorException(errorMessage, errorCode)

        fun execute3(): Unit = throw InternalServerErrorException(errorMessage, errorCode, showType)

        fun execute4(): Unit = throw InternalServerErrorException(errorMessage, traceId, host)

        fun execute5(): Unit = throw InternalServerErrorException(errorMessage, errorCode, traceId, host)

        fun execute6(): Unit = throw InternalServerErrorException(errorMessage, showType, traceId, host)

        fun execute7(): Unit = throw InternalServerErrorException(errorMessage, errorCode, showType, traceId, host)
    }
}

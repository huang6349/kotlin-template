package org.huangyalong.core.commons.exception

import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.RandomUtil
import org.huangyalong.core.commons.info.ShowType

class DataAlreadyExistExceptionTestUtil {

    companion object {

        val errorMessage: String = RandomUtil.randomStringUpper(15)

        val showType: ShowType = ShowType.NOTIFICATION

        val traceId: String = IdUtil.randomUUID()

        val host: String = RandomUtil.randomStringUpper(15)

        fun execute1(): Unit = throw DataAlreadyExistException()

        fun execute2(): Unit = throw DataAlreadyExistException(errorMessage)

        fun execute3(): Unit = throw DataAlreadyExistException(showType)

        fun execute4(): Unit = throw DataAlreadyExistException(errorMessage, showType)

        fun execute5(): Unit = throw DataAlreadyExistException(traceId, host)

        fun execute6(): Unit = throw DataAlreadyExistException(errorMessage, traceId, host)

        fun execute7(): Unit = throw DataAlreadyExistException(showType, traceId, host)

        fun execute8(): Unit = throw DataAlreadyExistException(errorMessage, showType, traceId, host)
    }
}

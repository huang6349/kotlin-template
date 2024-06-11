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

        fun execute1() {
            throw DataAlreadyExistException()
        }

        fun execute2() {
            throw DataAlreadyExistException(errorMessage)
        }

        fun execute3() {
            throw DataAlreadyExistException(showType)
        }

        fun execute4() {
            throw DataAlreadyExistException(errorMessage, showType)
        }

        fun execute5() {
            throw DataAlreadyExistException(traceId, host)
        }

        fun execute6() {
            throw DataAlreadyExistException(errorMessage, traceId, host)
        }

        fun execute7() {
            throw DataAlreadyExistException(showType, traceId, host)
        }

        fun execute8() {
            throw DataAlreadyExistException(errorMessage, showType, traceId, host)
        }
    }
}

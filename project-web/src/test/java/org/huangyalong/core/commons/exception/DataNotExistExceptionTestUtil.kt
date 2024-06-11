package org.huangyalong.core.commons.exception

import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.RandomUtil
import org.huangyalong.core.commons.info.ShowType

class DataNotExistExceptionTestUtil {

    companion object {

        val errorMessage: String = RandomUtil.randomStringUpper(15)

        val showType: ShowType = ShowType.NOTIFICATION

        val traceId: String = IdUtil.randomUUID()

        val host: String = RandomUtil.randomStringUpper(15)

        fun execute1() {
            throw DataNotExistException()
        }

        fun execute2() {
            throw DataNotExistException(errorMessage)
        }

        fun execute3() {
            throw DataNotExistException(showType)
        }

        fun execute4() {
            throw DataNotExistException(errorMessage, showType)
        }

        fun execute5() {
            throw DataNotExistException(traceId, host)
        }

        fun execute6() {
            throw DataNotExistException(errorMessage, traceId, host)
        }

        fun execute7() {
            throw DataNotExistException(showType, traceId, host)
        }

        fun execute8() {
            throw DataNotExistException(errorMessage, showType, traceId, host)
        }
    }
}

package org.huangyalong.core.commons.exception

import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.RandomUtil
import org.huangyalong.core.commons.info.ShowType

class BadRequestExceptionTestUtil {

    companion object {

        val errorMessage: String = RandomUtil.randomStringUpper(15)

        val showType: ShowType = ShowType.NOTIFICATION

        val traceId: String = IdUtil.randomUUID()

        val host: String = RandomUtil.randomStringUpper(15)

        fun execute1() {
            throw BadRequestException(errorMessage)
        }

        fun execute2() {
            throw BadRequestException(errorMessage, showType)
        }

        fun execute3() {
            throw BadRequestException(errorMessage, traceId, host)
        }

        fun execute4() {
            throw BadRequestException(errorMessage, showType, traceId, host)
        }
    }
}

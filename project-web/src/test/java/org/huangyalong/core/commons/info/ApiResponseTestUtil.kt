package org.huangyalong.core.commons.info

import cn.hutool.core.collection.CollUtil
import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.RandomUtil
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus

class ApiResponseTestUtil {

    companion object {

        val data: List<Int> = CollUtil.newArrayList(1, 2, 3)

        val message: String = RandomUtil.randomStringUpper(15)

        val code: Int = HttpStatus.INTERNAL_SERVER_ERROR.value()

        val e: String = RandomUtil.randomStringUpper(15)

        val traceId: String = IdUtil.randomUUID()

        val host: String = RandomUtil.randomStringUpper(15)

        fun compare(
            a: ApiResponse<List<Int>>,
            b: ApiResponse<List<Int>>,
        ) {
            assertThat(a.success).isEqualTo(b.success)
            assertThat(a.data).isEqualTo(b.data)
            assertThat(a.message).isEqualTo(b.message)
            assertThat(a.code).isEqualTo(b.code)
            assertThat(a.showType).isEqualTo(b.showType)
            assertThat(a.e).isEqualTo(b.e)
            assertThat(a.traceId).isEqualTo(b.traceId)
            assertThat(a.host).isEqualTo(b.host)
        }
    }
}

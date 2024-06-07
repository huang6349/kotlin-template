package org.huangyalong.core.commons.info

import cn.hutool.core.collection.CollUtil
import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.RandomUtil
import org.assertj.core.api.Assertions
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
            b: ApiResponse<List<Int>>
        ) {
            Assertions.assertThat(a.success).isEqualTo(b.success)
            Assertions.assertThat(a.data).isEqualTo(b.data)
            Assertions.assertThat(a.message).isEqualTo(b.message)
            Assertions.assertThat(a.code).isEqualTo(b.code)
            Assertions.assertThat(a.showType).isEqualTo(b.showType)
            Assertions.assertThat(a.e).isEqualTo(b.e)
            Assertions.assertThat(a.traceId).isEqualTo(b.traceId)
            Assertions.assertThat(a.host).isEqualTo(b.host)
        }
    }
}

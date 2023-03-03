package org.huangyalong.crud.web

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import java.io.Serializable

interface DeleteController<M : BaseMapper<T>?, T> {
    val service: ServiceImpl<M, T>

    @DeleteMapping("/{id:.+}")
    fun delete(@PathVariable id: Serializable?): Boolean {
        return service
            .removeById(id)
    }
}

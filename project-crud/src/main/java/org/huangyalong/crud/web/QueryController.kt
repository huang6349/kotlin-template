package org.huangyalong.crud.web

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.web.bind.annotation.PostMapping
import com.baomidou.mybatisplus.core.metadata.IPage
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.io.Serializable

interface QueryController<M : BaseMapper<T>?, T> {
    val service: ServiceImpl<M, T>

    @PostMapping("/_query/no-paging")
    fun postQuery(): List<T>? {
        return query()
    }

    @PostMapping("/_query")
    fun <E : IPage<T>?> postQueryPage(page: E): E {
        return queryPage(page)
    }

    @PostMapping("/_count")
    fun postCount(): Long {
        return count()
    }

    @GetMapping("/_query/no-paging")
    fun query(): List<T>? {
        return service
            .list()
    }

    @GetMapping("/_query")
    fun <E : IPage<T>?> queryPage(page: E): E {
        return service
            .page(page)
    }

    @GetMapping("/_count")
    fun count(): Long = service
        .count()

    @GetMapping("/{id:.+}")
    fun getById(@PathVariable id: Serializable?): T {
        return service
            .getById(id)
    }
}

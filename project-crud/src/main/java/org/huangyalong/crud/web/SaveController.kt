package org.huangyalong.crud.web

import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping

interface SaveController<M : BaseMapper<T>, T> {
    val service: ServiceImpl<M, T>

    @PatchMapping
    fun save(@RequestBody payload: List<T>): Boolean {
        return service
            .saveOrUpdateBatch(payload)
    }

    @PostMapping("/_batch")
    fun add(@RequestBody payload: List<T>): Boolean {
        return service
            .saveBatch(payload)
    }

    @PostMapping
    fun add(@RequestBody payload: T): Boolean {
        return service
            .save(payload)
    }

    @PutMapping("/_batch")
    fun update(@RequestBody payload: List<T>): Boolean {
        return service
            .updateBatchById(payload)
    }

    @PutMapping
    fun update(@RequestBody payload: T): Boolean {
        return service
            .updateById(payload)
    }
}

package org.huangyalong.crud.web

import com.baomidou.mybatisplus.core.mapper.BaseMapper

interface CrudController<M : BaseMapper<T>, T> : SaveController<M, T>,
    QueryController<M, T>,
    DeleteController<M, T>

package org.huangyalong.core.web

import com.mybatisflex.core.service.IService

interface BaseController<E> {

    val service: IService<E>?
}

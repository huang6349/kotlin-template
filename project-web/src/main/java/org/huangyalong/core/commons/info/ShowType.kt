package org.huangyalong.core.commons.info

enum class ShowType(val showType: Int) {
    SILENT(0),
    WARN_MESSAGE(1),
    ERROR_MESSAGE(2),
    NOTIFICATION(4),
    REDIRECT(9);
}

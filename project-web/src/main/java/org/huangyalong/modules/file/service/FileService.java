package org.huangyalong.modules.file.service;

import com.mybatisflex.core.service.IService;
import org.huangyalong.modules.file.domain.File;

public interface FileService extends IService<File> {

    void useByIds(String objectType,
                  Long objectId,
                  Object... ids);
}

package org.huangyalong.modules.file.service.impl;

import cn.hutool.core.convert.Convert;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.val;
import org.huangyalong.modules.file.domain.File;
import org.huangyalong.modules.file.enums.FileStatus;
import org.huangyalong.modules.file.mapper.FileMapper;
import org.huangyalong.modules.file.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.huangyalong.modules.file.domain.table.FileTableDef.FILE;

@AllArgsConstructor
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File>
        implements FileService {

    @Transactional(rollbackFor = Exception.class)
    public void useByIds(String objectType,
                         Long objectId,
                         Object... ids) {
        val query = QueryWrapper.create()
                .where(FILE.ID.in(ids));
        val file = File.create()
                .setObjectType(objectType)
                .setObjectId(Convert.toStr(objectId))
                .setStatus(FileStatus.TYPE1);
        super.update(file, query);
    }
}

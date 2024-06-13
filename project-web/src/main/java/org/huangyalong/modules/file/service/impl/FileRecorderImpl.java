package org.huangyalong.modules.file.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Opt;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.val;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.dromara.x.file.storage.core.upload.FilePartInfo;
import org.huangyalong.core.commons.exception.DataNotExistException;
import org.huangyalong.modules.file.domain.File;
import org.huangyalong.modules.file.domain.FilePart;
import org.huangyalong.modules.file.service.FilePartService;
import org.huangyalong.modules.file.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.huangyalong.modules.file.domain.table.FilePartTableDef.FILE_PART;
import static org.huangyalong.modules.file.domain.table.FileTableDef.FILE;

@AllArgsConstructor
@Service
public class FileRecorderImpl implements FileRecorder {

    private final FilePartService filePartService;

    private final FileService fileService;

    @Transactional(rollbackFor = Exception.class)
    public boolean save(FileInfo fileInfo) {
        val data = File.create()
                .with(fileInfo);
        fileService.save(data);
        Opt.ofNullable(data)
                .map(File::getId)
                .map(Convert::toStr)
                .ifPresent(fileInfo::setId);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(FileInfo fileInfo) {
        val data = fileService.getByIdOpt(fileInfo.getId())
                .orElseThrow(DataNotExistException::new)
                .with(fileInfo);
        fileService.updateById(data);
    }

    @Override
    public FileInfo getByUrl(String url) {
        val query = QueryWrapper.create()
                .where(FILE.URL.eq(url));
        return fileService.getOne(query)
                .without();
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(String url) {
        val query = QueryWrapper.create()
                .where(FILE.URL.eq(url));
        fileService.remove(query);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFilePart(FilePartInfo filePartInfo) {
        val data = FilePart.create()
                .with(filePartInfo);
        filePartService.save(data);
        Opt.ofNullable(data)
                .map(FilePart::getId)
                .map(Convert::toStr)
                .ifPresent(filePartInfo::setId);
    }

    @Override
    public void deleteFilePartByUploadId(String uploadId) {
        val query = QueryWrapper.create()
                .where(FILE_PART.UPLOAD_ID.eq(uploadId));
        filePartService.remove(query);
    }
}

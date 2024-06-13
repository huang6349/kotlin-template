package org.huangyalong.modules.file.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.huangyalong.modules.file.domain.FilePart;
import org.huangyalong.modules.file.mapper.FilePartMapper;
import org.huangyalong.modules.file.service.FilePartService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FilePartServiceImpl extends ServiceImpl<FilePartMapper, FilePart>
        implements FilePartService {
}

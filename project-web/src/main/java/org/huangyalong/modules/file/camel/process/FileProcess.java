package org.huangyalong.modules.file.camel.process;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.val;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.huangyalong.modules.file.domain.File;
import org.huangyalong.modules.file.enums.FileStatus;
import org.huangyalong.modules.file.service.FileService;

import java.util.stream.Collectors;

import static org.huangyalong.modules.file.domain.table.FileTableDef.FILE;

public class FileProcess implements Processor {

    @Override
    public void process(Exchange exchange) {
        val fileService = SpringUtil.getBean(FileService.class);
        val query = QueryWrapper.create()
                .where(FILE.CREATE_TIME.le(DateUtil.offsetDay(DateUtil.date(), -1)))
                .and(FILE.STATUS.eq(FileStatus.TYPE0));
        val files = fileService.list(query);
        val urls = files.stream()
                .map(File::getUrl)
                .collect(Collectors.toList());
        exchange.setProperty("urls", urls);
    }
}

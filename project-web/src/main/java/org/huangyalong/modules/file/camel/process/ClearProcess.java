package org.huangyalong.modules.file.camel.process;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.val;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.dromara.x.file.storage.core.FileStorageService;

public class ClearProcess implements Processor {

    @Override
    public void process(Exchange exchange) {
        val url = exchange.getIn()
                .getBody(String.class);
        if (ObjectUtil.isEmpty(url)) return;
        val storageService = SpringUtil.getBean(FileStorageService.class);
        storageService.delete(url);
    }
}

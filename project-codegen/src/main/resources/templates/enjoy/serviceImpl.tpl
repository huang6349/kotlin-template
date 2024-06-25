#set(controllerPackage = packageConfig.controllerPackage)
#set(serviceImplPackage = packageConfig.serviceImplPackage)
#set(servicePackage = packageConfig.servicePackage)
#set(entityPackage = packageConfig.entityPackage)
#set(mapperPackage = packageConfig.mapperPackage)
#set(basePackage = packageConfig.basePackage)
#set(boPackage = basePackage.concat(".request"))
#set(superClassImport = serviceImplConfig.buildSuperClassImport())
#set(superClassName = serviceImplConfig.buildSuperClassName())
#set(serviceImplClassName = table.buildServiceImplClassName())
#set(serviceClassName = table.buildServiceClassName())
#set(mapperClassName = table.buildMapperClassName())
#set(entityClassName = table.buildEntityClassName())
#set(boClassName = entityClassName.concat("BO"))
package #(serviceImplPackage);

import cn.hutool.core.bean.BeanUtil;
import #(superClassImport);
import lombok.AllArgsConstructor;
import lombok.val;
import #(entityPackage).#(entityClassName);
import #(mapperPackage).#(mapperClassName);
import #(boPackage).#(boClassName);
import #(servicePackage).#(serviceClassName);
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class #(serviceImplClassName) extends #(superClassName)<#(mapperClassName), #(entityClassName)>
        implements #(serviceClassName) {

    @Transactional(rollbackFor = Exception.class)
    public boolean add(#(boClassName) payload) {
        val data = BeanUtil.copyProperties(payload, #(entityClassName).class);
        super.save(data);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean update(#(boClassName) payload) {
        val data = BeanUtil.copyProperties(payload, #(entityClassName).class);
        super.updateById(data);
        return Boolean.TRUE;
    }
}

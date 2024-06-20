#set(controllerPackage = packageConfig.controllerPackage)
#set(servicePackage = packageConfig.servicePackage)
#set(entityPackage = packageConfig.entityPackage)
#set(basePackage = packageConfig.basePackage)
#set(boPackage = basePackage.concat(".request"))
#set(superClassImport = serviceConfig.buildSuperClassImport())
#set(superClassName = serviceConfig.buildSuperClassName())
#set(serviceClassName = table.buildServiceClassName())
#set(entityClassName = table.buildEntityClassName())
#set(boClassName = entityClassName.concat("BO"))
package #(servicePackage);

import #(superClassImport);
import #(entityPackage).#(entityClassName);
import #(boPackage).#(boClassName);

public interface #(serviceClassName) extends #(superClassName)<#(entityClassName)> {

    boolean add(#(boClassName) payload);

    boolean update(#(boClassName) payload);
}

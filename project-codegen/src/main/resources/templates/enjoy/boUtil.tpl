#set(basePackage = packageConfig.basePackage)
#set(boUtilPackage = basePackage.concat(".request"))
#set(entityClassName = table.buildEntityClassName())
#set(boUtilClassName = entityClassName.concat("Util"))
#set(boClassName = entityClassName.concat("BO"))
#set(boVarName = firstCharToLowerCase(boClassName))
package #(boUtilPackage);

import cn.hutool.json.JSONObject;
import lombok.val;

public final class #(boUtilClassName) {

    public static #(boClassName) createBO(JSONObject jsonObject) {
        val #(boVarName) = new #(boClassName)();
        val id = jsonObject.getLong("id");
        #(boVarName).setId(id);
        return #(boVarName);
    }

    public static #(boClassName) createBO() {
        val jsonObject = new JSONObject();
        return createBO(jsonObject);
    }
}

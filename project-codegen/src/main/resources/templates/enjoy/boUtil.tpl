#set(entityClassName = table.buildEntityClassName())
#set(boClassName = entityClassName.concat("BO"))
#set(boVarName = firstCharToLowerCase(boClassName))
package #(packageName);

import cn.hutool.json.JSONObject;
import lombok.val;

public final class #(className) {

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

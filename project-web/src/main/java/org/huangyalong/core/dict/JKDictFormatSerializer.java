package org.huangyalong.core.dict;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.val;

import java.io.IOException;

public class JKDictFormatSerializer extends StdSerializer<Object> {

    protected JKDictFormatSerializer() {
        super(Object.class);
    }

    @Override
    public void serialize(Object value,
                          JsonGenerator gen,
                          SerializerProvider provider) throws IOException {
        if (!(value instanceof EnumDict<?>)) {
            provider.defaultSerializeValue(value, gen);
        } else {
            val currentName = gen.getOutputContext()
                    .getCurrentName();
            val enumDict = (EnumDict<?>) value;
            gen.writeObject(enumDict.getValue());
            gen.writeFieldName(StrUtil.format("{}Tag", currentName));
            gen.writeStartObject();
            gen.writeFieldName("label");
            gen.writeString(enumDict.getLabel());
            gen.writeFieldName("value");
            gen.writeObject(enumDict.getValue());
            gen.writeFieldName("style");
            gen.writeNumber(enumDict.getStyle());
            gen.writeEndObject();
        }
    }
}

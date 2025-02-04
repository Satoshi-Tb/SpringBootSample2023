package com.example.rest.sandbox.model;


import java.io.IOException;
import java.util.Map;

import com.example.rest.sandbox.model.GridDynamicColumnModel.RowDataV2;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * JSONレスポンス加工用
 * */
public class RowDataV2Serializer extends JsonSerializer<RowDataV2> {
    @Override
    public void serialize(RowDataV2 rowData, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        
        // 基本フィールドをシリアライズ
        gen.writeStringField("id", rowData.id());
        gen.writeStringField("category", rowData.category());
        gen.writeStringField("item", rowData.item());

        // detailItems のキーと値をトップレベルに展開
        for (Map.Entry<String, Object> entry : rowData.detailItems().entrySet()) {
            gen.writeObjectField(entry.getKey(), entry.getValue());
        }

        gen.writeEndObject();
    }
}

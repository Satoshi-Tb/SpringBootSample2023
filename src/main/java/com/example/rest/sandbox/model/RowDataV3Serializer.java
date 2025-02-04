package com.example.rest.sandbox.model;


import java.io.IOException;

import com.example.rest.sandbox.model.GridDynamicColumnModel.DetailItemSub;
import com.example.rest.sandbox.model.GridDynamicColumnModel.RowDataV3;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * JSONレスポンス加工用
 * */
public class RowDataV3Serializer extends JsonSerializer<RowDataV3> {
    @Override
    public void serialize(RowDataV3 rowData, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        
        // 基本フィールドをシリアライズ
        gen.writeStringField("id", rowData.id());
        gen.writeStringField("category", rowData.category());
        gen.writeStringField("item", rowData.item());

        // detailItems のキーと値をトップレベルに展開
        for (var entry : rowData.detailItems()) {
            gen.writeObjectField(entry.gridFieldName(), new DetailItemSub(entry.id(), entry.fieldName(), entry.value()));
        }

        gen.writeEndObject();
    }
}

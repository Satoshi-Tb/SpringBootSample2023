package com.example.rest.sandbox.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.rest.sandbox.model.GridDynamicColumnModel.DetailItem;
import com.example.rest.sandbox.model.GridDynamicColumnModel.RowData;
import com.example.rest.sandbox.model.GridDynamicColumnModel.RowDataV2;
import com.example.rest.controller.RestResponse;
import com.example.rest.sandbox.model.GridDynamicColumnService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/grid")
@Slf4j
public class NextJsSandboxController {
	
	@Autowired
	private GridDynamicColumnService gridDynamicColumnsService;
	
	@GetMapping("/dynamic-column/list/{id}")
	public ResponseEntity<RestResponse<GridDynamicColumnResponse>> getGridDynamicColumnDataList(@PathVariable String id) {
		
		var response = new GridDynamicColumnResponse();
		
		response.setColDefData(gridDynamicColumnsService.getColumnDefinitions(id));
		response.setRowData(gridDynamicColumnsService.getRowData(id));

		return RestResponse.createSuccessResponse(response);
	}

	@GetMapping("/dynamic-column/list/v2/{id}")
	public ResponseEntity<RestResponse<GridDynamicColumnResponseV2>> getGridDynamicColumnDataList2(@PathVariable String id) {
		
		var response = new GridDynamicColumnResponseV2();
		
		response.setColDefData(gridDynamicColumnsService.getColumnDefinitions(id));
		var rows = gridDynamicColumnsService.getRowData(id);
		
		// freeItem1～Nをプロパティのキー名に格上げして、フラット化
		// この場合、明確な型指定ができない。おそらくDBの型でデフォルトマップされると思われる
		List<Map<String, Object>> transformedData = rows.stream()
	            .map(NextJsSandboxController::convertRowData)
	            .collect(Collectors.toList());
		
		response.setRowData(transformedData);

		//	    ObjectMapper objectMapper = new ObjectMapper();
		//	    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		//	    String json = objectMapper.writeValueAsString(Map.of("rowData", transformedData));	
		
		return RestResponse.createSuccessResponse(response);
	}
	
	@GetMapping("/dynamic-column/list/v3/{id}")
	public ResponseEntity<RestResponse<GridDynamicColumnResponseV3>> getGridDynamicColumnDataList3(@PathVariable String id) {
		
		var response = new GridDynamicColumnResponseV3();
		
		response.setColDefData(gridDynamicColumnsService.getColumnDefinitions(id));
		var rows = gridDynamicColumnsService.getRowData(id);
		
		// カスタムシリアライザーを利用して、detailItemsプロパティの中身を、キー名をプロパティとしてフラット化
		var transformedData = rows.stream()
	            .map(NextJsSandboxController::convertRowDataV2)
	            .collect(Collectors.toList());
		
		response.setRowData(transformedData);
		
		return RestResponse.createSuccessResponse(response);
	}
	
	@GetMapping("/dynamic-column/list/v4/{id}")
	public ResponseEntity<RestResponse<GridDynamicColumnResponseV4>> getGridDynamicColumnDataList4(@PathVariable String id) {
		
		var response = new GridDynamicColumnResponseV4();
		
		response.setColDefData(gridDynamicColumnsService.getColumnDefinitions(id));
		response.setRowData(gridDynamicColumnsService.getRowDataV3(id));

		return RestResponse.createSuccessResponse(response);
	}
	// 型変換
    private static RowDataV2 convertRowDataV2(RowData rowData) {
    	
    	Map<String, Object> details = new HashMap<>();
        for (DetailItem detailItem : rowData.detailItems()) {
            Map<String, String> detailMap = Map.of(
                "id", detailItem.id(),
                "fieldName", detailItem.fieldName(),
                "value", detailItem.value()
            );
            details.put(detailItem.gridFieldName(), detailMap);
        }
        var result = new RowDataV2(rowData.id(), rowData.category(), rowData.item(), details);

        return result;
    }
	
	// 型変換
    private static Map<String, Object> convertRowData(RowData rowData) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("id", rowData.id());
        result.put("category", rowData.category());
        result.put("item", rowData.item());

        for (DetailItem detailItem : rowData.detailItems()) {
            Map<String, String> detailMap = Map.of(
                "id", detailItem.id(),
                "fieldName", detailItem.fieldName(),
                "value", detailItem.value()
            );
            result.put(detailItem.gridFieldName(), detailMap);
        }
        return result;
    }
}

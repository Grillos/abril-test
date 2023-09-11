package com.abril.test.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PdfRequest {
	
	@ApiModelProperty(value = "The url bucket for get images", required = false, position = 1, dataType = "string", example = "http://image_editora_abril.com")
	private String url;

}

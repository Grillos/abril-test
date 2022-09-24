package com.abril.test.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignatureRequest {
	
	@ApiModelProperty(value = "The product name for search signature", required = false, position = 1, dataType = "string", example = "product x")
	private String product;
	
	@ApiModelProperty(value = "The cep for search signature", required = false, position = 2, dataType = "long", example = "05594000")
	private Long cep;
	

}

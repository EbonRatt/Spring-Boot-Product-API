package com.api.product.model.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class ProductResponse<T> extends BaseResponse{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payLoad;
}

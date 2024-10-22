package com.swp391.koibe.responses.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp391.koibe.responses.base.BasePaginationResponse;
import com.swp391.koibe.responses.order.OrderResponse;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class OrderPaginationResponse extends BasePaginationResponse {
        @JsonProperty("item")
        private List<OrderResponse> item;
}

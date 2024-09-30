package com.swp391.koibe.responses.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp391.koibe.responses.KoiResponse;
import com.swp391.koibe.responses.base.BasePaginationResponse;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class KoiPaginationResponse extends BasePaginationResponse {

    @JsonProperty("items")
    private List<KoiResponse> items;

}

package com.swp391.koibe.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.responses.base.BaseResponse;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KoiResponse extends BaseResponse<Koi> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("length")
    private int length;

    @JsonProperty("age")
    private int age;

    @JsonProperty("base_price")
    private Long price;

    @JsonProperty("status_name")
    private String statusName;

    @JsonProperty("is_display")
    private int idDisplay;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("description")
    private String description;

    @JsonProperty("owner_id")
    private long ownerId;

    @JsonProperty("category_id")
    private int categoryId;

}

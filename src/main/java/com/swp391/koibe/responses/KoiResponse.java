package com.swp391.koibe.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.responses.base.BaseResponse;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
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
    private Integer length;

    @JsonProperty("year_born")
    private Integer yearBorn;

    @JsonProperty("base_price")
    private Long price;

    @JsonProperty("status_name")
    private String statusName;

    @JsonProperty("is_display")
    private Integer idDisplay;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("description")
    private String description;

    @JsonProperty("owner_id")
    private Long ownerId;

    @JsonProperty("category_id")
    private Integer categoryId;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("total_page")
    private Integer totalPage;
    //this field is for holding the total number of pages (in case query directly from database)
    //and retrieve from redis cache

}

package com.swp391.koibe.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp391.koibe.enums.EKoiStatus;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.responses.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KoiInAuctionResponse extends BaseResponse<Koi> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("length")
    private Integer length;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("base_price")
    private Long price;

    @JsonProperty("status_name")
    private String statusName;

    @JsonProperty("is_display")
    private Integer isDisplay;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("description")
    private String description;

    @JsonProperty("owner_id")
    private Long ownerId;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("auction_id")
    private Long auctionId;

    public KoiInAuctionResponse(Long id, String name, String sex, int length, int age, Long price,
                                EKoiStatus status, int isDisplay, String thumbnail, String description,
                                Long ownerId, Integer categoryId, Long auctionId) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.length = length;
        this.age = age;
        this.price = price;
        this.statusName = status.name();
        this.isDisplay = isDisplay;
        this.thumbnail = thumbnail;
        this.description = description;
        this.ownerId = ownerId;
        this.categoryId = categoryId;
        this.auctionId = auctionId;
    }
}

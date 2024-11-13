package com.swp391.koibe.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swp391.koibe.enums.EBidMethod;
import com.swp391.koibe.enums.EKoiStatus;
import com.swp391.koibe.models.Koi;
import com.swp391.koibe.responses.base.BaseResponse;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
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

    @JsonProperty("year_born")
    private Integer yearBorn;

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

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("auction_id")
    private Long auctionId;

    @JsonProperty("bid_method")
    private EBidMethod bidMethod;

    @JsonProperty("total_page")
    private Integer totalPage;

    public KoiInAuctionResponse(Long id, String name, String sex, int length, int yearBorn, Long price,
                                EKoiStatus status, int isDisplay, String thumbnail, String description,
                                Long ownerId, Integer categoryId, LocalDateTime createdAt, LocalDateTime updatedAt,
                                Long auctionId, EBidMethod bidMethod) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.length = length;
        this.yearBorn = yearBorn;
        this.price = price;
        this.statusName = status.name();
        this.isDisplay = isDisplay;
        this.thumbnail = thumbnail;
        this.description = description;
        this.ownerId = ownerId;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.auctionId = auctionId;
        this.bidMethod = bidMethod;
    }
}

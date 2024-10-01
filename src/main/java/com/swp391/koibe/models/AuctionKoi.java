package com.swp391.koibe.models;

import com.swp391.koibe.enums.EBidMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "auction_kois")
public class AuctionKoi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "base_price")
    @Min(0)
    private Long basePrice;

    @Column(name = "bid_step", columnDefinition = "int default 0")
    private long bidStep; // Track the bid step

    @Enumerated(EnumType.STRING)
    @Column(name = "bid_method")
    private EBidMethod bidMethod;

    @Column(name = "is_sold", nullable = false, columnDefinition = "boolean default false")
    private boolean isSold; // Track if the item has been sold

    // New fields
    @Column(name = "current_bid")
    private Long currentBid; // Track current highest bid

    @Column(name = "current_bidder_id") // Nullable since no bidder initially
    private Long currentBidderId; // Track the highest bidder

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "koi_id")
    private Koi koi;

//    @OneToMany(mappedBy = "auctionKoi")
//    private List<BidHistory> bidHistories;

}
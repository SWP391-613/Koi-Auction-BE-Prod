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
    private Float basePrice;

    // New fields
    @Column(name = "current_bid", nullable = false, columnDefinition = "int default 0")
    private int currentBid; // Track current highest bid

    @Column(name = "bid_step", nullable = false, columnDefinition = "int default 0")
    private int bidStep; // Track the bid step

    @Column(name = "current_bidder_id", nullable = true) // Nullable since no bidder initially
    private Long currentBidderId; // Track the highest bidder

    @Column(name = "is_sold", nullable = false, columnDefinition = "boolean default false")
    private boolean isSold; // Track if the item has been sold

    @Enumerated(EnumType.STRING)
    @Column(name = "bid_method")
    private EBidMethod bidMethod;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "koi_id")
    private Koi koi;

//    @OneToMany(mappedBy = "auctionKoi")
//    private List<BidHistory> bidHistories;

}
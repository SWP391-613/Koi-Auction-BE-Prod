package com.swp391.koibe.models;

import com.swp391.koibe.enums.EAuctionStatus;
import com.swp391.koibe.models.listener.AuctionListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "auctions")
@ToString
//@EntityListeners(AuctionListener.class)
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EAuctionStatus status;

    @ManyToOne
    @JoinColumn(name = "auctioneer_id")
    private User auctioneer;

//    @OneToMany(mappedBy = "auction")
//    private List<AuctionKoi> auctionKoisk;
//
//    @OneToMany(mappedBy = "auction")
//    private List<AuctionParticipant> auctionParticipants;

}

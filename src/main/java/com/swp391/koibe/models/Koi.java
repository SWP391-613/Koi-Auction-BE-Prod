package com.swp391.koibe.models;

import com.swp391.koibe.enums.EKoiStatus;
import com.swp391.koibe.models.listener.KoiListener;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kois")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@EntityListeners(KoiListener.class)
public class Koi extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //koi properties
    @Column(name = "name", nullable = false, length = 350)
    private String name;

    @Column(name = "sex", length = 10)
    private String sex;

    @Column(name = "length")
    private int length;

    @Column(name= "year_born")
    private int yearBorn;
    //koi properties

    @Column(name = "base_price") // Negotiated with Koi Breeder
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EKoiStatus status;

    @Column(name = "is_display")
    private int isDisplay;

    @Column(name = "thumbnail", length = 300)
    private String thumbnail;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner; //koi breeder

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @OneToMany(mappedBy = "koi")
//    private List<KoiImage> koiImages;
//
//    @OneToMany(mappedBy = "koi")
//    private List<AuctionKoi> auctionKoisk;
//
//    @OneToMany(mappedBy = "koi")
//    private List<OrderDetail> orderDetails;

}
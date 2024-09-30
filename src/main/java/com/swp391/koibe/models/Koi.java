package com.swp391.koibe.models;

import com.swp391.koibe.enums.EKoiStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kois")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Column(name= "age")
    private int age;
    //koi properties

    @Column(name = "base_price") // Negotiated with Koi Breeder
    private Float price;

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
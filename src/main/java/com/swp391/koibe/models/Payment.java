package com.swp391.koibe.models;

import com.swp391.koibe.enums.EPaymentStatus;
import com.swp391.koibe.enums.EPaymentType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_amount", nullable = false)
    private Float paymentAmount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private EPaymentStatus paymentStatus; // e.g., SUCCESS, PENDING, REFUNDED

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private EPaymentType paymentType; // e.g., 'DEPOSIT', 'ORDER', 'DRAW_OUT'
    
    // Track refunds related to this payment
//    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
//    private List<Refund> refunds;

    // Calculate total refunded amount
//    public Float getTotalRefundedAmount() {
//        return refunds.stream()
//            .map(Refund::getRefundAmount)
//            .reduce(0.0f, Float::sum);
//    }
//
//    // Check if the payment is refundable
//    public boolean isRefundable(Float refundAmount) {
//        return paymentAmount >= getTotalRefundedAmount() + refundAmount;
//    }
}


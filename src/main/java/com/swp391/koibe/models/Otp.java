package com.swp391.koibe.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "otps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Otp extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "otp")
    private String otp;

    @JsonProperty("expired_at")
    @Column(name = "expired_at")
    private LocalDateTime expiredAt; // in milliseconds

    @JsonProperty("is_used")
    @Column(name = "is_used")
    private boolean isUsed;

    @JsonProperty("is_expired")
    @Column(name = "is_expired")
    private boolean isExpired;

}

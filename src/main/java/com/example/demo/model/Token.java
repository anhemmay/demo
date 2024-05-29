package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Column(name = "expiration_date")
    private Date expirationDate;
    private Boolean expired;
    private Boolean revoked;
    @Column(name = "is_mobile")
    private Boolean isMobile;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

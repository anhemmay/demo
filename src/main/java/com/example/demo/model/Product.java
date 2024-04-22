package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "operator")
    private String operator;

    @Column(name = "types")
    private String types;

    @Column(name = "status", columnDefinition = "boolean default true")
    private Boolean status;

    @OneToMany(mappedBy = "product")
    private List<ProductDetail> productDetails;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Date createdAt;

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private Date updatedAt;

    @PrePersist
    public void onCreate(){
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    public void onUpdate(){
        updatedAt = new Date();
    }
}


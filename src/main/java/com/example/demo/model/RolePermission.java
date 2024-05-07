package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "role_permission")
public class RolePermission {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority")
    private String authority;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;

    public static final String CREATE_AUTHORITY = "POST";
    public static final String READ_AUTHORITY = "GET";
    public static final String UPDATE_AUTHORITY = "PUT";
    public static final String DELETE_AUTHORITY = "DELETE";

    public static final List<String> permissions = Arrays.asList(
            CREATE_AUTHORITY, READ_AUTHORITY, UPDATE_AUTHORITY, DELETE_AUTHORITY
    );

}

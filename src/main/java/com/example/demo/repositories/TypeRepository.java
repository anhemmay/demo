package com.example.demo.repositories;

import com.example.demo.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    List<Type> findAllByProductId(Long productId);
}

package com.microservice.building_be.repository;

import com.microservice.building_be.model.Card;
import com.microservice.building_be.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Page<Card> findAll(Pageable pageable);
}

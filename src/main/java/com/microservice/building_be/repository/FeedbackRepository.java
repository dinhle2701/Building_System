package com.microservice.building_be.repository;

import com.microservice.building_be.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Page<Feedback> findAll(Pageable pageable);

    @Query("SELECT f FROM Feedback f WHERE f.apartment.apartment_name = :apartment_name")
    List<Feedback> findByApartment_Apartment_name(String apartment_name);

    @Query(value = "SELECT f FROM Feedback f WHERE f.apartment.apartment_name = :apartment_name",
            countQuery = "SELECT count(f) FROM Feedback f WHERE f.apartment.apartment_name = :apartment_name")
    Page<Feedback> findByApartmentNameNative(@Param("apartment_name") String apartment_name, Pageable pageable);
}

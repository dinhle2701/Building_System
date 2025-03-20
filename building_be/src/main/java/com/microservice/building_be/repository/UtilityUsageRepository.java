package com.microservice.building_be.repository;

import com.microservice.building_be.dto.response.MonthlyUsageStats;
import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.UtilityUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtilityUsageRepository extends JpaRepository<UtilityUsage, Long> {
//    @Query("SELECT u FROM utilityUsage u WHERE u.apartment.apartment_id = :apartmentId ORDER BY u.create_date DESC")
//    List<UtilityUsage> findAllByApartmentId(@Param("apartmentId") Long apartmentId);

    // Tìm hóa đơn mới nhất của một apartment
    @Query("SELECT u FROM utilityUsage u WHERE u.apartment.apartment_id = :apartmentId ORDER BY u.createDate DESC")
    List<UtilityUsage> findByApartmentId(@Param("apartmentId") Long apartmentId);

    List<UtilityUsage> findByCreateDateBetween(Timestamp createDate, Timestamp updateDate);

    @Query("SELECT SUM(u.totalPrice) FROM utilityUsage u")
    Double calculateTotalPrice();

    Optional<UtilityUsage> findTopByApartmentOrderByCreateDateDesc(Apartment apartment);

}

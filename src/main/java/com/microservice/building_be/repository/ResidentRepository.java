package com.microservice.building_be.repository;

import com.microservice.building_be.dto.response.ResidentResponse;
import com.microservice.building_be.model.Feedback;
import com.microservice.building_be.model.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    Page<Resident> findAll(Pageable pageable);

    boolean existsByCccd(String cccd);
//    List<Resident> findByApartmentId(Long apartment_id);

    @Query("SELECT new com.microservice.building_be.dto.response.ResidentResponse(" +
            "r.resident_id, r.resident_name, r.phone_number, r.email, r.cccd, " +
            "r.birthday, r.sex, r.move_in_date, a.apartment_name) " +
            "FROM Resident r " +
            "LEFT JOIN r.apartment a") // Sử dụng LEFT JOIN thay vì JOIN
    Page<ResidentResponse> findAllResidentsWithApartmentName(Pageable pageable);
}

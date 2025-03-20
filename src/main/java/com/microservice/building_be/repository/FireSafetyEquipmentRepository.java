package com.microservice.building_be.repository;

import com.microservice.building_be.enums.EquipmentStatus;
import com.microservice.building_be.model.FireSafetyEquipment;
import com.microservice.building_be.model.Resident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FireSafetyEquipmentRepository extends JpaRepository<FireSafetyEquipment, Long> {
//    List<FireSafetyEquipment> findByStatus(EquipmentStatus status);
    Page<FireSafetyEquipment> findAll(Pageable pageable);

    Long countByStatus(String status);
}


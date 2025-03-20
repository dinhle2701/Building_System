package com.microservice.building_be.repository;
import com.microservice.building_be.dto.response.VehicleResponse;
import com.microservice.building_be.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT new com.microservice.building_be.dto.response.VehicleResponse(v.vehicle_id, v.vehicle_name, v.license_plate, v.vehicle_type, v.color, "
            + "r.resident_name, a.apartment_name) "
            + "FROM Vehicle v "
            + "LEFT JOIN v.resident r "
            + "LEFT JOIN r.apartment a")
    Page<VehicleResponse> findAllVehiclesWithResidentAndApartment(Pageable pageable);
}

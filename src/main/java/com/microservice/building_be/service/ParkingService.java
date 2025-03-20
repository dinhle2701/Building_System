package com.microservice.building_be.service;

import com.microservice.building_be.dto.request.ParkingRequest;
import com.microservice.building_be.model.Parking;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParkingService {
    List<Parking> getAllParking();

    Parking getParkingById(Long id);

    Parking createParking(ParkingRequest parkingRequest);
}

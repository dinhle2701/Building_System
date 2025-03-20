package com.microservice.building_be.service.serviceimpl;

import com.microservice.building_be.dto.request.ParkingRequest;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Parking;
import com.microservice.building_be.repository.ParkingRepository;
import com.microservice.building_be.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {
    @Autowired
    private ParkingRepository parkingRepository;
    @Override
    public List<Parking> getAllParking() {
        List<Parking> parkings = parkingRepository.findAll();
        return parkings;
    }

    @Override
    public Parking getParkingById(Long id) {
        Parking parking = parkingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found parking has id: " + id));
        return parking;
    }

    @Override
    public Parking createParking(ParkingRequest parkingRequest) {
        Parking parking = new Parking();
        parking.setPark_name(parkingRequest.getPark_name());
        parking.setPark_type(parkingRequest.getPark_type());
        parking.setPark_description(parkingRequest.getPark_description());
        return parkingRepository.save(parking);
    }
}

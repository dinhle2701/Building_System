package com.microservice.building_be.service;


import com.microservice.building_be.dto.request.CardRequest;
import com.microservice.building_be.dto.request.ResidentRequest;
import com.microservice.building_be.dto.request.VehicleRequest;
import com.microservice.building_be.dto.response.ResidentResponse;
import com.microservice.building_be.dto.response.VehicleResponse;
import com.microservice.building_be.model.Card;
import com.microservice.building_be.model.Resident;
import com.microservice.building_be.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResidentService {

    //    List<Resident> getResidents();
    Page<ResidentResponse> getAllResidents(int page, int size);

    Resident getResidentById(Long resident_id);

    Resident createNewResident(ResidentRequest residentRequest);

    Resident createResident(ResidentRequest residentRequest, Long apartmentId);

    Resident addResidentIntoApartment(Long residentId, Long apartmentId);

    Resident removeResidentFromApartment(Long residentId, Long apartmentId);

    Resident updateResidentById(Long resident_id, Resident updateResident);

    void deleteResidentById(Long resident_id);

    Vehicle addVehicleToResident(Long residentId, Long parking_id, VehicleRequest vehicleRequest);

    void deleteVehicle(Long vehicle_id);

    Page<VehicleResponse> getAllVehicles(int page, int size);
}
package com.microservice.building_be.service;

import com.microservice.building_be.dto.request.ApartmentRequest;
import com.microservice.building_be.dto.response.ApartmentResponse;
import com.microservice.building_be.model.Account;
import com.microservice.building_be.model.Apartment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApartmentService {

    ApartmentResponse getAllApartments();

    Page<Apartment> getAllStaffs(int page, int size);

    Apartment getApartmentById(Long apartment_id);

    Apartment saveApartment(ApartmentRequest apartmentRequest);

    Apartment updateApartmentById(Long apartment_id, ApartmentRequest updateApartment);

    void deleteApartmentById(Long apartment_id);


}

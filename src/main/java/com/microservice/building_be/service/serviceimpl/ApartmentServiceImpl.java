package com.microservice.building_be.service.serviceimpl;


import com.microservice.building_be.dto.request.ApartmentRequest;
import com.microservice.building_be.dto.response.ApartmentResponse;
import com.microservice.building_be.enums.ApartmentStatus;
import com.microservice.building_be.model.Account;
import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.repository.AccountRepository;
import com.microservice.building_be.repository.ApartmentRepository;
import com.microservice.building_be.service.ApartmentService;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ApartmentResponse getAllApartments() {
        List<Apartment> apartments = apartmentRepository.findAll();
        int total = apartments.size();
        return new ApartmentResponse(apartments, total);
    }

    @Override
    public Page<Apartment> getAllStaffs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return apartmentRepository.findAll(pageable);
    }

    @Override
    public Apartment getApartmentById(Long apartment_id) {
        Apartment apartment = apartmentRepository.findById(apartment_id).orElseThrow(
                () -> new RuntimeException("Not found apartment has id: " + apartment_id));
        if (apartment.getResidents() == null || apartment.getResidents().isEmpty()) {
            apartment.setApartmentStatus("TRỐNG"); // Cập nhật trạng thái thành "TRỐNG"
            apartmentRepository.save(apartment);
        }
        return apartment;
    }

    @Override
    public Apartment saveApartment(ApartmentRequest apartmentRequest) {
        Apartment apartment = new Apartment();
        apartment.setApartment_name(apartmentRequest.getApartment_name());
        apartment.setArea(apartmentRequest.getArea());
        apartment.setNumber_of_room(apartmentRequest.getNumber_of_room());
        apartment.setApartmentStatus(apartmentRequest.getApartmentStatus());
        return apartmentRepository.save(apartment);
    }

    @Override
    public Apartment updateApartmentById(Long apartment_id, ApartmentRequest apartmentRequest) {
        Apartment apartment = apartmentRepository.findById(apartment_id).orElseThrow(
                () -> new RuntimeException("Not found apartment has id: " + apartment_id));

        apartment.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        apartment.setApartmentStatus(apartmentRequest.getApartmentStatus());
        return apartmentRepository.save(apartment);
    }

    @Override
    public void deleteApartmentById(Long apartment_id) {
        apartmentRepository.deleteById(apartment_id);
    }
}

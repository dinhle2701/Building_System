package com.microservice.building_be.service.serviceimpl;

import com.microservice.building_be.dto.request.CardRequest;
import com.microservice.building_be.dto.request.ResidentRequest;
import com.microservice.building_be.dto.request.VehicleRequest;
import com.microservice.building_be.dto.response.FeedbackResponse;
import com.microservice.building_be.dto.response.ResidentResponse;
import com.microservice.building_be.dto.response.VehicleResponse;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.*;
import com.microservice.building_be.repository.*;
import com.microservice.building_be.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    private ResidentRepository residentRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ParkingRepository parkingRepository;

    @Override
    public Page<ResidentResponse> getAllResidents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return residentRepository.findAllResidentsWithApartmentName(pageable);
    }
//    @Override
//    public Page<ResidentResponse> getAllResidents(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size); // Tạo Pageable với số trang và kích thước trang
//
//        // Truy vấn với phân trang từ repository (sử dụng method với Pageable)
//        Page<Resident> residents = residentRepository.findAll(pageable);
//
//        // Chuyển đổi từ Feedback sang FeedbackResponse DTO
//        Page<ResidentResponse> residentResponses = residents.map(resident -> new ResidentResponse(
//                resident.getResident_id(),
//                resident.getResident_name(),
//                resident.getPhone_number(),
//                resident.getEmail(),
//                resident.getCccd(),
//                resident.getBirthday(),
//                resident.getSex(),
//                resident.getMove_in_date(),
//                resident.getApartment().getApartment_name()
//        ));
//
//        return residentResponses;
//    }

    @Override
    public Resident getResidentById(Long resident_id) {
        return residentRepository.findById(resident_id).orElseThrow(
                () -> new ResourceNotFoundException("Not found resident has id: " + resident_id)
        );
    }

    @Override
    public Resident createNewResident(ResidentRequest residentRequest) {
        Resident resident = new Resident();
        resident.setResident_name(residentRequest.getResident_name());
        boolean cccdExists = residentRepository.existsByCccd(residentRequest.getCccd());
        if (cccdExists) {
            throw new IllegalArgumentException("CCCD đã tồn tại. Không thể thêm cư dân mới với CCCD này.");
        }
        resident.setCccd(residentRequest.getCccd());
        resident.setEmail(residentRequest.getEmail());
        resident.setPhone_number(residentRequest.getPhone_number());
        resident.setBirthday(residentRequest.getBirthday());
        resident.setMove_in_date(null);
        resident.setMove_out_date(null);

        residentRepository.save(resident);
        return resident;
    }

    @Override
    public Resident createResident(ResidentRequest residentRequest, Long apartmentId) {
        // Tìm Apartment theo apartment_id
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment not found"));

        boolean cccdExists = residentRepository.existsByCccd(residentRequest.getCccd());
        if (cccdExists) {
            throw new IllegalArgumentException("CCCD đã tồn tại. Không thể thêm cư dân mới với CCCD này.");
        }

        // Tạo Resident từ ResidentRequest và gán cho Apartment
        Resident newResident = new Resident();
        apartment.setApartmentStatus("ĐANG SỬ DỤNG");
        apartment.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        newResident.setSex(residentRequest.getSex());
        newResident.setResident_name(residentRequest.getResident_name());
        newResident.setPhone_number(residentRequest.getPhone_number());
        newResident.setEmail(residentRequest.getEmail());
        newResident.setCccd(residentRequest.getCccd());
        newResident.setBirthday(residentRequest.getBirthday());
        newResident.setMove_in_date(new Timestamp(System.currentTimeMillis()));
        newResident.setApartment(apartment); // Gán Apartment cho Resident

        // Lưu Resident vào database
        return residentRepository.save(newResident);
    }

    @Override
    public Resident addResidentIntoApartment(Long residentId, Long apartmentId) {
        // Tìm cư dân theo ID
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException("Resident not found"));

        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment not found"));
        apartment.setApartmentStatus("ĐANG SỬ DỤNG");

        resident.setMove_in_date(new Timestamp(System.currentTimeMillis()));
//        // Kiểm tra xem cư dân có thuộc căn hộ chỉ định không
//        if (resident.getApartment() == null || !resident.getApartment().getApartment_id().equals(apartmentId)) {
//            throw new IllegalStateException("Resident is not associated with this apartment");
//        }

        // Thêm cư dân ra vào trong căn hộ
        resident.setApartment(apartment);

        // Cập nhật lại database
        return residentRepository.save(resident);
    }
    @Override
    public Resident removeResidentFromApartment(Long residentId, Long apartmentId) {
        // Tìm cư dân theo ID
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException("Resident not found"));

        resident.setMove_in_date(null);
        // Kiểm tra xem cư dân có thuộc căn hộ chỉ định không
        if (resident.getApartment() == null || !resident.getApartment().getApartment_id().equals(apartmentId)) {
            throw new IllegalStateException("Resident is not associated with this apartment");
        }

        // Gỡ cư dân ra khỏi căn hộ
        resident.setApartment(null);

        // Cập nhật lại database
        return residentRepository.save(resident);
    }


    @Override
    public Resident updateResidentById(Long resident_id, Resident updateResident) {
        Resident resident = residentRepository.findById(resident_id).orElseThrow(
                () -> new ResourceNotFoundException("Not found resident has id: " + resident_id)
        );

        resident.setResident_name(updateResident.getResident_name());
        resident.setEmail(updateResident.getEmail());
        resident.setPhone_number(updateResident.getPhone_number());
        resident.setBirthday(updateResident.getBirthday());

        return residentRepository.save(resident);
    }

    @Override
    public void deleteResidentById(Long resident_id) {
        Optional<Resident> resident = residentRepository.findById(resident_id);
        if (resident.isPresent()) {
            residentRepository.deleteById(resident_id);
        } else {
            throw new ResourceNotFoundException("Resident với id " + resident_id + " không tồn tại!");
        }
    }

    @Transactional
    public Vehicle addVehicleToResident(Long residentId, Long parking_id, VehicleRequest vehicleRequest) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException("Resident not found"));
        Parking parking = parkingRepository.findById(parking_id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bãi đỗ xe có id: " + parking_id));

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle_name(vehicleRequest.getVehicle_name());
        vehicle.setLicense_plate(vehicleRequest.getLicense_plate());
        vehicle.setVehicle_type(vehicleRequest.getVehicle_type());
        vehicle.setColor(vehicleRequest.getColor());
        vehicle.setResident(resident); // Link vehicle to resident
        vehicle.setParking(parking);
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void deleteVehicle(Long vehicle_id) {
        vehicleRepository.deleteById(vehicle_id);
    }

    @Override
    public Page<VehicleResponse> getAllVehicles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return vehicleRepository.findAllVehiclesWithResidentAndApartment(pageable);
    }
}

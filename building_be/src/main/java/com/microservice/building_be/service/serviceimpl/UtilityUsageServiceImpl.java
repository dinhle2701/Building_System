package com.microservice.building_be.service.serviceimpl;

import com.microservice.building_be.dto.request.EmailRequest;
import com.microservice.building_be.dto.request.UtilityRequest;
import com.microservice.building_be.dto.response.MonthlyUsageStats;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.UtilityUsage;
import com.microservice.building_be.repository.ApartmentRepository;
import com.microservice.building_be.repository.UtilityUsageRepository;
import com.microservice.building_be.service.UtilityUsageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UtilityUsageServiceImpl implements UtilityUsageService {
    @Autowired
    private UtilityUsageRepository utilityUsageRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
//    @Autowired
//    private EmailService emailService;
//    @Autowired
//    private JavaMailSender javaMailSender;


    @Override
    public List<UtilityUsage> getAllUtility() {
        return utilityUsageRepository.findAll();
    }

    @Override
    public UtilityUsage createUtilityUsage(UtilityRequest request) {
        Apartment apartment = apartmentRepository.findById(request.getApartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Apartment not found"));

        // Lấy bản ghi mới nhất (nếu có)
        List<UtilityUsage> usages = utilityUsageRepository.findByApartmentId((long) apartment.getApartment_id());
        UtilityUsage lastUsage = usages.isEmpty() ? null : usages.get(0);

        UtilityUsage newUsage = new UtilityUsage();
        newUsage.setCreateDate(new Timestamp(System.currentTimeMillis()));
        newUsage.setUpdateDate(new Timestamp(System.currentTimeMillis()));
        newUsage.setApartment(apartment);

        // Lấy số điện và nước cũ
        long electricityOld = lastUsage != null ? lastUsage.getElectricity_new() : 0;
        long waterOld = lastUsage != null ? lastUsage.getWater_new() : 0;

        // Kiểm tra nếu số mới nhỏ hơn số cũ
        if (request.getElectricity_new() < electricityOld) {
            throw new IllegalArgumentException("Số điện mới không thể nhỏ hơn số cũ.");
        }
        if (request.getWater_new() < waterOld) {
            throw new IllegalArgumentException("Số nước mới không thể nhỏ hơn số cũ.");
        }

        // Cập nhật giá trị cho bản ghi mới
        newUsage.setElectricity_old(electricityOld);
        newUsage.setElectricity_new(request.getElectricity_new());
        newUsage.setElectricityTotalUsage(request.getElectricity_new() - electricityOld);

        newUsage.setWater_old(waterOld);
        newUsage.setWater_new(request.getWater_new());
        newUsage.setWaterTotalUsage(request.getWater_new() - waterOld);

        // Tính toán và tạo dữ liệu điện/nước
        generateElectronicAndWaterUsage(newUsage);

        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setSubject(String.valueOf(apartment));
        emailRequest.setText(
                String.valueOf(newUsage.getElectricity_new())
        );
        sendInvoiceEmail(apartment, newUsage);

        return utilityUsageRepository.save(newUsage);
    }

    @Override
    public Long calculateTotalCostForPeriod(Timestamp create_date, Timestamp update_date) {
        List<UtilityUsage> utilityUsages = utilityUsageRepository.findByCreateDateBetween(create_date, update_date);

        return utilityUsages.stream()
                .mapToLong(UtilityUsage::getTotalPrice)
                .sum();
    }

    @Override
    public void generateElectronicAndWaterUsage(UtilityUsage utilityUsage) {
        // generate electric usage
        Long dataElect = (utilityUsage.getElectricity_new()) - (utilityUsage.getElectricity_old());
        Long electrictPrice;
        if (dataElect > 0 && dataElect <= 50) {
            electrictPrice = dataElect * 1893;
            System.out.println(electrictPrice);
            utilityUsage.setElectricTotalPrice(electrictPrice);
        } else if (dataElect > 50 && dataElect <= 100) {
            electrictPrice = 50 * 1893 + ((dataElect - 50) * 1956);
            System.out.println(electrictPrice);
            utilityUsage.setElectricTotalPrice(electrictPrice);
        } else if (dataElect > 100 && dataElect <= 200) {
            electrictPrice = 50 * 1893 + (50 * 1956) + ((dataElect - 100) * 2271);
            System.out.println(electrictPrice);
            utilityUsage.setElectricTotalPrice(electrictPrice);
        } else if (dataElect > 200 && dataElect <= 300) {
            electrictPrice = (50 * 1893) + (50 * 1956) + (100 * 2271) + ((dataElect - 200) * 2860);
            System.out.println(electrictPrice);
            utilityUsage.setElectricTotalPrice(electrictPrice);
        } else if (dataElect > 300 && dataElect <= 400) {
            electrictPrice = (50 * 1893) + (50 * 1956) + (100 * 2271) + (100 * 2860) + ((dataElect - 300) * 3197);
            System.out.println(electrictPrice);
            utilityUsage.setElectricTotalPrice(electrictPrice);
        } else {
            electrictPrice = (50 * 1893) + (50 * 1956) + (100 * 2271) + (100 * 2860) + (100 * 3197) + ((dataElect - 400) * 3302);
            System.out.println(electrictPrice);
            utilityUsage.setElectricTotalPrice(electrictPrice);
        }


        // generate water usage
        Long dataWater = (utilityUsage.getWater_new()) - (utilityUsage.getWater_old());
        Long waterPrice;

        if (dataWater <= 10) {
            waterPrice = dataWater * 4500;
            System.out.println(waterPrice);
            utilityUsage.setWaterTotal_price(waterPrice);
        } else if (dataWater > 10 && dataWater <= 20) {
            waterPrice = (10 * 4500) + ((dataWater - 10) * 5600);
            System.out.println(waterPrice);
            utilityUsage.setWaterTotal_price(waterPrice);
        } else if (dataWater > 20 && dataWater <= 30) {
            waterPrice = (dataWater * 4500) + (10 * 5600) + ((dataWater - 20) * 6700);
            System.out.println(waterPrice);
            utilityUsage.setWaterTotal_price(waterPrice);
        } else {
            waterPrice = (10 * 4500) + (10 * 5600) + (10 * 6700) + ((dataWater - 30) * 9200);
            System.out.println(waterPrice);
            utilityUsage.setWaterTotal_price(waterPrice);
        }

        // generate total usage

        Long totalUsage = dataWater + dataElect;
        Long totalPrice = electrictPrice + waterPrice + utilityUsage.getHygiene_price() + utilityUsage.getManage_price();
        utilityUsage.setTotalPrice(totalPrice);
        utilityUsage.setTotalUsage(totalUsage);
    }

    private void sendInvoiceEmail(Apartment apartment, UtilityUsage usage) {
        // Tạo tiêu đề email
        String emailSubject = "Hóa đơn sử dụng điện nước cho căn hộ " + apartment.getApartment_name();

        // Tạo nội dung email
        String emailBody = String.format(
                "Xin chào,\n\nHóa đơn tháng này của bạn:\n" +
                        "- Số điện mới: %d kWh (sử dụng: %d kWh)\n" +
                        "- Số nước mới: %d m³ (sử dụng: %d m³)\n" +
                        "- Tổng tiền điện: %d VNĐ\n" +
                        "- Tổng tiền nước: %d VNĐ\n\nCảm ơn bạn đã sử dụng dịch vụ!",
                usage.getElectricity_new(),
                usage.getElectricityTotalUsage(),
                usage.getWater_new(),
                usage.getWaterTotalUsage(),
                usage.getElectricTotalPrice(),
                usage.getWaterTotal_price()
        );

//        sendEmailToUsers(emailBody);
    }

//    public EmailRequest sendEmailToUsers(EmailRequest emailRequest) {
//        // Lấy danh sách email từ đối tượng emailRequest
//        List<String> emailAddresses = emailRequest.getTo();
//        String subject = emailRequest.getSubject();
//        String text = emailRequest.getText();
//
//        // Duyệt qua danh sách email và gửi từng email
//        for (String email : emailAddresses) {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(email);
//            message.setSubject(subject);
//            message.setText(text);
//            javaMailSender.send(message);
//        }
//        return emailRequest;
//    }


    public Double getTotalPrice() {
        Double totalPrice = utilityUsageRepository.calculateTotalPrice();
        return totalPrice != null ? totalPrice : 0.0; // Nếu không có bản ghi nào, trả về 0
    }

    @Override
    public UtilityUsage getLatestUtilityUsage(Long apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Apartment not found with ID: " + apartmentId));

        return utilityUsageRepository.findTopByApartmentOrderByCreateDateDesc(apartment)
                .orElseThrow(() -> new ResourceNotFoundException("No utility usage found for Apartment ID: " + apartmentId));
    }

//    public UtilityUsage getLatestUsage(Long apartmentId) {
//        return utilityUsageRepository.findTopByApartmentIdOrderByUsageDateDesc(apartmentId)
//                .orElseThrow(() -> new ResourceNotFoundException("No usage data found for apartment with id " + apartmentId));
//    }
}

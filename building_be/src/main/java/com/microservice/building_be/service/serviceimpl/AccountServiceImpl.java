package com.microservice.building_be.service.serviceimpl;


import com.microservice.building_be.dto.request.AccountRequest;
import com.microservice.building_be.dto.response.AccountResponse;
import com.microservice.building_be.enums.Role;
import com.microservice.building_be.enums.Status;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Account;
import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.Staff;
import com.microservice.building_be.repository.AccountRepository;
import com.microservice.building_be.repository.ApartmentRepository;
import com.microservice.building_be.repository.StaffRepository;
import com.microservice.building_be.service.AccountService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AccountResponse> getAccounts() {
        List<Account> account = accountRepository.findAll();


        List<AccountResponse> responses = new ArrayList<>();
        for (int i = 0; i < account.size(); i++) {
            AccountResponse accountResponse = modelMapper.map(account.get(i), AccountResponse.class);
            responses.add(accountResponse);
        }

        System.out.println(responses);
        return responses;
    }

    @Override
    public Account getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found account has id: " + id)
        );
        // return modelMapper.map(account, AccountResponse.class);
        return account;
    }

    @Override
    @Transactional
    public AccountResponse createNewAccount(AccountRequest accountRequest) {
        Account account = new Account();
        account.setEmail(accountRequest.getEmail());
        account.setCreate_date(Timestamp.valueOf(LocalDateTime.now()));
        account.setUpdate_date(null);
        account.setRole(accountRequest.getRole());
        account.setStatus(Status.ENABLED.name());
        account.setPassword(bCryptPasswordEncoder.encode(accountRequest.getPassword()));

        accountRepository.save(account);

        AccountResponse response = modelMapper.map(account, AccountResponse.class);

        return response;
    }

    private LocalDateTime getCurrentFormattedDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm dd/MM/yyyy");
        return LocalDateTime.now();
    }

    @Override
    public AccountResponse updateAccount(Long id, AccountRequest request) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found account has id: " + id)
        );

        account.setEmail(request.getEmail());
        account.setUpdate_date(Timestamp.valueOf(LocalDateTime.now()));
        accountRepository.save(account);

        AccountResponse response = modelMapper.map(account, AccountResponse.class);

        return response;
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy account có id: " + id));

        // So sánh vai trò của account bằng phương thức equals()
        if (!account.getRole().equals("ADMIN")) {
            accountRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Không thể xóa tài khoản ADMIN");
        }
    }


    @Override
    public AccountResponse lockAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found account has id: " + id)
        );
        account.setStatus(Status.DISABLED.name());
        account.setDelete_date(Timestamp.valueOf(LocalDateTime.now()));
        accountRepository.save(account);

        return modelMapper.map(account, AccountResponse.class);
    }

    @Override
    public Account assignAccountToApartment(Long accountId, Long apartmentId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));

        account.setApartment(apartment); // Gắn tài khoản vào căn hộ
        Account updatedAccount = accountRepository.save(account);
        return updatedAccount;
    }

    @Override
    public Account resignAccountToApartment(Long accountId, Long apartmentId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));

        apartmentRepository.deleteById(accountId);
        account.setApartment(null); // Gắn tài khoản vào căn hộ
        Account updatedAccount = accountRepository.save(account);
        return updatedAccount;
    }

    @Override
    public Account assignAccountToStaff(Long accountId, Long staffId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        account.setStaff(staff); // Gắn tài khoản vào căn hộ
        return accountRepository.save(account);
    }

    @Override
    public Staff getStaffByAccountId(Long accountId) {
        // Lấy thông tin Account từ ID
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Kiểm tra xem tài khoản đã được liên kết với nhân viên chưa
        Staff staff = account.getStaff();
        if (staff == null) {
            throw new RuntimeException("No staff assigned to this account");
        }

        return staff;
    }


    @Override
    public Apartment getApartmentByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));
        return account.getApartment();
    }

//    @Override
//    public void logAllAccounts() {
//        List<Account> account = accountRepository.findAll();
//        System.out.println("Number of accounts: " + account.size());
//    }

}
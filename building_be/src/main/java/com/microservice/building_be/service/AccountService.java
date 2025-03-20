package com.microservice.building_be.service;


import com.microservice.building_be.dto.request.AccountRequest;
import com.microservice.building_be.dto.response.AccountResponse;
import com.microservice.building_be.model.Account;
import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.Staff;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {


    List<AccountResponse> getAccounts();

    Account getAccountById(Long id);

    AccountResponse createNewAccount(AccountRequest account);

    AccountResponse updateAccount(Long id, AccountRequest request);

    void deleteAccount(Long id);

    AccountResponse lockAccount(Long id);

    Account assignAccountToApartment(Long accountId, Long apartmentId);

    Account resignAccountToApartment(Long accountId, Long apartmentId);

    Account assignAccountToStaff(Long accountId, Long staffId);

    Staff getStaffByAccountId(Long accountId);

    Apartment getApartmentByAccountId(Long accountId);

//    void logAllAccounts();
}

package com.microservice.building_be.controller;


import com.microservice.building_be.dto.request.AccountRequest;
import com.microservice.building_be.dto.response.AccountResponse;
import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Account;
import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.Staff;
import com.microservice.building_be.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    // api get all
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAccounts(){
        List<AccountResponse> list = accountService.getAccounts();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    // api get by id
    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable("id") Long id){
        Account response = accountService.getAccountById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // api get apartment by account id
    @GetMapping("/{id}/apartment")
    public ResponseEntity<Apartment> getApartmentByAccount(@PathVariable("id") Long accountId) {
        Apartment apartment = accountService.getApartmentByAccountId(accountId);
        return ResponseEntity.ok(apartment);
    }

    // api create new account
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountRequest accountRequest){
        AccountResponse newAccountResponse =accountService.createNewAccount(accountRequest);
        return new ResponseEntity<>(newAccountResponse, HttpStatus.CREATED);
    }

    // api update account with id
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable("id") Long id, @RequestBody AccountRequest request){
        AccountResponse response = accountService.updateAccount(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // api delete account out database
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable("id") Long id){
        accountService.deleteAccount(id);
        HttpStatus.OK.value();
    }

    // api lock account but not delete - change status
    @PutMapping("/lock/{id}")
    public ResponseEntity<AccountResponse> lockAccount(@PathVariable("id") Long id){
        AccountResponse response = accountService.lockAccount(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // create account for apartment
    @PostMapping("/{accountId}/assign-to-apartment/{apartmentId}")
    public ResponseEntity<Account> assignAccountToApartment(
            @PathVariable Long accountId,
            @PathVariable Long apartmentId) {
        try {
            Account accountReq = accountService.assignAccountToApartment(accountId, apartmentId);
            return new ResponseEntity<>(accountReq, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{accountId}/delete-from-apartment/{apartmentId}")
    public ResponseEntity<Account> deleteAccountToApartment(
            @PathVariable Long accountId,
            @PathVariable Long apartmentId) {
        try {
            Account accountReq = accountService.resignAccountToApartment(accountId, apartmentId);
            return new ResponseEntity<>(accountReq, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{accountId}/assign-to-staff/{staffId}")
    public ResponseEntity<Account> assignAccountToStaff(
            @PathVariable Long accountId,
            @PathVariable Long staffId) {
        try {
            Account accountReq = accountService.assignAccountToStaff(accountId, staffId);
            return new ResponseEntity<>(accountReq, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{accountId}/staff")
    public ResponseEntity<Staff> getStaffByAccountId(
            @PathVariable Long accountId) {
        try {
            Staff staff = accountService.getStaffByAccountId(accountId);
            return new ResponseEntity<>(staff, HttpStatus.OK);
        } catch (ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

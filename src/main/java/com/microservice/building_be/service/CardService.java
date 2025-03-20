package com.microservice.building_be.service;

import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CardService {
    Page<Card> getAllCards(int page, int size);

    Card addCardToResident(Long residentId);

    void deleteCardByResidentId(Long card_id);

    void generateCardCode(Card card);
}

package com.microservice.building_be.service.serviceimpl;

import com.microservice.building_be.exception.ResourceNotFoundException;
import com.microservice.building_be.model.Apartment;
import com.microservice.building_be.model.Card;
import com.microservice.building_be.model.Resident;
import com.microservice.building_be.repository.CardRepository;
import com.microservice.building_be.repository.ResidentRepository;
import com.microservice.building_be.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ResidentRepository residentRepository;

    @Override
    public Page<Card> getAllCards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createDate"));
        return cardRepository.findAll(pageable);
    }
    @Transactional
    public Card addCardToResident(Long residentId) {
        Resident resident = residentRepository.findById(residentId)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found"));

        Card card = new Card();
        card.setCard_status("ĐÃ KÍCH HOẠT");
        generateCardCode(card);
        card.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        card.setResident(resident); // Link vehicle to resident

        return cardRepository.save(card);
    }

    @Override
    public void deleteCardByResidentId(Long card_id) {
        Optional<Card> card = cardRepository.findById(card_id);
        if (card.isPresent()) {
            cardRepository.deleteById(card_id);
        } else {
            throw new ResourceNotFoundException("Card với id " + card_id + " không tồn tại!");
        }
    }

    @Override
    public void generateCardCode(Card card) {
        String prefix = "TCDM";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder suffix = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(characters.length());
            suffix.append(characters.charAt(randomIndex));
        }

        String cardCode = prefix + suffix;
        System.out.println(cardCode);
        card.setCardCode(cardCode);
    }
}

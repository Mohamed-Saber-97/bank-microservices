package org.example.cards.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.cards.constants.CardsConstants;
import org.example.cards.dto.CardsDto;
import org.example.cards.entity.Cards;
import org.example.cards.exception.CardAlreadyExistsException;
import org.example.cards.exception.ResourceNotFoundException;
import org.example.cards.mapper.CardsMapper;
import org.example.cards.repository.CardsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardsService {
    private final CardsRepository cardsRepository;
    private final CardsMapper cardsMapper;

    @Transactional
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if (optionalCards.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD.value());
        newCard.setTotalLimit(Integer.parseInt(CardsConstants.NEW_CARD_LIMIT.value()));
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(Integer.parseInt(CardsConstants.NEW_CARD_LIMIT.value()));
        return newCard;
    }

    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber)
                                     .orElseThrow(
                                             () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
                                                 );
        return cardsMapper.toDto(cards);
    }

    @Transactional
    public boolean updateCard(CardsDto cardsDto) {
        Cards existingCard =  cardsRepository.findByCardNumber(cardsDto.cardNumber())
                       .orElseThrow(
                               () -> new ResourceNotFoundException("Card",
                                                                   "CardNumber",
                                                                   cardsDto.cardNumber()));

        existingCard.setCardNumber(cardsDto.cardNumber());
        existingCard.setCardType(cardsDto.cardType());
        existingCard.setMobileNumber(cardsDto.mobileNumber());
        existingCard.setTotalLimit(cardsDto.totalLimit());
        existingCard.setAvailableAmount(cardsDto.availableAmount());
        existingCard.setAmountUsed(cardsDto.amountUsed());
        cardsRepository.save(existingCard);
        return true;
    }

    @Transactional
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber)
                                     .orElseThrow(
                                             () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
                                                 );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}

package kz.iitu.iitu.service;

import kz.iitu.iitu.dto.CreditCardDto;
import kz.iitu.iitu.entity.CreditCard;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.repository.CreditCardRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;

    public CreditCardService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    public CreditCard getCreditCard(Long id) {
        return creditCardRepository.findById(id).orElse(null);
    }

    @Transactional
    public CreditCard addCreditCard(CreditCard creditCard) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        creditCard.setUser(currentUser);

        return creditCardRepository.save(creditCard);
    }

    public CreditCardDto mapToDto(CreditCard creditCard) {
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setId(creditCard.getId());
        creditCardDto.setHolderName(creditCard.getHolderName());
        creditCardDto.setCardNumber(creditCard.getCardNumber());
        creditCardDto.setExpirationDate(creditCard.getExpirationDate());
        creditCardDto.setCvv(creditCard.getCvv());

        return creditCardDto;
    }

}

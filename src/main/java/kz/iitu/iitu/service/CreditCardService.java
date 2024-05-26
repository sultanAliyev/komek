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
    private final UserService userService;

    public CreditCardService(CreditCardRepository creditCardRepository,
                             UserService userService) {
        this.creditCardRepository = creditCardRepository;
        this.userService = userService;
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

    public CreditCard updateCreditCard(CreditCard creditCard) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        var creditCard1 = userService.getCreditCardsByUserId(currentUser).get(0);
        creditCard1.setHolderName(creditCard.getHolderName());
        creditCard1.setCardNumber(creditCard.getCardNumber());
        creditCard1.setExpirationDate(creditCard.getExpirationDate());
        creditCard1.setCvv(creditCard.getCvv());

        return creditCardRepository.save(creditCard1);
    }

    public void deleteCreditCard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        var creditCard = currentUser.getCreditCards();

        this.creditCardRepository.deleteAll(creditCard);
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

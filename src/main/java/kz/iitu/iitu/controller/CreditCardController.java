package kz.iitu.iitu.controller;

import jakarta.validation.Valid;
import kz.iitu.iitu.dto.CreditCardDto;
import kz.iitu.iitu.entity.CreditCard;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.service.CreditCardService;
import kz.iitu.iitu.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/credit-cards")
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final UserService userService;

    public CreditCardController(CreditCardService creditCardService,
                                UserService userService) {
        this.creditCardService = creditCardService;
        this.userService = userService;
    }

    @GetMapping
    public List<CreditCardDto> getAllCreditCards() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        return userService.getCreditCardsByUserId(currentUser).stream()
                .map(creditCardService::mapToDto)
                .toList();
    }

    @GetMapping("/{id}")
    public CreditCardDto getCreditCard(@PathVariable Long id) {
        return creditCardService.mapToDto(creditCardService.getCreditCard(id));
    }

    @PostMapping
    public CreditCardDto addCreditCard(@Valid @RequestBody CreditCard creditCard) {
        var creditCardObj = creditCardService.addCreditCard(creditCard);
        return creditCardService.mapToDto(creditCardObj);
    }

}

package kz.iitu.iitu.controller;

import kz.iitu.iitu.dto.TransactionDto;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.service.TransactionService;
import kz.iitu.iitu.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService,
                                 UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @GetMapping
    public List<TransactionDto> getAllTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        return userService.getTransactionsByUserId(currentUser).stream()
                .map(transactionService::mapToDto)
                .toList();
    }

}

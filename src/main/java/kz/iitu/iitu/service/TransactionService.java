package kz.iitu.iitu.service;

import kz.iitu.iitu.dto.TransactionDto;
import kz.iitu.iitu.entity.Transaction;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.repository.TransactionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public TransactionDto createTransaction(Transaction transaction) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        transaction.setUser(currentUser);
        transaction.setCreatedAt(LocalDateTime.now());

        return this.mapToDto(transactionRepository.save(transaction));
    }

    public TransactionDto mapToDto(Transaction transaction) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy");

        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setTitle(transaction.getTitle());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setType(transaction.getType().name());
        transactionDto.setCreatedAt(transaction.getCreatedAt().format(formatter));

        return transactionDto;
    }

}

package kz.iitu.iitu.service;

import kz.iitu.iitu.entity.CreditCard;
import kz.iitu.iitu.entity.Transaction;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<CreditCard> getCreditCardsByUserId(User user) {
        List<CreditCard> creditCards = user.getCreditCards();

        return creditCards;
    }

    public List<Transaction> getTransactionsByUserId(User user) {
        List<Transaction> transactions = user.getTransactions();

        return transactions;
    }
}

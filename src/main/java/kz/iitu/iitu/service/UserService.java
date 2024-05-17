package kz.iitu.iitu.service;

import kz.iitu.iitu.dto.UserDto;
import kz.iitu.iitu.entity.CreditCard;
import kz.iitu.iitu.entity.Transaction;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return this.mapUserToDto(user);
    }

    public UserDto updateUser(UserDto userDto, Long userId) {

        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Could not find user by id " + userId);
        }

        User user = userOpt.get();

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }

        userRepository.save(user);

        return this.mapUserToDto(user);
    }

    public List<CreditCard> getCreditCardsByUserId(User user) {
        List<CreditCard> creditCards = user.getCreditCards();

        return creditCards;
    }

    public List<Transaction> getTransactionsByUserId(User user) {
        List<Transaction> transactions = user.getTransactions();

        return transactions;
    }

    private UserDto mapUserToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPassword("********");
        dto.setAccountOpened(user.getAccountOpened());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return dto;
    }
}

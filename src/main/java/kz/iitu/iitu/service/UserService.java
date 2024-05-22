package kz.iitu.iitu.service;

import kz.iitu.iitu.dto.UserDto;
import kz.iitu.iitu.dto.UserPasswordDto;
import kz.iitu.iitu.entity.CreditCard;
import kz.iitu.iitu.entity.Transaction;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return this.mapUserToDto(user);
    }

    public UserDto updateUser(UserDto userDto, User user) {

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

    public UserDto updatePassword(UserPasswordDto passwordDto, User user) {

        var encodedPassword = user.getPassword();
        var rawPassword = passwordDto.getOldPassword();

        if (!encoder.matches(rawPassword, encodedPassword)) {
            throw new BadCredentialsException("Incorrect old password");
        }

        user.setPassword(encoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);

        return mapUserToDto(user);
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

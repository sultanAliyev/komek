package kz.iitu.iitu.service;

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

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User register(User user) {
        user.setAccountOpened(false);
        return userRepository.save(user);
    }

    public User login(User user) {
        Optional<User> userOpt = userRepository.findByEmail(user.getEmail());

        if (userOpt.isPresent()) {
           var currentUser = userOpt.get();
            if (currentUser.getPassword().equals(user.getPassword())) {
                return currentUser;
            }
        }

        return null;
    }
}

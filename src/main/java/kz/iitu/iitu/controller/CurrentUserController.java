package kz.iitu.iitu.controller;

import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController.
 *
 * @author USER
 * Date: 06.05.2024
 */
@RequestMapping("/api/users/current")
@RestController
public class CurrentUserController {

    private final UserService userService;

    public CurrentUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getCurrentUser() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       var currentUser = (User) authentication.getPrincipal();

       return userService.getUser(currentUser.getId());
    }

    @PutMapping
    public User updateCurrentUser() {
        return null;
    }

}

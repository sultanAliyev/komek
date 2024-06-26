package kz.iitu.iitu.controller;

import kz.iitu.iitu.dto.UserDto;
import kz.iitu.iitu.dto.UserPasswordDto;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@CrossOrigin
public class CurrentUserController {

    private final UserService userService;

    public CurrentUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        return userService.getUser(currentUser.getId());
    }

    @PutMapping
    public UserDto updateCurrentUser(@RequestBody UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        return userService.updateUser(userDto, currentUser);
    }

    @PutMapping("/password")
    public UserDto updateCurrentUserPassword(@RequestBody UserPasswordDto passwordDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (User) authentication.getPrincipal();

        return userService.updatePassword(passwordDto, currentUser);
    }

}

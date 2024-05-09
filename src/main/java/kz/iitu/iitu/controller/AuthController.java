package kz.iitu.iitu.controller;

import kz.iitu.iitu.dto.LoginResponse;
import kz.iitu.iitu.dto.LoginUserDto;
import kz.iitu.iitu.dto.RegisterUserDto;
import kz.iitu.iitu.entity.User;
import kz.iitu.iitu.exception.AccountAlreadyExistsException;
import kz.iitu.iitu.repository.UserRepository;
import kz.iitu.iitu.service.AuthenticationService;
import kz.iitu.iitu.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    public AuthController(
            JwtService jwtService,
            AuthenticationService authenticationService,
            UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {

        var user = userRepository.findByEmail(registerUserDto.getEmail());

        if (user.isPresent()) {
            throw new AccountAlreadyExistsException("Account Already Exists");
        }

        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}

package ir.netrira.core.business.tmp;

import ir.netrira.core.filter.utils.JwtUtils;
import ir.netrira.core.models.personnel.personnel.User;
import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.exception.DataResponse;
import ir.netrira.core.filter.dto.request.LoginRequest;
import ir.netrira.core.filter.dto.request.SignupRequest;
import ir.netrira.core.filter.repository.UserRepository;
import ir.netrira.core.filter.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/data")
public class CController2 {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/a")
    public ResponseEntity<?> aaaa(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(loginRequest.getUsername());
    }

    @PostMapping("/test")
    public ResponseEntity<?> authenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new DataResponse(ResponseConstant.SC_OK, ResponseConstantMessage.SC_OK,
                userDetails.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getRole()
        );


        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}

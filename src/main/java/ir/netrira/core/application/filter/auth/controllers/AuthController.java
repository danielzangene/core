package ir.netrira.core.application.filter.auth.controllers;

import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.exception.BusinessException;
import ir.netrira.core.application.dto.DataResponse;
import ir.netrira.core.application.filter.auth.dto.request.AuthRequest;
import ir.netrira.core.application.filter.auth.dto.request.LoginRequest;
import ir.netrira.core.application.filter.auth.dto.request.SignupRequest;
import ir.netrira.core.application.filter.auth.dto.response.CaptchaResponse;
import ir.netrira.core.application.filter.auth.repository.UserRepository;
import ir.netrira.core.application.filter.auth.dto.response.LoginResponse;
import ir.netrira.core.application.filter.auth.util.captcha.CaptchaUtils;
import ir.netrira.core.application.filter.auth.util.JwtUtils;
import ir.netrira.core.models.application.personnel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        validateCaptcha(loginRequest);
        return getAuthResponseToken(loginRequest);
    }

    private ResponseEntity<DataResponse> getAuthResponseToken(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken();
        return ResponseEntity.ok(DataResponse.SUCCESS_RESPONSE.setResultData(new LoginResponse(jwt)));
    }

    private void validateCaptcha(LoginRequest loginRequest) {
        CaptchaUtils.validateCaptcha(loginRequest.getCaptcha(), loginRequest.getCaptchaId());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BusinessException(ResponseConstant.USERNAME_EXIST, ResponseConstantMessage.USERNAME_EXIST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BusinessException(ResponseConstant.EMAIL_EXIST, ResponseConstantMessage.EMAIL_EXIST);
        }

        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getRole()
        );
        userRepository.save(user);

        return getAuthResponseToken(signUpRequest);
    }

    @PatchMapping("/captcha")
    public ResponseEntity<?> getCaptcha(HttpSession httpSession) {
        CaptchaResponse captchaResponse = CaptchaUtils.generateNumericalCaptchaResponse(100, 50);
        return ResponseEntity.ok(DataResponse.SUCCESS_RESPONSE.setResultData(captchaResponse));
    }
}

package ir.netrira.core.business.management.config;

import ir.netrira.core.application.dto.DataResponse;
import ir.netrira.core.application.filter.auth.repository.UserRepository;
import ir.netrira.core.application.filter.auth.util.JwtUtils;
import ir.netrira.core.business.management.config.dto.ConfigRequest;
import ir.netrira.core.business.management.config.dto.ConfigResponse;
import ir.netrira.core.business.utils.config.ConfigUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/config")
public class ConfigsController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PatchMapping("/all")
    public ResponseEntity<?> allRootConfigs() {
        return ResponseEntity.ok(DataResponse.SUCCESS_RESPONSE.setResultData(ConfigUtils.getAllMainNodes()));
    }

    @PatchMapping("/sub")
    public ResponseEntity<?> getSubTopicsById(@Valid @RequestBody ConfigRequest request) {
        ConfigResponse configResponse = new ConfigResponse(
                ConfigUtils.getConfig(request.getConfigId()),
                ConfigUtils.getConfigs(request.getConfigId())
        );
        return ResponseEntity.ok(DataResponse.SUCCESS_RESPONSE.setResultData(configResponse));
    }
}

package ir.netrira.core.application.utils.config.dto;

import ir.netrira.core.application.utils.config.ConfigModel;
import ir.netrira.core.application.utils.config.ConfigUtils;
import ir.netrira.core.application.dto.DataResponse;
import ir.netrira.core.application.filter.auth.repository.UserRepository;
import ir.netrira.core.application.filter.auth.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

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
        FormulaConfigDto formulaConfigDto = new FormulaConfigDto().setExpression("cos(a*x)").setArgument(Arrays.asList("a", "x"));
        ConfigModel formulas = new ConfigModel().setCode("FORMULAS");
        ConfigModel testFormula = ConfigUtils.saveConfig("testFormula", formulas.getId(), formulaConfigDto);
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

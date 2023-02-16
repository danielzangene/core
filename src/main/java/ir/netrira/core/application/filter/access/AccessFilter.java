package ir.netrira.core.application.filter.access;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.dto.Response;
import ir.netrira.core.application.exception.BusinessException;
import ir.netrira.core.application.filter.auth.dto.UserDetailsDto;
import ir.netrira.core.application.filter.auth.repository.UserRepository;
import ir.netrira.core.models.application.personnel.User;
import ir.netrira.core.models.application.systemaccess.GroupSystemAccess;
import ir.netrira.core.models.application.systemaccess.SystemAccess;
import ir.netrira.core.models.application.utils.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccessFilter extends OncePerRequestFilter {

    @Autowired
    SystemAccessRepository systemAccessRepository;

    @Autowired
    GroupSystemAccessRepository groupSystemAccessRepository;

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info(AccessFilter.class.toString());
        String method = request.getMethod();
        String requestURI = request.getRequestURI().replaceAll("/$", "");
        Optional<SystemAccess> systemAccess = systemAccessRepository.findByMethodAndRequestUri(method, requestURI);
        if (systemAccess.isPresent()) {
            if (hasPermission(systemAccess.get())) {
                filterChain.doFilter(request, response);
            } else {
                Response responseObject = new Response(ResponseConstant.SC_METHOD_NOT_ALLOWED, getAccessMessageHandler(systemAccess.get().getName()));
                new ObjectMapper().writeValue(response.getOutputStream(), responseObject);
            }
        } else {
            filterChain.doFilter(request, response);
        }

    }

    private Boolean isAnonymousUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return FilterCodes.ANONYMOUS_USER.equals(principal);
    }
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto userDetails = (UserDetailsDto) authentication.getPrincipal();
        return getUserByUsername(userDetails.getUsername());
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> {
            throw new BusinessException(ResponseConstant.USERNAME_NOT_EXIST, ResponseConstantMessage.USERNAME_NOT_EXIST);
        });
    }

    private Boolean hasPermission(SystemAccess access) {
        if (isAnonymousUser()) return Boolean.FALSE;
        String code = getCurrentUser().getRole().getCode();
        List<SystemAccess> systemAccesses = systemAccessRepository.userHasAccess(code, access.getId());
        return !systemAccesses.isEmpty();
    }

    private String getAccessMessageHandler(String useCaseName) {
        return String.format(ResponseConstantMessage.SC_METHOD_NOT_ALLOWED, useCaseName);
    }

}

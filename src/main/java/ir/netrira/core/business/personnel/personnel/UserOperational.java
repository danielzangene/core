package ir.netrira.core.business.personnel.personnel;

import ir.netrira.core.ResponseConstant;
import ir.netrira.core.ResponseConstantMessage;
import ir.netrira.core.application.exception.BusinessException;
import ir.netrira.core.application.filter.auth.dto.UserDetailsDto;
import ir.netrira.core.models.application.personnel.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserOperational implements UserService {


    @Autowired
    UserRepo userRepo;

    @Override
    public User getUserByUsername(String username){
        return userRepo.getByUsername(username).orElseThrow(() -> {
            throw new BusinessException(ResponseConstant.USERNAME_NOT_EXIST, ResponseConstantMessage.USERNAME_NOT_EXIST);
        });
    }

    @Override
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDto userDetails = (UserDetailsDto) authentication.getPrincipal();
        return getUserByUsername(userDetails.getUsername());
    }
}

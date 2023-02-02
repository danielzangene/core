package ir.netrira.core.business.personnel.personnel;

import ir.netrira.core.models.personnel.personnel.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByUsername(String username);

    User getCurrentUser();
}

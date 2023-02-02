package ir.netrira.core.business.personnel.personnel;

import ir.netrira.core.models.personnel.personnel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> getByUsername(String username);

}

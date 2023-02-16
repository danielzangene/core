package ir.netrira.core.application.filter.access;

import ir.netrira.core.models.application.systemaccess.GroupSystemAccess;
import ir.netrira.core.models.application.systemaccess.SystemAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupSystemAccessRepository extends JpaRepository<GroupSystemAccess, Long> {
    Optional<GroupSystemAccess> findByCode(String code);
}

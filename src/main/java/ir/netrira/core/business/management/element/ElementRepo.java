package ir.netrira.core.business.management.element;

import ir.netrira.core.models.management.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElementRepo extends JpaRepository<Element, Long> {

    Optional<Element> findByCode(String code);
}

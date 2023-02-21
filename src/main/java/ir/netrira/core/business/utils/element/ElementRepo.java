package ir.netrira.core.business.utils.element;

import ir.netrira.core.models.application.utils.Element;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementRepo extends CrudRepository<Element, String> {

    Optional<Element> findByCode(String code);
    List<Element> findByRootCode(String code);

    @Query("select rootCode from Element group by rootCode")
    List<String> findAllRootCodes();
}

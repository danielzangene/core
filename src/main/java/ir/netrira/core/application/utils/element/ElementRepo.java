package ir.netrira.core.application.utils.element;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementRepo extends CrudRepository<Element, String> {

    Optional<Element> findByCode(String code);
    List<Element> findByRoot_Code(String code);
    List<Element> findByRoot_Id(String id);

}

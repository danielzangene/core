package ir.netrira.core.business.utils.config;

import ir.netrira.core.models.application.utils.Config;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ConfigModelRepository extends CrudRepository<Config, Long> {

    Optional<Config> findByCode(String code);
    List<Config> findAllByRoot_Id(Long rootId);

}

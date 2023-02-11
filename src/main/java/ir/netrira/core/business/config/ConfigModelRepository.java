package ir.netrira.core.business.config;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConfigModelRepository extends CrudRepository<ConfigModel, String> {

    List<ConfigModel> findAllByTypeCode(String TypeCode);

}

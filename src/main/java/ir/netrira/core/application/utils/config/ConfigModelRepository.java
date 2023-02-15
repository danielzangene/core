package ir.netrira.core.application.utils.config;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConfigModelRepository extends CrudRepository<ConfigModel, String> {

    List<ConfigModel> findAllByParentId(String TypeCode);

}

package ir.netrira.core.application.cache;

import org.springframework.data.repository.CrudRepository;

public interface CacheModelRepository extends CrudRepository<CacheModel, String> {

    Iterable<CacheModel> findAllByType(String type);
}

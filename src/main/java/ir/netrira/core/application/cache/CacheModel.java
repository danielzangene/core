package ir.netrira.core.application.cache;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;

@RedisHash(value = "CacheModel")
@Data @NoArgsConstructor @Accessors(chain = true)
public class CacheModel implements Serializable {

    @Id
    private String id;
    @Indexed
    private String type;
    private String value;

}

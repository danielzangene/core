package ir.netrira.core.application.utils.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;

@RedisHash(value = "ConfigModel")
@Data @NoArgsConstructor @Accessors(chain = true)
public class ConfigModel implements Serializable {

    @Id
    private String id;
    @Indexed
    private String code;
    @Indexed
    private String parentId;
    private String value;

}

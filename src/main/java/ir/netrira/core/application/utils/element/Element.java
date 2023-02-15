package ir.netrira.core.application.utils.element;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@RedisHash(value = "Element")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Element {

    @Id @Indexed
    private String id;
    private String name;
    @Indexed
    private String code;
    @Indexed
    private Element root;
}

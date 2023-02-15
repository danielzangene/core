package ir.netrira.core.application.filter.auth.util.captcha;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.io.Serializable;

@RedisHash(value = "CaptchaModel", timeToLive = 200)
@Data @NoArgsConstructor @Accessors(chain = true)
public class CaptchaModel implements Serializable {

    @Id
    private String id;
    private String answer;

}

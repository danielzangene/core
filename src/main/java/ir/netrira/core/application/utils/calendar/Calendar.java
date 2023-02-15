package ir.netrira.core.application.utils.calendar;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "Calendar")
@Data @NoArgsConstructor @Accessors(chain = true)
public class Calendar {

    private String id;

    @Indexed private String date;
    @Indexed private Integer week;
    @Indexed private Integer year;
    @Indexed private Integer month;
    @Indexed private Integer day;

    private Boolean off;
    private Integer dayOfWeek;

}

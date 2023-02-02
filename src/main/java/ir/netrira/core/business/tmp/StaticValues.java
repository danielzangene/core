package ir.netrira.core.business.tmp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StaticValues {
    Map<Integer, List<String>> offOfficialDays = new HashMap<>() {{
        put(1401, Arrays.asList("1401/01/01", "1401/01/02", "1401/01/03", "1401/01/04", "1401/01/13", "1401/02/03", "1401/02/13", "1401/02/14", "1401/03/14", "1401/03/15", "1401/04/19", "1401/04/27", "1401/05/16", "1401/05/17", "1401/06/26", "1401/07/03", "1401/07/05", "1401/07/13", "1401/10/06", "1401/11/15", "1401/11/22", "1401/11/29", "1401/12/17", "1401/12/29"));
    }};

}

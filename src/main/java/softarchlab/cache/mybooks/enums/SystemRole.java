package softarchlab.cache.mybooks.enums;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

public enum SystemRole {

    ADMIN, COMMON;

    public static List<String> names() {
        return Arrays.stream(values()).map(SystemRole::name).collect(toList());
    }
}

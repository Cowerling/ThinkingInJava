package enumerated;

import java.util.*;
import java.text.*;

/**
 * Created by cowerling on 16-2-24.
 */
public enum ConstantSpecificMethod {
    DATE_TIME {
        String getInfo() {
            return DateFormat.getTimeInstance().format(new Date());
        }
    },
    CLASSPATH {
        String getInfo() {
            return System.getenv("CLASSPATH");
        }
    },
    VERSION {
        String getInfo() {
            return System.getProperty("java.version");
        }
    };

    abstract String getInfo();

    public static void main(String[] args) {
        for(ConstantSpecificMethod constantSpecificMethod : values())
            System.out.println(constantSpecificMethod.getInfo());
    }
}

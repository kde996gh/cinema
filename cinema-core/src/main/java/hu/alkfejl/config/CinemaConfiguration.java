package hu.alkfejl.config;

import java.io.IOException;
import java.util.Properties;

public class CinemaConfiguration {
    private static Properties props = new Properties();



    static{
        try {
            props.load(CinemaConfiguration.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            //TODO LOGGING
            e.printStackTrace();
        }
    }
    public static Properties getProps() {
        return props;
    }

    public static String getValue(String key){
        return props.getProperty(key);
    }
}

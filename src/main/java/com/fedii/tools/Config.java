package com.fedii.tools;

import com.fedii.entities.Credentials;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sfedii on 2/29/16.
 */
public class Config {


    static {
        POLLING_PERIOD = getLong("polling_period");
        LOCATION_TIMEOUT = getLong("location_timeout");
    }

    public static final long POLLING_PERIOD;
    public static final long LOCATION_TIMEOUT;
    private static Config instance;

    private Config() {
        properties = new Properties();
        try {
            InputStream stream = getClass().getClassLoader().getResourceAsStream("config.properties");
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Credentials getDefaultCredentials() {
        return new Credentials(getProperty("username"), getProperty("password"));
    }

    public static long getLong(String key) {
        return Long.parseLong(properties.getProperty(key));
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getRecruiterUrl() {
        return properties.getProperty("recruiterurl");
    }

    public static String getJobSeekerUrl() {
        return properties.getProperty("jobseekerurl");
    }

    static Properties properties = new Properties();

    static {
        instance = new Config();
    }
}

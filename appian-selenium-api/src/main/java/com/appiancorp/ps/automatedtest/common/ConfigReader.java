package com.appiancorp.ps.automatedtest.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//public class ConfigReader {
//
//    private static final String CONFIG_PATH = "src/test/resources/config/config.properties";
//    private static Properties props = new Properties();
//
//    static {
//        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
//            props.load(fis);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to load config.properties file from " + CONFIG_PATH, e);
//        }
//    }
//
//    public static String getProperty(String key) {
//        return props.getProperty(key);
//    }
//
//    public static String getEnvironment() {
//        return getProperty("environment").trim().toUpperCase();
//    }
//
//    public static String getBrowser() {
//        return getProperty("browser").trim().toUpperCase();
//    }
//
//    public static String getEnvironmentUrl() {
//        return getProperty(getEnvironment() + ".url");
//    }
//
//    public static String[] getUserCredentials(String role) {
//        String env = getEnvironment();
//        String key = env + ".user." + role;
//        String creds = getProperty(key);
//        if (creds == null)
//            throw new RuntimeException("No credentials found for key: " + key);
//        return creds.split("\\|");
//    }
//}


public class ConfigReader {
    private static final Properties props = new Properties();

//    static {
//        try {
//            FileInputStream fis = new FileInputStream("cucumber-for-appian/src/test/resources/config.properties");
//            props.load(fis);
//            fis.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to load config.properties file", e);
//        }
//    }

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException(" config.properties not found in classpath");
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file", e);
        }
    }

    public static String getEnvironment() {
        return props.getProperty("ENVIRONMENT", "TEST");
    }

    public static String getEnvironmentUrl() {
        String env = getEnvironment().toLowerCase();
        return props.getProperty(env + ".url");
    }

    public static String getBrowser() {
        return props.getProperty("BROWSER", "chrome");
    }

    public static String[] getUserCredentials(String role) {
        // Build the property key dynamically, e.g. TEST.user.introducer
        String key = getEnvironment() + ".user." + role;
        String creds = props.getProperty(key);

        if (creds == null || creds.trim().isEmpty()) {
            throw new RuntimeException("No credentials found for role: " + role + " (key: " + key + ")");
        }

        // Split credentials by "|" (username|password)
        String[] parts = creds.split("\\|", 2);
        if (parts.length < 2) {
            throw new RuntimeException("Invalid credentials format for role: " + role +
                    ". Expected format: username|password but got: " + creds);
        }

        // Trim whitespace and return as array
        return new String[]{ parts[0].trim(), parts[1].trim() };
    }

}

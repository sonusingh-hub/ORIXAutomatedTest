package com.appiancorp.ps.automatedtest.common;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class PropertiesUtilities {

    private static Properties properties = new Properties();

    public static void loadProperties() {
        if (Boolean.TRUE.equals(Settings.isJunitTest())) {
            loadPropertiesForTest();
            return;
        }

        InputStream inputStream = null;

        // If inside of jar
        try {
            List<String> files = IOUtils.readLines(PropertiesUtilities.class.getClassLoader()
                    .getResourceAsStream("configs/"), StandardCharsets.UTF_8);
            for (String file : files) {
                inputStream = PropertiesUtilities.class.getClassLoader().getResourceAsStream("configs/" + file);
                properties.load(inputStream);
            }
        } catch (Exception e) {
        }

        try {
            // If outside of jar
            if (inputStream == null) {
                File jarPath = new File(
                        PropertiesUtilities.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                String propertiesPath = jarPath.getParentFile().getAbsolutePath();
                File folder = new File(URLDecoder.decode(propertiesPath + "/../../configs", "UTF-8"));

                for (File file : folder.listFiles()) {
                    if (FilenameUtils.getExtension(file.getPath()).equals("properties")) {
                        inputStream = new FileInputStream(file.getAbsolutePath());
                        properties.load(inputStream);
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    private static void loadPropertiesForTest() {
        try {
            File configsFolder = new File("../shared/configs/");
            File[] configFiles = configsFolder.listFiles();
            for (File file : configFiles) {
                InputStream inputStream = new FileInputStream(file);
                properties.load(inputStream);
            }
        } catch (Exception e) {
        }
    }

    public static Properties getProps() {
        return properties;
    }

    public static void setProps(Properties props) {
        properties = props;
    }

}

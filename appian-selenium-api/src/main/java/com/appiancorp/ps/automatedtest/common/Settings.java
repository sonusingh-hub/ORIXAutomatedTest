package com.appiancorp.ps.automatedtest.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Settings {
    private static final Logger LOG = LogManager.getLogger(Settings.class);
    private static List<AppianVersion> appianVersions;
    private static List<Version> allVersions;
    private static Boolean isWindows;
    private static Boolean isMacOs;
    private static Boolean isLinux;
    private static Boolean isJunitTest; //if the execution is run from the junit test (jenkins or local)
    private static String junitTestName;

    private WebDriver driver;
    private String masterWindowHandle;
    private String url;
    private static Version version = new Version("7.10");
    private String locale;
    private int timeoutSeconds = 10;
    private int notPresentTimeoutSeconds = 1;
    private Date startDatetime = new Date();
    private int refreshTimes = 5;
    private int attemptTimes = 3;

    private String dateFormat = "M/d/yyyy";
    private String dateDisplayFormat = "MMM d, yyyy";
    private String timeFormat = "h:mm aa";
    private String timeDisplayFormat = "h:mm aa";
    private String datetimeFormat = "M/d/yyyy h:mm aa";
    private String datetimeDisplayFormat = "MMM d, yyyy, h:mm aa";

    private String dataSourceName = null;

    private String screenshotPath;
    private Boolean takeErrorScreenshots = false;
    private Boolean stopOnError = false;
    private int errorNumber = 1;

    private Map<String, String> testVariables = new HashMap<String, String>();

    @JsonCreator
    public Settings(
            @JsonProperty("appianVersions") List<AppianVersion> appianVersions,
            @JsonProperty("appianLocales") List<AppianLocale> appianLocales) {
        Settings.appianVersions = appianVersions;
        allVersions = new ArrayList<Version>();
        for (AppianVersion av : appianVersions) {
            allVersions.add(av.getVersion());
        }
        isWindows = SystemUtils.IS_OS_WINDOWS;
        isMacOs = SystemUtils.IS_OS_MAC;
        isLinux = SystemUtils.IS_OS_LINUX;
    }

    public static Boolean isWindows() {
        return isWindows;
    }

    public static Boolean isMacOs() {
        return isMacOs;
    }

    public static Boolean isLinux() {
        return isLinux;
    }

    public static Boolean isJunitTest() {
        return isJunitTest;
    }

    public static String testName() {
        return junitTestName;
    }

    public static void setIsJunitTest(boolean bool) {
        isJunitTest = bool;
    }

    public static void setJunitTestName(String name) {
        junitTestName = name;
    }

    public static List<AppianVersion> getAppianVersions() {
        return appianVersions;
    }

    public static String getByConstant(String constant) {
        Integer index = Version.getBestIndexFromList(getVersion(), allVersions);
        AppianVersion appianVersion = appianVersions.get(index);
        String byConstant;

        if (appianVersion.getVersion().match(getVersion()) <= 1) {
            throw new IllegalArgumentException(String.format("%s is not a recognized version", getVersion()));
        }

        while (index >= 0) {
            appianVersion = appianVersions.get(index);
            byConstant = appianVersion.getByConstant(constant);
          if (byConstant != null) {
            return byConstant;
          }
            index--;
        }

        return null;
    }

    public String getLabel(String label) {
        Integer index = Version.getBestIndexFromList(getVersion(), allVersions);
        AppianVersion appianVersion = appianVersions.get(index);
        String labelValue;

        if (appianVersion.getVersion().match(getVersion()) <= 1) {
            throw new IllegalArgumentException(String.format("%s is not a recognized version", getVersion()));
        }

        while (index >= 0) {
            labelValue = appianVersion.getLabel(this.getLocale(), label);
          if (labelValue != null) {
            return labelValue;
          }
            index--;
            appianVersion = appianVersions.get(index);
        }

        return null;
    }

    public String getDateFormat(String type) {
        Integer index = Version.getBestIndexFromList(getVersion(), allVersions);
        AppianVersion appianVersion = appianVersions.get(index);
        String format;

        if (appianVersion.getVersion().match(getVersion()) <= 1) {
            throw new IllegalArgumentException(String.format("%s is not a recognized version", getVersion()));
        }

        while (index >= 0) {
            format = appianVersion.getDateFormat(this.getLocale(), type);
          if (format != null) {
            return format;
          }
            index--;
            appianVersion = appianVersions.get(index);
        }

        return null;
    }

    public static Settings initialize() {
        InputStream in = null;
        try {
            in = Settings.class.getResource("/metadata.json").openStream();
            return new ObjectMapper().readValue(in, Settings.class);

        } catch (Exception e) {
            LOG.error("Error processing metadata.json resource", e);
        } finally {
            IOUtils.closeQuietly(in);
        }

        return null;
    }

    public void setDriver(WebDriver d) {
        driver = d;
        this.setMasterWindowHandle(d.getWindowHandle());
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setUrl(String u) {
        url = u.endsWith("/") ? u.substring(0, u.length() - 1) : u;
    }

    public String getUrl() {
        return url;
    }

    public static void setVersion(String v) {
        version = new Version(v);
    }

    public static Version getVersion() {
        return version;
    }

    public void setLocale(String l) {
        locale = l;

        setDateFormat(getDateFormat("dateFormat"));
        setDateDisplayFormat(getDateFormat("dateDisplayFormat"));
        setTimeFormat(getDateFormat("timeFormat"));
        setTimeDisplayFormat(getDateFormat("timeDisplayFormat"));
        setDatetimeFormat(getDateFormat("datetimeFormat"));
        setDatetimeDisplayFormat(getDateFormat("datetimeDisplayFormat"));
    }

    public void createLocale(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(path);
            AppianLocale l = objectMapper.readValue(file, AppianLocale.class);

            Integer index = Version.getBestIndexFromList(getVersion(), allVersions);
            AppianVersion appianVersion = appianVersions.get(index);

            appianVersion.addLocale(l);
            locale = l.getLocale();
        } catch (Exception e) {
            LOG.error("Error reading locale file", e);
        }


        setDateFormat(getDateFormat("dateFormat"));
        setDateDisplayFormat(getDateFormat("dateDisplayFormat"));
        setTimeFormat(getDateFormat("timeFormat"));
        setTimeDisplayFormat(getDateFormat("timeDisplayFormat"));
        setDatetimeFormat(getDateFormat("datetimeFormat"));
        setDatetimeDisplayFormat(getDateFormat("datetimeDisplayFormat"));
    }

    public String getLocale() {
        return locale;
    }

    public void setDateFormat(String df) {
        dateFormat = df;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateDisplayFormat(String df) {
        dateDisplayFormat = df;
    }

    public String getDateDisplayFormat() {
        return dateDisplayFormat;
    }

    public void setTimeFormat(String tf) {
        timeFormat = tf;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeDisplayFormat(String tf) {
        timeDisplayFormat = tf;
    }

    public String getTimeDisplayFormat() {
        return timeDisplayFormat;
    }

    public void setDatetimeFormat(String dtf) {
        datetimeFormat = dtf;
    }

    public String getDatetimeFormat() {
        return datetimeFormat;
    }

    public void setDatetimeDisplayFormat(String dtf) {
        datetimeDisplayFormat = dtf;
    }

    public String getDatetimeDisplayFormat() {
        return datetimeDisplayFormat;
    }

    public void setTimeoutSeconds(int t) {
        timeoutSeconds = t;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setNotPresentTimeoutSeconds(int t) {
        notPresentTimeoutSeconds = t;
    }

    public int getNotPresentTimeoutSeconds() {
        return notPresentTimeoutSeconds;
    }

    public void setStartDatetime(Date s) {
        startDatetime = s;
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setMasterWindowHandle(String w) {
        masterWindowHandle = w;
    }

    public String getMasterWindowHandle() {
        return masterWindowHandle;
    }

    public void setDataSourceName(String ds) {
        dataSourceName = ds;
    }

    public String getDataSourceName() {
        return this.dataSourceName;
    }

    public void setAttemptTimes(Integer at) {
        attemptTimes = at;
    }

    public Integer getAttemptTimes() {
        return this.attemptTimes;
    }

    public void setRefreshTimes(Integer rt) {
        refreshTimes = rt;
    }

    public Integer getRefreshTimes() {
        return this.refreshTimes;
    }

//    public void setScreenshotPath(String sp) {
//        screenshotPath = sp;
//    }

    public void setScreenshotPath(String path) {

        this.screenshotPath = path;

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public String getScreenshotPath() {
        return this.screenshotPath;
    }

    public void setTakeErrorScreenshots(Boolean es) {
        takeErrorScreenshots = es;
    }

    public Boolean isTakeErrorScreenshots() {
        return this.takeErrorScreenshots;
    }

    public void setStopOnError(Boolean es) {
        stopOnError = es;
    }

    public Boolean isStopOnError() {
        return this.stopOnError;
    }

    public void setErrorNumber(int e) {
        errorNumber = e;
    }

    public int getErrorNumber() {
        return this.errorNumber;
    }

    public void setTestVariableWith(String key, String val) {
        testVariables.put(key, val);
    }

    public String getTestVariable(String variable) {
        if (variable.contains(".")) {
            String variableKey = StringUtils.substringBefore(variable, ".");
            String variableName = StringUtils.substringAfter(variable, ".");
            LOG.debug("Variable Key: " + variableKey + " and Variable Name: " + variableName);
            Object result = JsonPath.read(testVariables.get(variableKey), "$." + variableName);

            // If the result returns JSON, then stringify
            if (result instanceof LinkedHashMap) {
                Gson gson = new Gson();
                String json = gson.toJson(result, LinkedHashMap.class);

                return json;
            } else {
                return result.toString();
            }
        } else {
            return testVariables.get(variable);
        }
    }
}

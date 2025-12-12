package com.appiancorp.ps.automatedtest.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AppianVersion {
    private Version version;
    private List<AppianLocale> appianLocales;
    private List<LabelValue> byConstants;

    @JsonCreator
    public AppianVersion(
            @JsonProperty("version") Version version,
            @JsonProperty("locales") List<AppianLocale> appianLocales,
            @JsonProperty("byConstants") List<LabelValue> byConstants) {
        this.version = version;
        this.appianLocales = appianLocales;
        this.byConstants = byConstants;
    }

    public Version getVersion() {
        return this.version;
    }

    public List<AppianLocale> getAppianLocales() {
        return appianLocales;
    }

    public void addLocale(AppianLocale l) {
        this.appianLocales.add(l);
    }

    public String getByConstant(String name) {
        for (LabelValue by : this.byConstants) {
            if (by.getLabel().equals(name)) {
                return by.getValue();
            }
        }

        return null;
    }

    public String getLabel(String locale, String name) {
        for (AppianLocale al : appianLocales) {
            if (al.getLocale().equals(locale)) {
                return al.getLabel(name);
            }
        }

        return null;
    }

    public String getDateFormat(String locale, String type) {
        for (AppianLocale al : appianLocales) {
            if (al.getLocale().equals(locale)) {
                return al.getDateFormat(type);
            }
        }

        return null;
    }
}

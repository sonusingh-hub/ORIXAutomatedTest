package com.appiancorp.ps.automatedtest.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AppianLocale {
    private String locale;
    private List<LabelValue> dateFormats;
    private List<LabelValue> labels;

    @JsonCreator
    public AppianLocale(
            @JsonProperty("locale") String locale,
            @JsonProperty("dateFormats") List<LabelValue> dateFormats,
            @JsonProperty("labels") List<LabelValue> labels) {
        this.locale = locale;
        this.dateFormats = dateFormats;
        this.labels = labels;
    }

    public String getLocale() {
        return this.locale;
    }

    public String getLabel(String name) {
        for (LabelValue by : this.labels) {
            if (by.getLabel().equals(name)) {
                return by.getValue();
            }
        }

        return null;
    }

    public String getDateFormat(String type) {
        for (LabelValue by : this.dateFormats) {
            if (by.getLabel().equals(type)) {
                return by.getValue();
            }
        }

        return null;
    }
}

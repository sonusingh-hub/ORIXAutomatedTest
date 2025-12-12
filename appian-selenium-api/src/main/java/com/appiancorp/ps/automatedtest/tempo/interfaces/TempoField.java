package com.appiancorp.ps.automatedtest.tempo.interfaces;

import com.appiancorp.ps.automatedtest.common.Settings;
import org.openqa.selenium.WebElement;

public final class TempoField extends AbstractTempoField {

    public static TempoField getInstance(Settings settings) {
        return new TempoField(settings);
    }

    private TempoField(Settings settings) {
        super(settings);
    }

    @Override
    public void populate(WebElement fieldLayout, String... params) throws Exception {

    }

    @Override
    public boolean contains(WebElement fieldLayout, String... params) throws Exception {
        return false;
    }

    @Override
    public boolean isNotBlank(WebElement fieldLayout) {
        return false;
    }

    @Override
    public String capture(WebElement fieldLayout, String... params) {
        return null;
    }

    @Override
    public void clear(WebElement fieldLayout, String... params) {
    }
}

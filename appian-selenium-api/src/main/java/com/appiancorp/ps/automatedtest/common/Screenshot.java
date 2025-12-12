package com.appiancorp.ps.automatedtest.common;

import com.appiancorp.ps.automatedtest.properties.Captureable;
import com.google.common.base.Throwables;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public final class Screenshot extends AppianObject implements Captureable {

    private static final Logger LOG = LogManager.getLogger(AppianObject.class);

    public static Screenshot getInstance(Settings settings) {
        return new Screenshot(settings);
    }

    private Screenshot(Settings settings) {
        super(settings);
    }

    @Override
    public String capture(String... params) {
        String fileName = params[0];

        File srcFile = ((TakesScreenshot) settings.getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(settings.getScreenshotPath(), fileName + ".png"));
            return settings.getScreenshotPath() + fileName + ".png";
        } catch (IOException e) {
            LOG.error(Throwables.getStackTraceAsString(e));
            throw new RuntimeException(e.getMessage());
        }
    }
}

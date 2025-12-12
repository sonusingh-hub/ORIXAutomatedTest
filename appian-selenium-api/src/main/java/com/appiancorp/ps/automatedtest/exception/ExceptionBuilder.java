package com.appiancorp.ps.automatedtest.exception;

import com.appiancorp.ps.automatedtest.common.Screenshot;
import com.appiancorp.ps.automatedtest.common.Settings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ExceptionBuilder {
    private static final Logger LOG = LogManager.getLogger(ExceptionBuilder.class);

    public static RuntimeException build(Exception e, Settings s, String... vals) {
        LOG.error(String.join(" - ", vals), e);

        try {
            if (s.isTakeErrorScreenshots()) {
                int errorNumber = s.getErrorNumber();
                if (Settings.isJunitTest()) {
                    Screenshot.getInstance(s).capture(String.format("%s - %3d", Settings.testName(), errorNumber));
                } else {
                    Screenshot.getInstance(s).capture(String.format("%3d", errorNumber));
                }
                s.setErrorNumber(errorNumber + 1);
            }
        } catch (Exception exception) {
            LOG.error("Screenshot error", e);
        }

        switch (e.getClass().getCanonicalName()) {
            case "org.openqa.selenium.NoSuchElementException":
                if (s.isStopOnError()) {
                    return new ObjectNotFoundStopTestException(vals);
                } else {
                    return new ObjectNotFoundTestException(vals);
                }

            case "org.openqa.selenium.TimeoutException":
                if (s.isStopOnError()) {
                    return new TimeoutStopTestException(vals);
                } else {
                    return new TimeoutTestException(vals);
                }

            case "org.openqa.selenium.StaleElementReferenceException":
                if (s.isStopOnError()) {
                    return new StaleElementStopTestException(vals);
                } else {
                    return new StaleElementTestException(vals);
                }

            case "java.lang.IllegalArgumentException":
                if (s.isStopOnError()) {
                    return new IllegalArgumentStopTestException(vals);
                } else {
                    return new IllegalArgumentTestException(vals);
                }

            case "org.openqa.selenium.remote.UnreachableBrowserException":
                if (s.isStopOnError()) {
                    return new UnreachableBrowserStopTestException(vals);
                } else {
                    return new UnreachableBrowserTestException(vals);
                }

            case "com.appiancorp.ps.automatedtest.exception.WaitForWorkingTestException":
                if (s.isStopOnError()) {
                    return new WaitForWorkingStopTestException(vals);
                } else {
                    return new WaitForWorkingTestException(vals);
                }

            case "com.appiancorp.ps.automatedtest.exception.WaitForProgressBarTestException":
                if (s.isStopOnError()) {
                    return new WaitForProgressBarStopTestException(vals);
                } else {
                    return new WaitForProgressBarTestException(vals);
                }

            default:
                if (s.isStopOnError()) {
                    return new GenericStopTestException(vals);
                } else {
                    e.printStackTrace();
                    return new GenericTestException(vals);
                }
        }
    }
}

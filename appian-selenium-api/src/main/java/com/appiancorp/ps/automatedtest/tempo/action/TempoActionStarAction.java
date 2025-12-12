package com.appiancorp.ps.automatedtest.tempo.action;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Clickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class TempoActionStarAction extends TempoAction implements Clickable {

    private static final Logger LOG = LogManager.getLogger(TempoActionStarAction.class);
    private static final String XPATH_ABSOLUTE_ACTION_STAR = XPATH_ABSOLUTE_ACTION_LINK +
            Settings.getByConstant("xpathConcatActionStar");

    public static TempoActionStarAction getInstance(Settings settings) {
        return new TempoActionStarAction(settings);
    }

    private TempoActionStarAction(Settings settings) {
        super(settings);
    }

    @Override
    public String getXpath(String... params) {
        String actionName = getParam(0, params);

        return xpathFormat(XPATH_ABSOLUTE_ACTION_STAR, actionName);
    }

    @Override
    public void click(String... params) {
        String actionName = getParam(0, params);

        if (LOG.isDebugEnabled()) {
            LOG.debug("STAR ACTION [" + actionName + "]");
        }

        WebElement star = getElement(params);
        clickElement(star);
    }

    public WebElement getElement(String... params) {
        return settings.getDriver().findElement(By.xpath(getXpath(params)));
    }
}

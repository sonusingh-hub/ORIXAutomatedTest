package com.appiancorp.ps.automatedtest.site;

import com.appiancorp.ps.automatedtest.common.AppianObject;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.properties.Navigateable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Site extends AppianObject implements Navigateable {

    private static final Logger LOG = LogManager.getLogger(Site.class);

    public static Site getInstance(Settings settings) {
        return new Site(settings);
    }

    private Site(Settings settings) {
        super(settings);
    }

    @Override
    public void navigateTo(String... params) {
        String siteUrl = params[0];

        if (LOG.isDebugEnabled()) {
            LOG.debug("NAVIGATE TO SITE [" + siteUrl + "]");
        }

        String url = settings.getUrl() + "/sites/" + siteUrl;
        settings.getDriver().get(url);
    }
}

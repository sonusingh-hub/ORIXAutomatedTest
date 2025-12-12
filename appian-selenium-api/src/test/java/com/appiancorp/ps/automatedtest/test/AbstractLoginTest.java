package com.appiancorp.ps.automatedtest.test;

import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.PropertiesUtilities;
import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.fixture.TempoFixture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.RegisterExtension;

public abstract class AbstractLoginTest<T extends TempoFixture> extends AbstractTest {

    @SuppressWarnings("checkstyle:visibilityModifier")
    @RegisterExtension
    RecordingDownloadExtension downloader = new RecordingDownloadExtension();

    private static final Logger LOG = LogManager.getLogger(AbstractLoginTest.class);
    @SuppressWarnings("checkstyle:visibilityModifier")
    public T fixture;

    protected AbstractLoginTest(T fixture) {
        this.fixture = fixture;
    }

    @BeforeEach
    public void setUp(TestInfo info) throws Exception {
        Settings.setJunitTestName(info.getDisplayName());
        LOG.debug("Setting up Tempo Fixture");

        fixture.setTakeErrorScreenshotsTo(true);
        fixture.setScreenshotPathTo(PropertiesUtilities.getProps().getProperty(Constants.DOWNLOAD_DIRECTORY));

        LOG.debug("Setting Up Login");
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(TEST_SITE_URL);
        fixture.setTimeoutSecondsTo(TEST_TIMEOUT);
        fixture.setAppianVersionTo(TEST_SITE_VERSION);
        fixture.setAppianLocaleTo(TEST_SITE_LOCALE);
        fixture.loginWithUsername(TEST_USERNAME);
    }

    @AfterEach
    public void tearDown() throws Exception {
        fixture.open(TEST_SITE_URL);
        fixture.logout();
        fixture.tearDown();
    }
}

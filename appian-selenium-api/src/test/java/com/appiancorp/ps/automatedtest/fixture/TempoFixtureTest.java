package com.appiancorp.ps.automatedtest.fixture;

import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.exception.IllegalArgumentTestException;
import com.appiancorp.ps.automatedtest.tempo.TempoSearch;
import com.appiancorp.ps.automatedtest.test.AbstractLoginTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class TempoFixtureTest extends AbstractLoginTest<TempoFixture> {

    private static final String AUTHENTICATION_URL_POST173 = TEST_SITE_URL + "admin/page/appian-authentication";
    private static final String REQUIRE_TERMS_CHECKBOX = "Require Users to Accept Terms of Service Before Logging In";
    private static final String SAVE_CHANGES_BUTTON = "Save Changes";
    private static final String TERMS_OF_SERVICE_FIELD = "Terms of Service";
    private static final String TERMS_OF_SERVICE_TEXT = "I agree to Terms of Service.";

    private static final Logger LOG = LogManager.getLogger(TempoFixtureTest.class);
    private static boolean isSetUp = false;
    protected static String randString;
    protected static Integer randInt;
    protected static Double randDecimal;

    public TempoFixtureTest() {
        super(new TempoFixture());
    }

    @BeforeEach
    public void setUpData() throws Exception {
        if (!isSetUp) {
            LOG.debug("Submitting new Record");

            fixture.clickOnMenu("Actions");
            fixture.clickOnAction("Automated Testing Input");

            randString = fixture.getRandomString(5);
            randInt = fixture.getRandomIntegerFromTo(0, 9);
            randDecimal = fixture.getRandomDecimalFromToWith(1.0, 2.0, 4);
            fixture.populateFieldWith("Title", new String[] {randString});
            fixture.populateFieldWith("Quantity", new String[] {randInt.toString()});
            fixture.populateFieldWith("Price", new String[] {randDecimal.toString()});
            fixture.populateFieldWith("Start Date", new String[] {"2016-02-04 02:00"});

            fixture.clickOnButton("Submit");

            isSetUp = true;
        }
    }

    @Test
    public void testTasksSort() {

        // Setup
        fixture.clickOnMenu("Tasks");

        // Verify oldest
        fixture.sortByOldest();
        assertTrue(fixture.verifySortLabel("Oldest"));

        // Verify newest
        fixture.sortByNewest();
        assertTrue(fixture.verifySortLabel("Newest"));
    }

    @Test
    public void testNews() {
        // Setup
        fixture.clickOnMenu("News");

        // Verify Post
        assertTrue(fixture.verifyNewsFeedContainingTextIsPresent(randString));
        assertTrue(fixture.verifyNewsFeedContainingTextIsNotPresent("Not present"));

        // Verify Comment
        assertTrue(fixture.verifyNewsFeedContainingTextCommentedWithIsPresent(randString, "Comment"));

        // Verify More Info
        fixture.toggleMoreInfoForNewsFeedContainingText(randString);
        assertTrue(fixture.verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(randString, "Label",
                "Value"));

        // Regex
        assertEquals(fixture.getRegexGroupFromNewsFeedContainingText("\\[([a-zA-Z0-9]{5})\\]", 1, randString),
                randString);
        assertEquals(fixture.getRegexGroupFromNewsFeedContainingTextCommentedWith("\\[([0-9])\\]", 1, randString,
                "Comment"), randInt.toString());

        // Search
        fixture.searchFor(randString);

        // Record Tag
        assertTrue(fixture.verifyNewsFeedContainingTextTaggedWithIsPresent(randString, randString));
        fixture.clickOnNewsFeedRecordTag(randString, randString);
    }

    @Test
    public void testSendKudos() {

        // Setup
        String newsText = "Good job! - " + fixture.getRandomString(5);
        fixture.clickOnMenu("News");
        fixture.sendKudosTo(newsText, "Fitness Twoser");

        // Verify Kudos
        assertTrue(fixture.verifyNewsFeedContainingTextIsPresent(newsText));
    }

    @Test
    public void testSendMessage() {
        // Setup
        String newsText = "Hello to you! - " + fixture.getRandomString(5);
        List<String> recipients = new ArrayList<>();
        recipients.add("Fitnesse Twoser");
        fixture.clickOnMenu("News");
        fixture.sendMessageTo("LOCKED", newsText, recipients);

        // Verify Message
        assertTrue(fixture.verifyNewsFeedContainingTextIsPresent(newsText));
    }

    @Test
    public void testSendTask() {
        // Setup
        String newsText = "Perform this tasklK - " + fixture.getRandomString(5);
        fixture.clickOnMenu("News");
        fixture.sendTaskTo(newsText, "Fitnesse Twoser");

        // Verify Task Sent
        fixture.clickOnMenu("Tasks");
        fixture.clickOnTaskReport("Sent by Me");
        fixture.verifyNewsFeedContainingTextIsPresent(newsText);
    }

    @Test
    public void testSendPost() {
        // Setup
        String newsText = "This is a post - " + fixture.getRandomString(5);
        List<String> participants = new ArrayList<>();
        participants.add("Fitnesse Twoser");
        fixture.clickOnMenu("News");
        fixture.sendPostTo(newsText, participants);

        // Verify Post
        assertTrue(fixture.verifyNewsFeedContainingTextIsPresent(newsText));
    }

    @Test
    public void testSendPostNoParticipants() {
        // Setup
        String newsText = "This is a post - " + fixture.getRandomString(5);
        fixture.clickOnMenu("News");
        fixture.sendPost(newsText);

        // Verify Post
        assertTrue(fixture.verifyNewsFeedContainingTextIsPresent(newsText));
    }

    @Test
    public void testStarPost() {
        // Setup
        String newsText = "star post test - " + fixture.getRandomString(5);
        List<String> participants = new ArrayList<>();
        participants.add("Fitnesse Twoser");
        fixture.clickOnMenu("News");
        fixture.sendPostTo(newsText, participants);
        fixture.starPostContaining(newsText);

        // Verify post star value changes
        assertTrue(fixture.verifyPostIsStarred(newsText));
    }

    @Test
    public void testAddComment() {
        // Setup
        String newsText = "post comment test - " + fixture.getRandomString(5);
        String postComment = "This is a comment";
        List<String> participants = new ArrayList<>();
        participants.add("Fitnesse Twoser");
        fixture.clickOnMenu("News");
        fixture.sendPostTo(newsText, participants);
        fixture.addCommentToPostContaining(postComment, newsText);

        // Verify comment posted
        assertTrue(fixture.verifyNewsFeedContainingTextCommentedWithIsPresent(newsText, postComment));
    }

    @Test
    public void testAddCommentToTask() {
        // Setup
        String taskMessage = "Perform This Task - " + fixture.getRandomString(5);
        String taskComment = "How about that";
        fixture.clickOnMenu("News");
        fixture.sendTaskTo(taskMessage, "Fitnesse Twoser");
        fixture.clickOnMenu("Tasks");
        fixture.clickOnTaskReport("Sent by me");
        fixture.addCommentToPostContaining(taskComment, taskMessage);

        // Verify comment posted
        assertTrue(
                fixture.verifyNewsFeedContainingTextCommentedWithIsPresent(taskMessage, taskComment));
    }

    @Test
    public void testCloseSocialTaskWithComment() {
        // Setup
        String taskMessage = "Perform This Social Task - " + fixture.getRandomString(5);
        fixture.clickOnMenu("News");
    /* Sending task to same user that's logged in because of instability of tests when assigning it to another
       user and logging in as that user. The logging out and logging back in immediately functionality has
       shown to be unstable and has to be fixed with a Thread.sleep() in between the two actions.
     */
        /* ---------------OLD UNSTABLE TEST---------------*/
    /* fixture.sendTaskTo(taskMessage, "Fitnesse Twoser");
       fixture.logout();
       fixture.loginWithUsername("fitnesse.twoser");
    */
        fixture.sendTaskTo(taskMessage, "Fitnesse User");
        fixture.clickOnMenu("Tasks");
        fixture.closeSocialTaskContainingWithComment(taskMessage, "Closing this social task");

        // Verify social task is closed
        assertTrue(fixture.verifyTaskFeedContainingTextIsNotPresent(taskMessage));
    }

    @Test
    public void testCloseSocialTaskWithoutComment() {
        // Setup
        String taskMessage = "Perform This Social Task - " + fixture.getRandomString(5);
        String comment = null;
        fixture.clickOnMenu("News");
    /* Sending task to same user that's logged in because of instability of tests when assigning it to another
       user and logging in as that user. The logging out and logging back in immediately functionality has
       shown to be unstable and has to be fixed with a Thread.sleep() in between the two actions.
     */
        /* ---------------OLD UNSTABLE TEST---------------*/
    /* fixture.sendTaskTo(taskMessage, "Fitnesse Twoser");
       fixture.logout();
       fixture.loginWithUsername("fitnesse.twoser");
    */
        fixture.sendTaskTo(taskMessage, "Fitnesse User");
        fixture.clickOnMenu("Tasks");
        fixture.closeSocialTaskContainingWithComment(taskMessage, comment);

        // Verify social task is closed
        assertTrue(fixture.verifyTaskFeedContainingTextIsNotPresent(taskMessage));
    }

    @Test
    public void testNewsFilter() {
        // Setup
        String newsText = "news filter text - " + fixture.getRandomString(5);
        List<String> participants = new ArrayList<>();
        participants.add("Fitnesse Twoser");
        fixture.clickOnMenu("News");
        fixture.sendPostTo(newsText, participants);
        fixture.starPostContaining(newsText);
        fixture.filterNewsOn("Starred");

        // Verify post appears in starred filter
        assertTrue(fixture.verifyNewsFeedContainingTextIsPresent(newsText));

        // Verify post does not appear in kudos filter
        fixture.filterNewsOn("Kudos");
        assertTrue(fixture.verifyNewsFeedContainingTextIsNotPresent(newsText));
    }

    @Test
    public void testNewsFeedLinkNavigation() {
        // Setup
        String newsText = "posted at navigation test - " + fixture.getRandomString(5);
        List<String> participants = new ArrayList<>();
        participants.add("Fitnesse Twoser");
        fixture.clickOnMenu("News");
        fixture.sendPostTo(newsText, participants);

        // Verify click and navigation
        assertTrue(fixture.verifyNewsFeedContainingLinkNavigation(newsText));

        // Verify that post exists at id url
        assertTrue(fixture.verifyNewsFeedContainingTextIsPresent(newsText));
    }

    @Test
    public void testNewsPosterCircleHover() {
        // Setup
        String newsText = "This is a post - " + fixture.getRandomString(5);
        List<String> participants = new ArrayList<>();
        participants.add("Fitnesse Twoser");
        fixture.clickOnMenu("News");
        fixture.sendPostTo(newsText, participants);

        // Hover and verify profile card popup shows
        assertTrue(fixture.verifyHoverOverNewsPosterCircleOnPostContaining(newsText));
    }

    @Test
    public void testNewsPosterLinkHover() {
        // Setup
        String newsText = "This is a post - " + fixture.getRandomString(5);
        List<String> participants = new ArrayList<>();
        participants.add("Fitnesse Twoser");
        fixture.clickOnMenu("News");
        fixture.sendPostTo(newsText, participants);

        // Hover and verify profile card popup shows
        assertTrue(fixture.verifyHoverOverNewsPosterLinkOnPostContaining(newsText));
    }

    @Test
    public void testClickNewsPosterProfileCircle() throws Exception {
        // Setup
        String newsText = "This is a post - " + fixture.getRandomString(5);
        List<String> participants = new ArrayList<>();
        participants.add("Fitnesse Twoser");
        fixture.clickOnMenu("News");
        fixture.sendPostTo(newsText, participants);

        // Click Profile
        fixture.clickUserProfileCircleOnPostContaining(newsText);

        // Verify Navigation
        assertTrue(fixture.verifyTextIsPresent("Edit Profile"));
    }

    @Test
    public void testClickNewsPosterProfileLink() throws InterruptedException {
        // Setup
        String newsText = "This is a post - " + fixture.getRandomString(5);
        List<String> participants = new ArrayList<>();
        participants.add("Fitnesse Twoser");
        fixture.clickOnMenu("News");
        fixture.sendPostTo(newsText, participants);

        // Click Profile
        fixture.clickUserProfileLinkOnPostContaining(newsText);

        // Verify Navigation
        assertTrue(fixture.verifyTextIsPresent("Edit Profile"));
    }

    @Test
    public void testRecordList() throws Exception {
        // Setup
        navigateToListView();

        // Verify Presence
        assertTrue(fixture.verifyRecordIsPresent(randString));
        assertTrue(fixture.verifyRecordIsNotPresent("Invalid"));

        //Search for Record
        fixture.searchFor(randString);
        assertTrue(fixture.verifyRecordIsPresent(randString));

        //Reset
        navigateToListView();

        // Record Type Filter
        fixture.populateRecordTypeUserFilterWith("Datetime", "Past");
        fixture.populateRecordTypeUserFilterWith("Datetime", "Future");
        fixture.clearRecordTypeUserFilter("Datetime");

        // Record Type Date Range Filter
        fixture.populateRecordTypeDateRangeUserFilterWithTo("Date Range", "12/12/2000", "12/13/2000");
        fixture.clearRecordTypeUserFilter("Date Range");

        // Regex
        assertEquals(fixture.getRegexGroupFromRecordNameContainingText("([a-zA-Z0-9]{5})", 1, randString),
                randString);

        // Click on Record Name[Index]
        fixture.clickOnRecord(randString + "[1]");

        // Click on Record [Index]
        navigateToListView();
        fixture.clickOnRecord("[1]");

        // Related Action View
        fixture.clickOnRecordView("Related Actions");
        assertTrue(fixture.verifyRecordRelatedActionIsPresent("AUT Data Input Test"));
        assertTrue(fixture.verifyRecordRelatedActionIsNotPresent("Not present"));
        fixture.clickOnRecordRelatedAction("AUT Data Input Test");
        fixture.clickOnButton("Cancel");

        // Related Action Shortcut
        fixture.clickOnRecordView("Summary");
        fixture.clickOnRecordRelatedAction("AUT Data Input Test");
        fixture.clickOnButton("Cancel");
    }

    @Test
    public void testRecordSummaryPageActionButtons() throws Exception {
        Assumptions.assumeTrue(atLeastVersion(25.1));
        // Setup
        navigateToListView();

        // Click on Record Name[Index]
        fixture.clickOnRecord(randString + "[1]");

        // Related Action Shortcut
        fixture.clickOnRecordView("Summary");
        // Click First Action
        fixture.clickOnRecordRelatedAction("AUT Data Input Test");
        fixture.clickOnButton("Cancel");
        // Click the 4th Action.
        // After 3 actions, remaining are grouped under more
        // actions button and this is to test it
        fixture.clickOnRecordRelatedAction("AUT Data Input Test 4");
        fixture.clickOnButton("Cancel");
    }

    @Test
    public void testDialogBoxPopUpInRecord() {
        // Setup
        fixture.clickOnMenu("Records");
        fixture.clickOnRecordType("Automated Testing Records Dialog Box");
        fixture.clickOnRecord("[1]");
        fixture.clickOnButton("OPEN DIALOG BOX");

        //When there is a open dialog box in the summary view of a record type and the
        //pop up box has the identical elements from the summary view, the DOM contains
        //duplicate elements and the only way to click on the elements on the pop up dialog
        //box is by correctly indexing.
        fixture.toggleBoxVisibility("Box[3]");
        assertTrue(fixture.verifyFieldIsNotPresent("ROTextField[2]"));
        fixture.toggleBoxVisibility("Box[3]");
        assertTrue(fixture.verifyFieldIsPresent("ROTextField[2]"));

        fixture.toggleBoxVisibility("Box[4]");
        assertTrue(fixture.verifyFieldIsNotPresent("BoxTwoROTextField[2]"));
        fixture.toggleBoxVisibility("Box[4]");
        assertTrue(fixture.verifyFieldIsPresent("BoxTwoROTextField[2]"));
        fixture.clickOnButton("Cancel[2]");
        assertTrue(fixture.verifyFieldIsNotPresent("BoxTwoROTextField[2]"));
        fixture.clickOnButton("Cancel");
    }


    @Test
    public void testSaveFiltersAs() {
        navigateToListView();
        fixture.clickOnButtonWithTooltip("Manage Filters");
        fixture.clickOnMenuWidget("Save filters as...");
        fixture.verifyButtonIsPresent("SAVE");
        fixture.clickOnButton("CANCEL");

        fixture.clickOnButtonWithTooltip("Manage Filters");
        fixture.clickOnMenuWidget("[1]");
        fixture.verifyButtonIsPresent("SAVE");
        fixture.clickOnButton("CANCEL");

        fixture.clickOnButtonWithTooltip("Manage Filters");
        fixture.clickOnMenuWidget("Save filters as...[1]");
        fixture.verifyButtonIsPresent("SAVE");
        fixture.clickOnButton("CANCEL");
    }

    private void navigateToListView() {
        fixture.clickOnMenu("Records");
        fixture.clickOnRecordType("Automated Testing Records");
    }

    @Test
    public void testRecordGrid() throws Exception {
        // Setup
        fixture.clickOnMenu("Records");
        fixture.clickOnRecordType("Automated Test Grid[1]");

        // Verify Presence
        fixture.verifyRecordIsPresent(randString + "[1]");

        // Sort
        fixture.sortRecordGridByColumn("Title");

        // Navigation
        swallowRecordGridNavigationException(() -> fixture.clickOnRecordGridNavigation("Next"));
        swallowRecordGridNavigationException(() -> fixture.clickOnRecordGridNavigation("First"));
        swallowRecordGridNavigationException(() -> fixture.clickOnRecordGridNavigation("Last"));
        swallowRecordGridNavigationException(() -> fixture.clickOnRecordGridNavigation("Previous"));

        try {
            fixture.clickOnRecordGridNavigation("Invalid");
            fail("Should have thrown illegal argument exception");
        } catch (IllegalArgumentTestException e) {
        }

        // Search
        fixture.searchFor(randString);
        assertTrue(fixture.verifyRecordIsPresent(randString));

        // Click on Record Name[Index]
        fixture.clickOnRecord(randString + "[1]");

        // Click on Record Index
        fixture.clickOnMenu("Records");
        fixture.clickOnRecordType("Automated Test Grid");
        fixture.clickOnRecord("[1]");
    }

    @Test
    public void testTask() throws Exception {
        // Setup
        fixture.clickOnMenu("Actions");
        fixture.clickOnAction("All Fields");
        fixture.clickOnMenu("Tasks");

        // Verify Presence
        assertTrue(fixture.verifyTaskIsPresent("All Fields Task"));
        assertTrue(fixture.verifyTaskIsNotPresent("Not present"));

        // Regex
        assertEquals(fixture.getRegexGroupFromTaskNameContainingText("([a-zA-Z0-9]{0,5})", 1, "All Fields"),
                "All");

        // Deadline
        assertTrue(fixture.verifyTaskHasDeadlineOf("All Fields Task", "1h"));

        // Task Report
        fixture.clickOnTaskReport("Task Report");

        // Click
        fixture.clickOnMenu("Tasks");
        fixture.clickOnTask("All Fields Task[1]");
        fixture.clickOnButton("Cancel");
    }

    @Test
    public void testAcceptTask() throws Exception {
        // Test Task Header Buttons
        fixture.clickOnMenu("Actions");
        fixture.clickOnApplicationFilter("Automated Testing");
        fixture.clickOnAction("Test with Accept Button");
        fixture.clickOnMenu("Tasks");
        fixture.clickOnTask("Task with Accept Button");
        fixture.acceptTask();
        fixture.clickOnButton("Ok");
    }

    @Test
    public void testReport() throws Exception {
        // Setup
        fixture.clickOnMenu("Reports");

        // Search
        fixture.searchFor("Automated Test Report");

        // Verify Presence
        assertTrue(fixture.verifyReportIsPresent("Automated Test Report[1]"));
        assertTrue(fixture.verifyReportIsNotPresent("Not Report"));

        // Click
        fixture.clickOnReport("Automated Test Report[1]");
        fixture.verifyChartIsPresent("Remaining");
        fixture.verifyChartIsNotPresent("Not Remaining");
    }

    @Test
    public void testAction() throws Exception {
        // Setup
        fixture.clickOnMenu("Actions");

        // Verify Presence
        assertTrue(fixture.verifyActionIsPresent("Automated Testing[1]"));
        assertTrue(fixture.verifyActionIsNotPresent("Not Automated Testing"));
        assertTrue(fixture.verifyApplicationFilterIsNotPresent("Not Automated"));
        assertTrue(fixture.verifyApplicationFilterIsPresent("Automated Testing"));

        // Click
        fixture.clickOnApplicationFilter("Automated");
        fixture.clickOnAction("Automated Testing[1]");
        fixture.clickOnButton("Cancel");
    }

    @Test
    public void testStarAction() {
        // Setup
        fixture.clickOnMenu("Actions");

        // Star Action
        fixture.starAction("Automated Testing");

        // Verify Starred
        fixture.clickOnLink("Starred");
        assertTrue(fixture.verifyActionIsPresent("Automated Testing"));

        // Reset
        fixture.starAction("Automated Testing");
    }

    @Test
    public void testTextAssertion() {
        // Setup
        fixture.clickOnMenu("Tasks");

        // Verify Text is Present or not Present
        assertTrue(fixture.verifyTextIsPresent("Assigned to Me"));
        assertTrue(fixture.verifyTextIsPresent("Sent by Me"));
        assertTrue(!fixture.verifyTextIsPresent("This is a long test string"));
    }

    @Test
    public void testLoginWithTermsWithUsernameAndPassword() throws Exception {
        //Ignoring this test because when this test fails halfway, it blocks other tests from working
        Assumptions.assumeFalse(isRemoteBrowser());
        setupSiteWithTermsOfService();
        fixture.loginWithTermsWithUsernameAndPassword(TEST_ADMIN_USERNAME, TEST_ADMIN_PASSWORD);
        removeTermsOfService();
    }

    @Test
    public void testLoginWithTermsWithUsername() throws Exception {
        //Ignoring this test because when this test fails halfway, it blocks other tests from working
        Assumptions.assumeFalse(isRemoteBrowser());
        setupSiteWithTermsOfService();
        fixture.loginWithTermsWithUsername(TEST_ADMIN_USERNAME);
        removeTermsOfService();
    }

    @Test
    public void testLoginWithTermsWithRole() throws Exception {
        //Ignoring this test because when this test fails halfway, it blocks other tests from working
        Assumptions.assumeFalse(isRemoteBrowser());
        setupSiteWithTermsOfService();
        fixture.loginWithTermsWithRole(TEST_ADMIN_ROLE);
        removeTermsOfService();
    }

    @Test
    public void testClickOnXAndYCoordinatesOnMonitor() {
        // Robot class click doesn't work in Docker
        Assumptions.assumeFalse(isRemoteBrowser());

        WebDriver driver = fixture.getSettings().getDriver();
        driver.manage().window().setPosition(new Point(0, 0));
        fixture.resizeWindowWidthHeight(1000, 1000);

        // This is the coordinate of the Report tab when browser is moved to top left on monitor
        if (isBrowser(Constants.Driver.CHROME.name())) {
            fixture.clickOnXAndYCoordinatesOnMonitor(350, 160);
        } else {
            fixture.clickOnXAndYCoordinatesOnMonitor(350, 120);
        }
        fixture.verifyReportIsPresent("Automated Test Report");
    }

    @Test
    public void testClearTempoSearch() {
        fixture.clickOnMenu("Records");
        fixture.verifyTextIsPresent("automated testing record");
        TempoSearch.getInstance(fixture.settings).populate("automated test grid");
        fixture.verifyTextIsNotPresent("automated testing record");
        TempoSearch.getInstance(fixture.settings).clear();
        fixture.verifyTextIsPresent("automated testing record");
    }

    private void setupSiteWithTermsOfService() {
        /* Logout of initial browser setup */
        fixture.tearDown();
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(AUTHENTICATION_URL_POST173);
        /* Login as Administrator to access Admin Console */
        fixture.loginWithUsernameAndPassword(TEST_ADMIN_USERNAME, TEST_ADMIN_PASSWORD);
        /* Add Terms of Service */
        fixture.clickOnCheckboxOption(REQUIRE_TERMS_CHECKBOX);
        fixture.populateFieldWith(TERMS_OF_SERVICE_FIELD, new String[] {TERMS_OF_SERVICE_TEXT});
        fixture.clickOnButton(SAVE_CHANGES_BUTTON);
        fixture.tearDown();
        fixture.setupWithBrowser(TEST_BROWSER);
        fixture.setAppianUrlTo(AUTHENTICATION_URL_POST173);
    }


    private void removeTermsOfService() throws Exception {
        /* Remove Terms of Service to reset for other tests */
        fixture.clickOnCheckboxOption(REQUIRE_TERMS_CHECKBOX);
        fixture.clickOnButton(SAVE_CHANGES_BUTTON);
    }

    private void swallowRecordGridNavigationException(Runnable func) {
        try {
            func.run();
        } catch (Exception e) {
        }
    }
}

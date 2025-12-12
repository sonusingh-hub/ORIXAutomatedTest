package com.appiancorp.ps.automatedtest.fixture;

import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.tempo.TempoAssertion;
import com.appiancorp.ps.automatedtest.tempo.TempoError;
import com.appiancorp.ps.automatedtest.tempo.TempoLogin;
import com.appiancorp.ps.automatedtest.tempo.TempoMenu;
import com.appiancorp.ps.automatedtest.tempo.TempoSearch;
import com.appiancorp.ps.automatedtest.tempo.action.TempoAction;
import com.appiancorp.ps.automatedtest.tempo.action.TempoActionApplicationFilter;
import com.appiancorp.ps.automatedtest.tempo.action.TempoActionStarAction;
import com.appiancorp.ps.automatedtest.tempo.interfaces.*;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsFilters;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItem;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemAddComment;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemComment;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemMoreInfo;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemMoreInfoLabelValue;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemPosted;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemPoster;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemStarPost;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsItemTag;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsSendKudos;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsSendMessage;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsSendPost;
import com.appiancorp.ps.automatedtest.tempo.news.TempoNewsSendTask;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecord;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordGridColumn;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordGridNavigation;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordRelatedAction;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordType;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordTypeUserFilter;
import com.appiancorp.ps.automatedtest.tempo.record.TempoRecordView;
import com.appiancorp.ps.automatedtest.tempo.report.TempoReport;
import com.appiancorp.ps.automatedtest.tempo.task.TempoSocialTaskItem;
import com.appiancorp.ps.automatedtest.tempo.task.TempoSocialTaskItemClose;
import com.appiancorp.ps.automatedtest.tempo.task.TempoTask;
import com.appiancorp.ps.automatedtest.tempo.task.TempoTaskDeadline;
import com.appiancorp.ps.automatedtest.tempo.task.TempoTaskReport;
import com.appiancorp.ps.automatedtest.tempo.task.TempoTaskSort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * This is the tempo class for integrating Appian and FitNesse.
 * This class contains fixture commands which are specific to the Tempo interface
 */
public class TempoFixture extends BaseFixture {

    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(TempoFixture.class);

    public TempoFixture() {
        super();
    }

    /**
     * Clicks on the associated tempo menu.<br>
     * <br>
     * Example: <code>| click on menu | MENU_NAME |</code>
     *
     * @param tempoMenu Name of tempo menu, e.g. Records, Tasks, News
     */
    public void clickOnMenu(String tempoMenu) {
        TempoMenu.getInstance(settings).waitFor(tempoMenu);
        TempoMenu.getInstance(settings).click(tempoMenu);
    }

    /**
     * Clicks on menu widget item.<br>
     * <br>
     * Example: <code>| click on menu widget | MENU_WIDGET or MENU_WIDGET[INDEX] or [INDEX] |</code>
     *
     * @param menuWidget name of menu widget
     */
    public void clickOnMenuWidget(String menuWidget) {
        TempoMenuWidget.getInstance(settings).waitFor(menuWidget);
        TempoMenuWidget.getInstance(settings).click(menuWidget);
    }

    /**
     * Populates search fields in News, Reports, and Records. <br>
     * <br>
     * Example: <code>| search for | SEARCH_TERM |</code>
     *
     * @param searchTerm The term to search for News, Reports, and Records
     */
    public void searchFor(String searchTerm) {
        TempoSearch.getInstance(settings).waitFor(searchTerm);
        TempoSearch.getInstance(settings).populate(searchTerm);
    }

    /**
     * Clears search field in Records. <br>
     * Example: <code>| clear search value |</code>
     */
    public void clearSearchValue() {
        TempoSearch.getInstance(settings).clear();
    }

    /**
     * Logs out of Appian <br>
     * Example: <code>| logout |</code>
     */
    public void logout() {
        TempoLogin.getInstance(settings).waitFor();
        TempoLogin.getInstance(settings).logout();
    }

    /*
     * News
     */

    /**
     * Verifies a news post containing specific text is present.
     * The method will wait for the timeout period and refresh up to the configured number of refresh times
     * before failing.<br>
     * <br>
     * FitNesse Example: <code>| verify news feed containing text | NEWS_TEXT | is present |</code>
     *
     * @param newsText Text to search for in the news feed
     * @return True, if post is located with specific text
     */
    public boolean verifyNewsFeedContainingTextIsPresent(String newsText) {
        TempoNewsItem.getInstance(settings).refreshAndWaitFor(newsText);
        return true;
    }

    /**
     * Verifies a news item containing specific text is not present.<br>
     * <br>
     * FitNesse Example: <code>| verify news feed containing text | NEWS_TITLE | is not present |</code><br>
     * <br>
     * The reason to use than <code>| reject | verify news feed containing text | NEWS_TEXT | is present |</code>
     * is that this method will not
     * refresh and wait.
     *
     * @param newsText Text to search for in the news feed
     * @return True, if no post is located with specific text
     */
    public boolean verifyNewsFeedContainingTextIsNotPresent(String newsText) {
        return TempoNewsItem.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), newsText);
    }

    /**
     * Toggles the more info on a news item containing specific text.<br>
     * <br>
     * FitNesse Example: <code>| toggle more info for news feed containing text | NEWS_TEXT |</code>
     *
     * @param newsText Text to search for in the news feed
     */
    public void toggleMoreInfoForNewsFeedContainingText(String newsText) {
        TempoNewsItemMoreInfo.getInstance(settings).waitFor(newsText);
        TempoNewsItemMoreInfo.getInstance(settings).click(newsText);
    }

    /**
     * Deletes a news post.<br>
     * <br>
     * FitNesse Example: <code>| delete news post | NEWS_TEXT |</code>
     *
     * @param newsText Text to search for in the news feed
     */
    public void deleteNewsPost(String newsText) {
        TempoNewsItem.getInstance(settings).waitFor(newsText);
        TempoNewsItem.getInstance(settings).clear(newsText);
    }

    /**
     * Verifies a news item containing specific text with a specific label and value is present.<br>
     * Deletes a news post.<br>
     * <br>
     * FitNesse Example: <code>| delete news post | NEWS_TEXT |</code>
     */
    public void deleteAllNewsPosts() {
        TempoNewsItem.getInstance(settings).clearAll();
    }

    /**
     * Verifies there is a news post containing specific text with a specific label and value is present.<br>
     * <br>
     * FitNesse Example:
     * <code>| verify news feed containing text | NEWS_TEXT | and more info with label | LABEL | and value | VALUE | is present |</code>
     *
     * @param newsText Text to search for in the news feed
     * @param label    Label to search for in the more info
     * @param value    Value to search for in the more info
     * @return True, if a news post with the specific text, label, and value is located
     */
    public boolean verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(String newsText, String label,
                                                                                     String value) {
        TempoNewsItemMoreInfoLabelValue.getInstance(settings).refreshAndWaitFor(newsText);
        TempoNewsItemMoreInfoLabelValue.getInstance(settings).waitFor(newsText, label, value);
        return true;
    }

    /**
     * Verifies a news item containing specific text with a specific tag is present.<br>
     * <br>
     * FitNesse Example: <code>| verify news feed containing text | NEWS_TEXT | tagged with | RECORD_TAG | is present |</code>
     *
     * @param newsText Text to search for in the news feed
     * @param newsTag  Tag to search for on the news post
     * @return True, if a news post with the specific text and tag is located
     */
    public boolean verifyNewsFeedContainingTextTaggedWithIsPresent(String newsText, String newsTag) {
        TempoNewsItemTag.getInstance(settings).refreshAndWaitFor(newsText, newsTag);
        TempoNewsItemTag.getInstance(settings).waitFor(newsText, newsTag);
        return true;
    }

    /**
     * Verifies a news item containing specific text with a specific comment is present.<br>
     * <br>
     * FitNesse Example: <code>| verify news feed containing text | NEWS_TEXT | commented with | COMMENT | is present |</code>
     *
     * @param newsText    Text to search for in the news feed
     * @param newsComment Text to search for in the comments
     * @return True, if a news post with the specific text and comment is located
     */
    public boolean verifyNewsFeedContainingTextCommentedWithIsPresent(String newsText, String newsComment) {
        TempoNewsItemComment.getInstance(settings).refreshAndWaitFor(newsText, newsComment);
        return true;
    }

    /**
     * Returns a string that matches the regex, this could be useful in extracting a system generated value from the news feed.<br>
     * <br>
     * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from news feed containing text | NEWS_TEXT |</code>
     * <code>| $regex= | get regex | [A-z]{3}-[0-9]{4} | group | GROUP |  </code> - Stores the regex value, which can later be accessed using
     * $error<br>
     *
     * @param regex    Regular expression string to search for within the news text
     * @param group    Regular expression group to return
     * @param newsText Text to search for in the news feed
     * @return String that matches the regular expression
     */
    public String getRegexGroupFromNewsFeedContainingText(String regex, Integer group, String newsText) {
        TempoNewsItem.getInstance(settings).refreshAndWaitFor(newsText);
        return TempoNewsItem.getInstance(settings).regexCapture(regex, group, newsText);
    }

    /**
     * Returns a string that matches the regex from a comment, this could be useful in extracting a system generated value from the news feed.
     * <br>
     * <br>
     * FitNesse Example:
     * <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from news feed containing text | NEWS_TEXT | commented with | COMMENTS |</code>
     * <code>| $regex= | get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from news feed containing text | NEWS_TEXT | commented with | COMMENTS |  </code>
     * - Stores the regex value, which can later be accessed using $error<br>
     *
     * @param regex       Regular expression string to search for within the news text
     * @param group       Regular expression group to return
     * @param newsText    Text to search for in the news feed
     * @param newsComment Text to search for in the comments
     * @return String that matches the regular expression
     */
    public String getRegexGroupFromNewsFeedContainingTextCommentedWith(String regex, Integer group, String newsText,
                                                                       String newsComment) {
        TempoNewsItemComment.getInstance(settings).refreshAndWaitFor(newsText, newsComment);
        return TempoNewsItemComment.getInstance(settings).regexCapture(regex, group, newsText, newsComment);
    }

    /**
     * Clicks on a record tag to navigate to a record summary dashboard<br>
     * <br>
     * FitNesse Example: <code>| click on news feed | NEWS_TEXT | record tag | RECORD_TAG |</code>
     *
     * @param newsText  Text to search for in the news feed
     * @param recordTag Record tag text
     */
    public void clickOnNewsFeedRecordTag(String newsText, String recordTag) {
        TempoNewsItemTag.getInstance(settings).refreshAndWaitFor(newsText, recordTag);
        TempoNewsItemTag.getInstance(settings).click(newsText, recordTag);
    }

    /**
     * Sends a Kudos to the specified Recipient with the specified message<br>
     * <br>
     * FitNesse Example: <code>| send kudos | MESSAGE | to | RECIPIENT |</code>
     *
     * @param message   Message to include in Kudos
     * @param recipient User to send Kudos to
     */
    public void sendKudosTo(String message, String recipient) {
        TempoNewsSendKudos.getInstance(settings).activateKudos();
        TempoNewsSendKudos.getInstance(settings).addRecipient(recipient);
        TempoNewsSendKudos.getInstance(settings).addMessage(message);
    }

    /**
     * Sends a locked news message to specified recipients<br>
     * <br>
     * FitNesse Example: <code>| send | LOCKED or UNLOCKED | message | MESSAGE | to | RECIPIENTS |</code>
     *
     * @param lockStatus String representing whether the message should be locked or unlocked
     * @param message    Body of message to send
     * @param recipients List of users/groups to send message to
     */
    public void sendMessageTo(String lockStatus, String message, List<String> recipients) {
        lockStatus = lockStatus.toUpperCase();
        Boolean locked = "LOCKED".equals(lockStatus);
        TempoNewsSendMessage.getInstance(settings).activateMessage();
        TempoNewsSendMessage.getInstance(settings).addRecipients(recipients, locked);
        TempoNewsSendMessage.getInstance(settings).addMessage(message);
    }

    /**
     * Sends a task from the news message box<br>
     * <br>
     * FitNesse Example <code>| send task | TASK MESSAGE | to | RECIPIENT |</code>
     *
     * @param message   Description of task to send
     * @param recipient User to send task to
     */
    public void sendTaskTo(String message, String recipient) {
        TempoNewsSendTask.getInstance(settings).activateTask();
        TempoNewsSendTask.getInstance(settings).addRecipient(recipient);
        TempoNewsSendTask.getInstance(settings).addMessage(message);
    }

    /**
     * Sends a post to a list of participants<br>
     * <br>
     * FitNesse Example <code>| send post | MESSAGE | to | RECIPIENTS |</code>
     *
     * @param message      Message to include in post body
     * @param participants List of participants to send post to
     */
    public void sendPostTo(String message, List<String> participants) {
        TempoNewsSendPost.getInstance(settings).activatePost();
        TempoNewsSendPost.getInstance(settings).addParticipants(participants);
        TempoNewsSendPost.getInstance(settings).addMessage(message);
    }

    /**
     * Sends a post with no participants
     * <br>
     * FitNesse Example <code>| send post | MESSAGE |</code>
     *
     * @param message Message to post
     */
    public void sendPost(String message) {
        TempoNewsSendPost.getInstance(settings).activatePost();
        TempoNewsSendPost.getInstance(settings).addMessage(message);
    }

    /**
     * Stars post that contains supplied text<br>
     * <br>
     * FitNesse Example <code>| star post containing | MESSAGE |</code>
     *
     * @param newsText Text of news post to find and star
     */
    public void starPostContaining(String newsText) {
        TempoNewsItemStarPost.getInstance(settings).click(newsText);
    }

    /**
     * Verifies that news post with supplied text is starred<br>
     * <br>
     * FitNesse Example <code>| verify post | MESSAGE | is starred |</code>
     *
     * @param newsText Text of news post to verify is starred
     * @return true if starred, otherwise false
     */
    public boolean verifyPostIsStarred(String newsText) {
        return TempoNewsItemStarPost.getInstance(settings).isPostStarred(newsText);
    }

    /**
     * Adds supplied comment to post containing supplied text<br>
     * <br>
     * FitNesse Example <code>| add comment | COMMENT | to post containing | MESSAGE |</code>
     *
     * @param comment  Comment to add to news post
     * @param newsText Text of news post to add comment to
     */
    public void addCommentToPostContaining(String comment, String newsText) {
        TempoNewsItemAddComment.getInstance(settings).activateComment(newsText);
        TempoNewsItemAddComment.getInstance(settings).addComment(comment, newsText);
    }

    /**
     * Clicks supplied news feed filter name<br>
     * <br>
     * FitNesse Example <code>| filter news on | FILTER NAME |</code>
     *
     * @param filterName Name of predefined filter for news feed (e.g. Participating, Kudos, etc.)
     */
    public void filterNewsOn(String filterName) {
        TempoNewsFilters.getInstance(settings).click(filterName);
    }

    /**
     * Clicks on the posted at link for a news post containing the supplied text and verifies that the browser is
     * directed to a URL with the appropriate ID<br>
     * <br>
     * FitNesse Example: <code>| verify news feed containing | MESSAGE | link navigation |</code>
     *
     * @param newsText Text of news post to click link for
     * @return true if clicking posted at takes browser to target post id, otherwise false.
     */
    public boolean verifyNewsFeedContainingLinkNavigation(String newsText) {
        String newsPostTargetId = TempoNewsItemPosted.getInstance(settings).getPostId(newsText);
        if (newsPostTargetId.isEmpty()) {
            return false;
        }
        TempoNewsItemPosted.getInstance(settings).click(newsText);
        String currentURL = settings.getDriver().getCurrentUrl();
        String currentPostId = TempoNewsItemPosted.getInstance(settings).getPostIdFromURL(currentURL);
        return newsPostTargetId.equals(currentPostId);
    }

    /**
     * Hovers mouse over the user profile circle on a news post containing the supplied text.<br>
     * <br>
     * FitNesse Example: <code>| verify hover over news poster circle on post containing | MESSAGE |</code>
     *
     * @param newsText Text of news post used to associate user profile circle
     * @return true if post is located and mouse hover over profile circle causes the popup container to be
     * displayed, otherwise false.
     */
    public boolean verifyHoverOverNewsPosterCircleOnPostContaining(String newsText) {
        return TempoNewsItemPoster.getInstance(settings).hoverOnPosterCircle(newsText);
    }

    /**
     * Hovers over the user profile link on a news post containing the supplied text <br>
     * <br>
     * FitNesse Example: <code>| verify hover over news poster link on post containing | MESSAGE |</code>
     *
     * @param newsText Text of news post used to associate user profile link
     * @return true if post is located and mouse hover over profile link causes the popup container to be
     * displayed, otherwise false.
     */
    public boolean verifyHoverOverNewsPosterLinkOnPostContaining(String newsText) {
        return TempoNewsItemPoster.getInstance(settings).hoverOnPosterLink(newsText);
    }

    /**
     * Clicks the user profile circle on a news post containing the supplied text<br>
     * <br>
     * FitNesse Example: <code>| click user profile circle on post containing | MESSAGE |</code>
     *
     * @param newsText Text of news post used to associate user profile circle
     */
    public void clickUserProfileCircleOnPostContaining(String newsText) {
        TempoNewsItemPoster.getInstance(settings).clickUserProfileCircle(newsText);
    }

    /**
     * Clicks the user profile link on a news post containing the supplied text<br>
     * <br>
     * FitNesse Example: <code>| click user profile link on post containing | MESSAGE |</code>
     *
     * @param newsText Text of news post used to associate user profile link
     */
    public void clickUserProfileLinkOnPostContaining(String newsText) {
        TempoNewsItemPoster.getInstance(settings).clickUserProfileLink(newsText);
    }

    /*
     * Social Tasks
     */

    /**
     * Verifies a social task item containing specific text is not present.<br>
     * <br>
     * FitNesse Example: <code>| verify task feed containing text | TASK_TEXT | is not present |</code>
     *
     * @param taskText Text to search for in the task feed
     * @return True, if no task is located with specific text
     */
    public boolean verifyTaskFeedContainingTextIsNotPresent(String taskText) {
        return TempoSocialTaskItem.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), taskText);
    }

    /**
     * Close the social task containing supplied text and add a comment<br>
     * <br>
     * Fitnesse Example <code>| close social task containing | MESSAGE | with comment | COMMENT |</code>
     *
     * @param taskText Text of social task to close
     * @param comment  Comment to add before closing social task
     */
    public void closeSocialTaskContainingWithComment(String taskText, String comment) {
        TempoSocialTaskItemClose.getInstance(settings).activateCloseTask(taskText);
        TempoSocialTaskItemClose.getInstance(settings).addCloseComment(comment, taskText);
    }

    /*
     * Tasks
     */

    /**
     * Clicks on the sort by Newest for tasks. <br>
     * <br>
     * FitNesse Example: <code>| sort by newest |</code>
     */
    public void sortByNewest() {
        TempoTaskSort.getInstance(settings).sortByNewest();
    }

    /**
     * Clicks on the sort by Oldest for tasks. <br>
     * <br>
     * FitNesse Example: <code>| sort by oldest |</code>
     */
    public void sortByOldest() {
        TempoTaskSort.getInstance(settings).sortByOldest();
    }

    /**
     * Gets current sort label, and compare it with the given value. <br>
     * <br>
     * FitNesse Example: <code>| verify sort label | value |</code>
     *
     * @param value Sort label value
     * @return true for matching label
     * false for non-matching label
     */
    public boolean verifySortLabel(String value) {
        String currentValue = TempoTaskSort.getInstance(settings).getCurrentSortLabel();
        return currentValue.equals(value);
    }

    /**
     * Clicks on the associated task.<br>
     * <br>
     * FitNesse Example: <code>| click on task | TASK_NAME or TASK_NAME[INDEX] |</code>
     *
     * @param taskName Name of task to click (partial names are acceptable)
     *                 If multiple task contain the same name the first will be selected
     */
    public void clickOnTask(String taskName) {
        TempoTask.getInstance(settings).refreshAndWaitFor(taskName);
        TempoTask.getInstance(settings).click(taskName);
    }

    /**
     * Returns a string that matches the regex, this could be useful in extracting a system generated value from a task's name.<br>
     * <br>
     * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from task name containing text | TASK_TEXT | </code>
     *
     * @param regex    Regular expression string to search for within the form
     * @param group    Regular expression group to return
     * @param taskText Text to find within the tasks' names
     * @return String that matches the regular expression
     */
    public String getRegexGroupFromTaskNameContainingText(String regex, Integer group, String taskText) {
        TempoTask.getInstance(settings).refreshAndWaitFor(taskText);
        return TempoTask.getInstance(settings).regexCapture(regex, group, taskText);
    }

    /**
     * Verifies if task is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify task | TASK_NAME or TASK_NAME[INDEX] | is present |</code>
     *
     * @param taskName Name of the task
     * @return True, if task is located
     */
    public boolean verifyTaskIsPresent(String taskName) {
        TempoTask.getInstance(settings).refreshAndWaitFor(taskName);
        return true;
    }

    /**
     * Verifies if task is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify task | TASK_NAME or TASK_NAME[INDEX] | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify task | TASK_NAME | is present |</code> as it will not refresh and wait.
     *
     * @param taskName Name of the task
     * @return True, if task is located
     */
    public boolean verifyTaskIsNotPresent(String taskName) {
        return TempoTask.getInstance(settings).waitForReturn(false, settings.getNotPresentTimeoutSeconds(), taskName);
    }

    /**
     * Verify a task with a specific name has a specific deadline.<br>
     * <br>
     * FitNesse Example: <code>| verify task | TASK_NAME or TASK_NAME[INDEX] | has deadline of | DEADLINE |</code>
     *
     * @param taskName Name of the task
     * @param deadline Deadline matching the Appian interface, e.g. 8d
     * @return True, if task with particular deadline is located
     */
    public boolean verifyTaskHasDeadlineOf(String taskName, String deadline) {
        TempoTaskDeadline.getInstance(settings).waitFor(taskName, deadline);
        return true;
    }

    /**
     * Click on a task report.<br>
     * <br>
     * FitNesse Example: <code>| click on task report | TASK_REPORT_NAME |</code>
     *
     * @param taskReport Name of task report
     */
    public void clickOnTaskReport(String taskReport) {
        TempoTaskReport.getInstance(settings).waitFor(taskReport);
        TempoTaskReport.getInstance(settings).click(taskReport);
    }

    /**
     * Accepts a task if the task can be accepted.<br>
     * <br>
     * FitNesse Example: <code>| accept task |</code>
     */
    public void acceptTask() {
        TempoAcceptButton.getInstance(settings).click();
    }

    /**
     * Clicks on the record type.<br>
     * <br>
     * FitNesse Example: <code>| click on record type | RECORD_TYPE_NAME or RECORD_TYPE_NAME[INDEX] |</code>
     *
     * @param typeName Name of record type to click (partial names are acceptable)
     *                 If multiple record types contain the same name, then the first will be selected
     */
    public void clickOnRecordType(String typeName) {
        TempoRecordType.getInstance(settings).waitFor(typeName);
        TempoRecordType.getInstance(settings).click(typeName);
    }

    /**
     * Clicks on the record type user filter.<br>
     * <br>
     * FitNesse Example: <code>| click on record type user filter | USER_FILTER_NAME |</code>
     *
     * @param userFilter User Filter to click (partial names are acceptable)
     *                   If multiple user filters contain the same name, then the first will be selected
     * @deprecated
     */
    @Deprecated
    public void clickOnRecordTypeUserFilter(String userFilter) {
        TempoRecordTypeUserFilter.getInstance(settings).waitForLink(userFilter);
        TempoRecordTypeUserFilter.getInstance(settings).click(userFilter);
    }

    /**
     * Verifies if user filter is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record type user filter | USER_FILTER_NAME | is present |</code>
     *
     * @param userFilter Name of user filter
     * @return True, if user filter is located
     * @deprecated
     */
    @Deprecated
    public boolean verifyRecordTypeUserFilterIsPresent(String userFilter) {
        TempoRecordTypeUserFilter.getInstance(settings).waitForLink(userFilter);
        return true;
    }

    /**
     * Populate on the record type user filter with value.<br>
     * <br>
     * FitNesse Example: <code>| populate record type user filter | USER_FILTER_NAME | with | VALUE |</code>
     *
     * @param userFilter User Filter to populate (partial names are acceptable)
     * @param value      Value to select within User Filter dropdown
     */
    public void populateRecordTypeUserFilterWith(String userFilter, String value) {
        TempoRecordTypeUserFilter.getInstance(settings).waitFor(userFilter);
        TempoRecordTypeUserFilter.getInstance(settings).populate(userFilter, value);
    }

    /**
     * Populate on the record type date range user filter with value.<br>
     * <br>
     * FitNesse Example: <code>| populate record type date range user filter | USER_FILTER_NAME | with | START_DATE | to | END_DATE |</code>
     *
     * @param userFilter User Filter to populate (partial names are acceptable)
     * @param startDate  Start date to enter in the date range (format mm/dd/yyyy)
     * @param endDate    End date to enter in the date range (format mm/dd/yyyy)
     */
    public void populateRecordTypeDateRangeUserFilterWithTo(String userFilter, String startDate, String endDate) {
        TempoRecordTypeUserFilter.getInstance(settings).waitFor(userFilter);
        TempoRecordTypeUserFilter.getInstance(settings).populateDateRange(userFilter, startDate, endDate);
    }

    /**
     * Clear record type user filter.<br>
     * <br>
     * FitNesse Example: <code>| clear record type user filter | USER_FILTER_NAME |</code>
     *
     * @param userFilter User Filter to clear
     */
    public void clearRecordTypeUserFilter(String userFilter) {
        TempoRecordTypeUserFilter.getInstance(settings).waitFor(userFilter);
        TempoRecordTypeUserFilter.getInstance(settings).clear(userFilter);
    }

    /**
     * Clicks on the associated record.<br>
     * <br>
     * FitNesse Example: <code>| click on record | RECORD_NAME or RECORD_NAME[INDEX] or [INDEX] |</code>
     *
     * @param recordName Name of record to click (partial names are acceptable)
     *                   If multiple records contain the same name, then the first will be selected
     */
    public void clickOnRecord(String recordName) {
        TempoRecord.getInstance(settings).refreshAndWaitFor(recordName);
        TempoRecord.getInstance(settings).click(recordName);
    }

    /**
     * Returns a string that matches the regex, this could be useful in extracting a system generated value from a record whose name, i.e.,
     * title field matches. <br>
     * <br>
     * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from record name containing text | RECORD_TEXT | </code>
     *
     * @param regex      Regular expression string to search for within the form
     * @param group      Regular expression group to return
     * @param recordText Name of text contained within record name
     * @return String that matches the regular expression
     */
    public String getRegexGroupFromRecordNameContainingText(String regex, Integer group, String recordText) {
        TempoRecord.getInstance(settings).refreshAndWaitFor(recordText);
        return TempoRecord.getInstance(settings).regexCapture(regex, group, recordText);
    }

    /**
     * Verifies if record is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record | RECORD_NAME or RECORD_NAME[INDEX] | is present |</code>
     *
     * @param recordName Name of record
     * @return True, if record is located
     */
    public boolean verifyRecordIsPresent(String recordName) {
        TempoRecord.getInstance(settings).waitFor(recordName);
        return true;
    }

    /**
     * Verifies if record is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record | RECORD_NAME or RECORD_NAME[INDEX] | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify record | RECORD_NAME | is present |</code> as it will not refresh and wait.
     *
     * @param recordName Name of record
     * @return True, if record is not located
     */
    public boolean verifyRecordIsNotPresent(String recordName) {
        return TempoRecord.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), recordName);
    }

    /**
     * Clicks on the associated record view.<br>
     * <br>
     * FitNesse Example: <code>| click on record view | VIEW_NAME |</code>
     *
     * @param viewName Name of view (e.g. Summary, News, Related Actions, etc.) to click (partial names are acceptable)
     *                 If multiple views contain the same name, then the first will be selected
     */
    public void clickOnRecordView(String viewName) {
        TempoRecordView.getInstance(settings).waitFor(viewName);
        TempoRecordView.getInstance(settings).click(viewName);
    }

    /**
     * Clicks on the associated related action.<br>
     * <br>
     * FitNesse Example: <code>| click on record related action | RELATED_ACTION_NAME |</code>
     *
     * @param relatedActionName Name of related action to click (partial names are acceptable)
     *                          If multiple related actions contain the same name, then the first will be selected
     */
    public void clickOnRecordRelatedAction(String relatedActionName) {
        if (!TempoRecordRelatedAction.getInstance(settings).waitForReturn(true, relatedActionName)) {
            TempoRecordRelatedAction.getInstance(settings).click(false, "More actions");
            TempoRecordRelatedAction.getInstance(settings).waitFor(relatedActionName);
        }
        TempoRecordRelatedAction.getInstance(settings).click(relatedActionName);
    }

    /**
     * Verifies if record related action is present in the user interface. This is useful for determining if security is applied correctly.
     * <br>
     * <br>
     * FitNesse Example: <code>| verify record related action | RELATED_ACTION_NAME | is present |</code>
     *
     * @param relatedActionName Name of the related action
     * @return True, if related action is located
     */
    public boolean verifyRecordRelatedActionIsPresent(String relatedActionName) {
        TempoRecordRelatedAction.getInstance(settings).refreshAndWaitFor(relatedActionName);
        return true;
    }

    /**
     * Verifies if record related action is not present in the user interface. This is useful for determining if security is applied
     * correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify record related action | RELATED_ACTION_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify record related action | RELATED_ACTION_NAME | is present |</code> as it will not refresh
     * and wait.
     *
     * @param relatedActionName Name of related action
     * @return True, if related action is not located
     */
    public boolean verifyRecordRelatedActionIsNotPresent(String relatedActionName) {
        return TempoRecord.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), relatedActionName);
    }

    /**
     * Find a grid index
     * FitNesse Example:
     * |Find grid index|[semicolon separted list of one or more sequential headers]| ~ E.g |Find grid index|Header1;Header2;Header3|
     *
     * @param headers One or more headers, starting with the first column, which uniquely identify the target grid to be checked for matching data.
     *                Multiple hearers should be separated by a semi-colon. e.g. Column1;Column2;Column3
     *                Blank column headers Other than that in column 1 will be ignored, though be careful that some columns may have hidden headers
     *                and are not actually blank, so need to be matched.
     *                Headers which are dynamic, for example, can be 'skipped' by using a wildcard character to match them: "*"
     * @return Header index
     */
    public String getGridIndex(String headers) {
        TempoGridHeaderIndex.getInstance(settings).waitFor(headers);
        return TempoGridHeaderIndex.getInstance(settings).findGridIndex(headers);
    }

    /**
     * Sorts Record Grid view by a specific column <br>
     * <br>
     * FitNesse Example: <code>| sort record grid by column | COLUMN_NAME |</code><br>
     *
     * @param columnName Name of column
     */

    public void sortRecordGridByColumn(String columnName) {
        TempoRecordGridColumn.getInstance(settings).waitFor(columnName);
        TempoRecordGridColumn.getInstance(settings).click(columnName);
    }

    /**
     * Clicks on record grid navigation option<br>
     * <br>
     * FitNesse Example: <code>| click on record grid view navigation | NAVIGATION_OPTION |</code> Navigation option can only be "First",
     * "Previous", "Next", or "Last"<br>
     *
     * @param navOption Navigation option
     */
    public void clickOnRecordGridNavigation(String navOption) {
        TempoRecordGridNavigation.getInstance(settings).waitFor(navOption);
        TempoRecordGridNavigation.getInstance(settings).click(navOption);
    }

    /*
     * Reports
     */

    /**
     * Clicks on the associated report.<br>
     * <br>
     * FitNesse Example: <code>| click on report | REPORT_NAME or REPORT_NAME[INDEX] |</code>
     *
     * @param reportName Name of report to click (partial names are acceptable)
     *                   If multiple reports contain the same name the first will be selected
     */
    public void clickOnReport(String reportName) {
        TempoReport.getInstance(settings).waitFor(reportName);
        TempoReport.getInstance(settings).click(reportName);
    }

    /**
     * Verifies if report is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify report | REPORT_NAME or REPORT_NAME[INDEX] | is present |</code>
     *
     * @param reportName Name of the report
     * @return True, if report is located
     */
    public boolean verifyReportIsPresent(String reportName) {
        TempoReport.getInstance(settings).waitFor(reportName);
        return true;
    }

    /**
     * Verifies if report is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify report | REPORT_NAME or REPORT_NAME[INDEX] | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify report | REPORT_NAME | is present |</code> as it will not refresh and wait.
     *
     * @param reportName Name of the report
     * @return True, if report is not located
     */
    public boolean verifyReportIsNotPresent(String reportName) {
        TempoReport.getInstance(settings).waitForReturn(settings.getNotPresentTimeoutSeconds(), reportName);
        return true;
    }

    /*
     * Actions
     */

    /**
     * Clicks on the associated action.<br>
     * <br>
     * FitNesse Example: <code>| click on action | ACTION_NAME or ACTION_NAME[INDEX] |</code>
     *
     * @param actionName Name or Name and index of action to click (partial names are acceptable)
     *                   If multiple actions contain the same name the first will be selected
     */
    public void clickOnAction(String actionName) {
        TempoAction.getInstance(settings).waitFor(actionName);
        TempoAction.getInstance(settings).click(actionName);
    }

    /**
     * Toggles star on the associated action.<br>
     * <br>
     * FitNesse Example <code>| star action | ACTION_NAME |</code>
     *
     * @param actionName Name of action to star.
     */
    public void starAction(String actionName) {
        TempoAction.getInstance(settings).waitFor(actionName);
        TempoActionStarAction.getInstance(settings).click(actionName);
    }

    /**
     * Returns true if the 'Action Completed successfully' is currently being displayed in the interface.<br>
     * This only applies to Appian 7.11 and below <br>
     * FitNesse Example: <code>| verify action completed |</code>
     *
     * @return True if the 'Action Completed successfully' is currently being displayed in the interface.
     * @deprecated
     */
    @Deprecated
    public boolean verifyActionCompleted() {
        TempoAction.getInstance(settings).complete();
        return true;
    }

    /**
     * Verifies if action is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify action | ACTION_NAME | is present |</code>
     *
     * @param actionName Name of the action
     * @return True, if action is located
     */
    public boolean verifyActionIsPresent(String actionName) {
        TempoAction.getInstance(settings).waitFor(actionName);
        return true;
    }

    /**
     * Verifies if action is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify action | ACTION_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify action | ACTION_NAME | is present |</code> as it will not refresh and wait.
     *
     * @param actionName Name of the action
     * @return True, if action is not located
     */
    public boolean verifyActionIsNotPresent(String actionName) {
        return TempoAction.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), actionName);
    }

    /**
     * Clicks on an actions application filter.<br>
     * <br>
     * FitNesse Example: <code>| click on application filter | APP_FILTER |</code>
     *
     * @param appFilter App Filter to click (partial names are acceptable)
     *                  If multiple application filters contain the same name, then the first will be selected
     */
    public void clickOnApplicationFilter(String appFilter) {
        TempoActionApplicationFilter.getInstance(settings).waitFor(appFilter);
        TempoActionApplicationFilter.getInstance(settings).click(appFilter);
    }

    /**
     * Verifies if action is present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify application filter| APPLICATION_NAME | is present |</code>
     *
     * @param applicationName Name of the action
     * @return True, if action is located
     */
    public boolean verifyApplicationFilterIsPresent(String applicationName) {
        TempoAction.getInstance(settings).waitFor(applicationName);
        return true;
    }

    /**
     * Verifies if action is not present in the user interface. This is useful for determining if security is applied correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify application filter| APPLICATION_NAME | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify application filter | APPLICATION_NAME | is present |</code> as it will not refresh and
     * wait.
     *
     * @param applicationName Name of the action
     * @return True, if action is not located
     */
    public boolean verifyApplicationFilterIsNotPresent(String applicationName) {
        return TempoAction.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), applicationName);
    }

    /*
     * Interfaces
     */

    /**
     * Returns the title of the form.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get form title |</code> - Simply returns a string<br>
     * <code>| $title= | get form title | </code> - Stores the title in titleVariable, which can later be accessed using
     * $title<br>
     * <code>| check | get form title | FORM_TITLE |</code> - Returns true if form title matches FORM_TITLE input
     *
     * @return The title string
     */
    public String getFormTitle() {
        TempoFormTitle.getInstance(settings).waitFor();
        return TempoFormTitle.getInstance(settings).capture();
    }

    /**
     * Returns a string that matches the regex, this could be useful in extracting a system generated value from a form's title.<br>
     * <br>
     * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from form title |</code>
     *
     * @param regex Regular expression string to search for within the form
     * @param group Regular expression group to return
     * @return String that matches the regular expression
     */
    public String getRegexGroupFromFormTitle(String regex, Integer group) {
        TempoFormTitle.getInstance(settings).waitFor();
        return TempoFormTitle.getInstance(settings).regexCapture(regex, group);
    }

    /**
     * Returns the instructions of the form.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get form instructions |</code> - Simply returns a string<br>
     * <code>| $inst= | get form instructions | </code> - Stores the title in instructionsVariable, which can later be
     * accessed using $inst<br>
     * <code>| check | get form instructions | FORM_INSTRUCTIONS |</code> - Returns true if form instructions matches FORM_INSTRUCTIONS input
     *
     * @return The instructions string
     */
    public String getFormInstructions() {
        TempoFormInstructions.getInstance(settings).waitFor();
        return TempoFormInstructions.getInstance(settings).capture();
    }

    /**
     * Populates a field with specific values.<br>
     * <br>
     * FitNesse Example: <code>| populate field | FIELD_LABEL or [INDEX] or FIELD_LABEL[INDEX] | with | VALUE(S) |</code>
     *
     * @param fieldName   Can either be the label of the field or a label with an index
     * @param fieldValues An array of strings containing the values to populate into the interface
     */
    public void populateFieldWith(String fieldName, String[] fieldValues) {
        TempoField.getInstance(settings).waitFor(fieldName);
        TempoFieldFactory.getInstance(settings).populateMultiple(fieldValues, fieldName);
    }

    public void populatingFieldWith(String fieldName, String[] fieldValues) {
        TempoField.getInstance(settings).waitFor(fieldName);
        TempoFields2Factory.getInstance(settings).populateMultiple(fieldValues, fieldName);
    }

    public void populatesFieldWith(String fieldName, String[] fieldValues) {
        TempoField.getInstance(settings).waitFor(fieldName);
        TempoFieldsFactory.getInstance(settings).populateMultiple(fieldValues, fieldName);
    }

    /**
     * Populates a picker field with inputs that display suggestions that aren't an exact match of the inputs. This is useful when
     * selecting a picker suggestion that only partially contains the input value.<br>
     * <br>
     * FitNesse Example: <code>| populate field | FIELD_LABEL or [INDEX] or FIELD_LABEL[INDEX] | with | VALUE(S) |
     * partially matching picker field suggestion</code>
     * <br>
     *
     * @param fieldName   Can either be the label of the field or a label with an index
     * @param fieldValues An array of strings containing the values to populate into the interface
     * @deprecated
     */
    @Deprecated
    public void populateFieldWithPartiallyMatchingPickerFieldSuggestion(String fieldName, String[] fieldValues) {
        populateFieldWithContains(fieldName, fieldValues);
    }

    /**
     * Populates a picker field with inputs that display suggestions that aren't an exact match of the inputs. This is useful when
     * selecting a picker suggestion that only partially contains the input value.<br>
     * <br>
     * FitNesse Example: <code>| populate field | FIELD_LABEL or [INDEX] or FIELD_LABEL[INDEX] | with | VALUE(S) |
     * partially matching picker field suggestion</code>
     * <br>
     *
     * @param fieldName   Can either be the label of the field or a label with an index
     * @param fieldValues An array of strings containing the values to populate into the interface
     */
    public void populateFieldWithContains(String fieldName, String[] fieldValues) {
        TempoField.getInstance(settings).waitFor(fieldName);
        TempoFieldFactory.getInstance(settings).populateMultiple(fieldValues, true, fieldName);
    }

    /**
     * Populates a field of a particular type with specific values.<br>
     * <br>
     * FitNesse Example: <code>| populate | FIELD_TYPE | field | [FIELD_INDEX] | with | VALUE(S) ||</code><br>
     *
     * @param fieldType   Can only currently accept TEXT, PARAGRAPH, FILE_UPLOAD, MILESTONE and PICKER
     * @param fieldName   Can either be the label of the field or a label with an index
     * @param fieldValues An array of strings containing the values to populate into the interface
     */
    public void populateFieldWith(String fieldType, String fieldName, String[] fieldValues) {
        TempoFieldFactory.getInstance(settings).waitFor(fieldType, fieldName);
        TempoFieldFactory.getInstance(settings).populateMultiple(fieldValues, fieldType, fieldName);
    }

    /**
     * Populates a field with a single value that may contain a comma. This is useful is selecting an option that contains a comma.<br>
     * <br>
     * FitNesse Example: <code>| populate field | FIELD_LABEL or [INDEX] or FIELD_LABEL[INDEX] | with value | VALUE |</code>
     *
     * @param fieldName  Field label or label and index
     * @param fieldValue Field value, can contain a comma.
     */
    public void populateFieldWithValue(String fieldName, String fieldValue) {
        populateFieldWith(fieldName, new String[] {fieldValue});
    }

    /**
     * Populates a field in a section with specific values.<br>
     * <br>
     * FitNesse Example: <code>| populate field | FIELD_LABEL OR [FIELD_INDEX] | in section | SECTION_NAME | with | VALUE(S) |</code> <br>
     *
     * @param fieldName   Can either be the label of the field or label with an index
     * @param sectionName Can either be the label of the section or an label with an index
     * @param fieldValues An array of strings containing the values to populate into the interface
     */
    public void populateFieldInSectionWith(String fieldName, String sectionName, String[] fieldValues) {
        TempoSection.getInstance(settings).waitFor(fieldName, sectionName);
        TempoSectionField.getInstance(settings).populateMultiple(fieldValues, fieldName, sectionName);
    }

    /**
     * Populates a field with placeholder with specific values.<br>
     * <br>
     * FitNesse Example: <code>| populate field with placeholder | PLACEHOLDER | with | VALUE(S) |</code> <br>
     *
     * @param placeholder Placeholder text in an input
     * @param fieldValues An array of strings containing the values to populate into the interface
     */
    public void populateFieldWithPlaceholderWith(String placeholder, String[] fieldValues) {
        TempoFieldFactory.getInstance(settings).waitFor(Constants.FIELD_LOCATOR_PLACEHOLDER + placeholder);
        TempoFieldFactory.getInstance(settings)
                .populateMultiple(fieldValues, false, Constants.FIELD_LOCATOR_PLACEHOLDER + placeholder);
    }

    /**
     * Populates a field with instructions with specific values.<br>
     * <br>
     * FitNesse Example: <code>| populate field with instructions | INSTRUCTIONS | with | VALUE(S) |</code> <br>
     *
     * @param instructions Instructions for an input
     * @param fieldValues  An array of strings containing the values to populate into the interface
     */
    public void populateFieldWithInstructionsWith(String instructions, String[] fieldValues) {
        TempoFieldFactory.getInstance(settings).waitFor(Constants.FIELD_LOCATOR_INSTRUCTIONS + instructions);
        TempoFieldFactory.getInstance(settings)
                .populateMultiple(fieldValues, false, Constants.FIELD_LOCATOR_INSTRUCTIONS + instructions);
    }

    /**
     * Populates a field with tooltip with specific values.<br>
     * <br>
     * FitNesse Example: <code>| populate field with tooltip | TOOLTIP | with | VALUE(S) |</code> <br>
     *
     * @param tooltip     Tooltip for an input
     * @param fieldValues An array of strings containing the values to populate into the interface
     */
    public void populateFieldWithTooltipWith(String tooltip, String[] fieldValues) {
        TempoFieldFactory.getInstance(settings).waitFor(Constants.FIELD_LOCATOR_TOOLTIP + tooltip);
        TempoFieldFactory.getInstance(settings)
                .populateMultiple(fieldValues, false, Constants.FIELD_LOCATOR_TOOLTIP + tooltip);
    }

    /**
     * Selects a value in a search dropdown.<br>
     * <br>
     * FitNesse Example: <code>| populate dropdown | FIELD_LABEL or [INDEX] or FIELD_LABEL[INDEX] | search box with | VALUE |</code>
     *
     * @param fieldName   Can either be the label of the dropdown or a label with an index
     * @param searchValue A string to populate the search box with and select from the dropdown
     */
    public void populateDropdownSearchBoxWith(String fieldName, String searchValue) {
        TempoDropdownSearchBox.getInstance(settings).waitFor(fieldName);
        TempoDropdownSearchBox.getInstance(settings).populate(fieldName, searchValue);
    }

    /**
     * Expand a section that is hidden.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| expand section | SECTION_NAME |</code><br>
     *
     * @param sectionName Label of the section
     * @deprecated
     */
    @Deprecated
    public void expandSection(String sectionName) {
        TempoSectionToggle.getInstance(settings).waitFor(sectionName);
        TempoSectionToggle.getInstance(settings).click(sectionName);
    }

    /**
     * Collapse a section that is visible.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| collapse section | SECTION_NAME |</code><br>
     *
     * @param sectionName Label of the section
     * @deprecated
     */
    @Deprecated
    public void collapseSection(String sectionName) {
        TempoSectionToggle.getInstance(settings).waitFor(sectionName);
        TempoSectionToggle.getInstance(settings).click(sectionName);
    }

    /**
     * Expand or collapse a section.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| toggle section | SECTION_NAME | visibility |</code><br>
     *
     * @param sectionName Label of the section
     */
    public void toggleSectionVisibility(String sectionName) {
        TempoSectionToggle.getInstance(settings).waitFor(sectionName);
        TempoSectionToggle.getInstance(settings).click(sectionName);
    }

    /**
     * Expand or collapse a box.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| toggle box | BOX_NAME or [BOX_INDEX] or BOX_NAME[INDEX] | visibility |</code><br>
     *
     * @param boxName Label of the box
     */
    public void toggleBoxVisibility(String boxName) {
        TempoBox.getInstance(settings).waitFor(boxName);
        TempoBox.getInstance(settings).click(boxName);
    }

    /**
     * Used to clear a field.<br>
     * <br>
     * This method currently works for text, integer paragraph, dropdown, file upload, datetime, date, picker<br>
     * FitNesse Example: <code>| clear field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] |</code><br>
     *
     * @param fieldName Field to clear
     */
    public void clearField(String fieldName) {
        TempoField.getInstance(settings).waitFor(fieldName);
        TempoFieldFactory.getInstance(settings).clear(fieldName);
    }

    /**
     * Used to clear a field of specific values.<br>
     * <br>
     * This method is only useful for picker objects to unselect an item. <br>
     * FitNesse Example: <code>| clear field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | of | VALUES |</code><br>
     *
     * @param fieldName   Field to clear
     * @param fieldValues Values to unselect
     */
    public void clearFieldOf(String fieldName, String[] fieldValues) {
        TempoField.getInstance(settings).waitFor(fieldName);
        TempoFieldFactory.getInstance(settings).clearOf(fieldValues, fieldName);
    }

    /**
     * Verifies a tag field is present<br>
     * <br>
     * FitNesse Example: <code>| verify tag field | TAG_FIELD or TAG_FIELD[INDEX] | is present |</code>
     *
     * @param tagField Name or Name[Index] of tag field
     * @return Presence of tag field
     */
    public boolean verifyTagFieldIsPresent(String tagField) {
        return TempoTagField.getInstance(settings).waitForReturn(true, tagField);
    }

    /**
     * Verifies a tag item is present<br>
     * <br>
     * FitNesse Example: <code>| verify tag item | TAG_ITEM or TAG_ITEM[INDEX] | is present |</code>
     *
     * @param tagItem Name or Name[Index] of tag item
     * @return Presence of tag item
     */
    public boolean verifyTagItemIsPresent(String tagItem) {
        return TempoTagField.getInstance(settings).waitForTagItemReturn(tagItem);
    }

    /**
     * Clicks on a tag field's tag item<br>
     * <br>
     * FitNesse Example: <code>| click on tag field | TAG_FIELD or TAG_FIELD[INDEX] | tag item |TAG_ITEM or TAG_ITEM[INDEX] |</code>
     *
     * @param tagField Name or Name[Index] of tag field
     * @param tagItem  Name or Name[Index] of tag item
     */
    public void clickOnTagFieldTagItem(String tagField, String tagItem) {
        TempoTagField.getInstance(settings).waitFor(tagField);
        TempoTagField.getInstance(settings).clickOnTagFieldTagItem(tagField, tagItem);
    }


    /**
     * Clicks on a tag item<br>
     * <br>
     * FitNesse Example: <code>| click on tag item |TAG_ITEM or TAG_ITEM[INDEX] |</code>
     *
     * @param tagItem Name or Name[Index] of tag item
     */
    public void clickOnTagItem(String tagItem) {
        TempoTagField.getInstance(settings).waitForAbsoluteTagItem(tagItem);
        TempoTagField.getInstance(settings).clickOnTagItem(tagItem);
    }

    /**
     * Returns the value of a field.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | value |</code> - Simply returns a string<br>
     * <code>| $fieldValue= | get field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | value | </code> - Stores the field value in
     * fieldValue, which can later be accessed
     * using $fieldValue<br>
     * <code>| check | get field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | FIELD_VALUE |</code> - Returns true if the field value
     * title matches the FIELD_VALUE input.
     * For file upload fields, do not include the full path. This will not work for relative date and datetime fields.
     * Image fields return the alt text of the image.
     *
     * @param fieldName Name of name and index of the field
     * @return The field value
     */
    public String getFieldValue(String fieldName) {
        TempoField.getInstance(settings).waitFor(fieldName);
        return TempoFieldFactory.getInstance(settings).capture(fieldName);
    }

    /**
     * Returns the value of a field using a placeholder.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get field with placeholder | PLACEHOLDER | value |</code><br>
     *
     * @param placeholder Value for the placeholder
     * @return The field value
     */
    public String getFieldWithPlaceholderValue(String placeholder) {
        TempoField.getInstance(settings).waitFor(Constants.FIELD_LOCATOR_PLACEHOLDER + placeholder);
        return TempoFieldFactory.getInstance(settings).capture(Constants.FIELD_LOCATOR_PLACEHOLDER + placeholder);
    }

    /**
     * Returns the value of a field using instructions.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get field with instructions | INSTRUCTIONS | value |</code><br>
     *
     * @param instructions Value for the instructions
     * @return The field value
     */
    public String getFieldWithInstructionsValue(String instructions) {
        TempoField.getInstance(settings).waitFor(Constants.FIELD_LOCATOR_INSTRUCTIONS + instructions);
        return TempoFieldFactory.getInstance(settings).capture(Constants.FIELD_LOCATOR_INSTRUCTIONS + instructions);
    }

    /**
     * Returns the value of a field using a tooltip<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get field with tooltip | TOOLTIP | value |</code><br>
     *
     * @param tooltip Value for the tooltip
     * @return The field value
     */
    public String getFieldWithTooltipValue(String tooltip) {
        TempoField.getInstance(settings).waitFor(Constants.FIELD_LOCATOR_TOOLTIP + tooltip);
        return TempoFieldFactory.getInstance(settings).capture(Constants.FIELD_LOCATOR_TOOLTIP + tooltip);
    }

    /**
     * Returns a string that matches the regex, this could be useful in extracting a system generated value from a field's value.<br>
     * <br>
     * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from field | FIELD_NAME | value |</code>
     *
     * @param regex     Regular expression string to search for within the form
     * @param group     Regular expression group to return
     * @param fieldName Name of field
     * @return String that matches the regular expression
     */
    public String getRegexGroupFromFieldValue(String regex, Integer group, String fieldName) {
        TempoField.getInstance(settings).waitFor(fieldName);
        return TempoFieldFactory.getInstance(settings).regexCapture(regex, group, fieldName);

    }

    /**
     * Returns the value of a field in a section.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get field | FIELD_LABEL or [FIELD_INDEX] | in section | SECTION_NAME | value |</code> - Simply returns a string<br>
     * <code>| $fieldValue= | get field | FIELD_LABEL or [FIELD_INDEX] | in section | SECTION_NAME | value |</code> - Stores the field value
     * in fieldValue,
     * which can later be accessed using $fieldValue<br>
     * <code>| check | get field | FIELD_LABEL or [FIELD_INDEX] | in section | SECTION_NAME | value | FIELD_VALUE |</code> - Returns true if
     * the field value
     * title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will not work for relative date and
     * datetime fields.
     *
     * @param fieldName   Field label or label and index
     * @param sectionName Section label or label and index
     * @return The field value
     */
    public String getFieldInSectionValue(String fieldName, String sectionName) {
        TempoSection.getInstance(settings).waitFor(fieldName, sectionName);
        return TempoSectionField.getInstance(settings).capture(fieldName, sectionName);
    }

    /**
     * Returns the values in a card as a json object.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>This is not for use with FitNesse</code>
     * This will not support a card with duplicate labels
     *
     * @param cardName Accessibility text for card
     * @return JSON string of label:values
     */
    public JSONObject getCardContents(String cardName) {
        TempoCard.getInstance(settings).waitFor(cardName);
        return TempoCard.getInstance(settings).getContents(cardName);
    }

    /**
     * Returns the values in a section as a json object.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>This is not for use with FitNesse</code>
     * This will not support a section with duplicate labels
     *
     * @param sectionName Label text for the section
     * @return JSON object of label:values
     */
    public JSONObject getSectionContents(String sectionName) {
        TempoSection.getInstance(settings).waitFor(sectionName);
        return TempoSection.getInstance(settings).getContents(sectionName);
    }

    /**
     * Returns the values in a box as a json object.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>This is not for use with FitNesse</code>
     * This will not support a box with duplicate labels
     *
     * @param boxName Label text for the box
     * @return JSON object of label:values
     */
    public JSONObject getBoxContents(String boxName) {
        TempoBox.getInstance(settings).waitFor(boxName);
        return TempoBox.getInstance(settings).getContents(boxName);
    }

    /**
     * Returns the values in a section as a json object.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>This is not for use with FitNesse</code>
     * This will not support a grid with duplicate labels
     *
     * @param gridName Name of the grid using label or index
     * @return JSON object of label:values
     */
    public JSONObject getGridContents(String gridName) {
        TempoGrid.getInstance(settings).waitFor(gridName);
        return TempoGrid.getInstance(settings).getContents(gridName);
    }

    /**
     * Returns a string that matches the regex, this could be useful in extracting a system generated value from a field's value in a
     * specified section.<br>
     * <br>
     * FitNesse Example: <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from field | FIELD_NAME | in section | SECTION_NAME | </code>
     *
     * @param regex       Regular expression string to search for within the form
     * @param group       Regular expression group to return
     * @param fieldName   Name of field
     * @param sectionName Section label or label and index
     * @return String that matches the regular expression
     */
    public String getRegexGroupFromFieldInSectionValue(String regex, Integer group, String fieldName,
                                                       String sectionName) {
        TempoSectionField.getInstance(settings).waitFor(fieldName, sectionName);
        return TempoSectionField.getInstance(settings).regexCapture(regex, group, fieldName, sectionName);
    }

    /**
     * Verifies there is a confirmation dialog with the specified header.
     * The method will wait for the timeout period before failing.<br>
     * <br>
     * FitNesse Example: <code>| verify confirmation dialog header | HEADER_TEXT | is present |</code>
     *
     * @param headerText Value of the header
     * @return True, if the confirmation dialog is located with the specified header text
     */
    public boolean verifyConfirmationDialogHeaderIsPresent(String headerText) {
        TempoConfirmationDialog.getInstance(settings).waitFor("header", headerText);
        return true;
    }

    /**
     * Verifies there is a confirmation dialog with the specified message.
     * The method will wait for the timeout period before failing.<br>
     * <br>
     * FitNesse Example: <code>| verify confirmation dialog message | MESSAGE_TEXT | is present |</code>
     *
     * @param messageText Value of the message
     * @return True, if the confirmation dialog is located with the specified message text
     */
    public boolean verifyConfirmationDialogMessageIsPresent(String messageText) {
        TempoConfirmationDialog.getInstance(settings).waitFor("message", messageText);
        return true;
    }

    /**
     * Verifies a field contains a specific value.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| verify field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | contains | VALUES() |</code> - For
     * date and datetime fields, relative times can be entered such as +1 minute, +2 hours, +3 days. To use these relative times, the
     * startDatetime must be initialized: see {@link BaseFixture#setStartDatetime()} Image fields
     * return the alt text of the image.
     *
     * @param fieldName   Can either be a name or a name and index, e.g. 'Text Field' or 'Text Field[1]'
     * @param fieldValues Values to compare field value against
     * @return True, if the field contains the value
     */
    public boolean verifyFieldContains(String fieldName, String[] fieldValues) {
        TempoField.getInstance(settings).waitFor(fieldName);
        return TempoFieldFactory.getInstance(settings).containsMultiple(fieldValues, fieldName);
    }

    /**
     * Verifies a field with a single value that may contain a comma. This is useful in verifying that an option that contains a comma has
     * been selected.<br>
     * <br>
     * FitNesse Example: <code>| verify field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | contains value | VALUE |</code>
     *
     * @param fieldName  Field label or label and index
     * @param fieldValue Field value, can contain a comma.
     * @return True, if fields contains value
     */
    public boolean verifyFieldContainsValue(String fieldName, String fieldValue) {
        return verifyFieldContains(fieldName, new String[] {fieldValue});
    }

    /**
     * Verifies a field is not blank.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| verify field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | is not blank|</code>
     *
     * @param fieldName Can either be a name or or field index or a name and index, e.g. 'Text Field' or 'Text Field[1]'
     * @return True, if the field is not blank
     */
    public boolean verifyFieldIsNotBlank(String fieldName) {
        TempoField.getInstance(settings).waitFor(fieldName);
        return TempoFieldFactory.getInstance(settings).isNotBlank(fieldName);
    }

    /**
     * Verifies a field contains a specific value.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| verify field | FIELD_NAME or [FIELD_INDEX] | in section | SECTION_NAME | contains | VALUES |</code><br>
     * <code>| verify field | FIELD_NAME or [FIELD_INDEX] | in section | SECTION_NAME | contains | +4 hours |</code> - For date and datetime
     * fields, relative
     * times can be entered such as +1 minute, +2 hours, +3 days. To use these relative times, the startDatetime must be initialized: see
     * {@link BaseFixture#setStartDatetime()}
     *
     * @param fieldName   Can either be a field label or a label and index, e.g. 'Text Field' or 'Text Field[2]'
     * @param sectionName Can either be a section label or a label and index, e.g. 'Section Name' or 'Section Name[2]'
     * @param fieldValues Values to compare field value against
     * @return True, if the field contains the value
     */
    public boolean verifyFieldInSectionContains(String fieldName, String sectionName, String[] fieldValues) {
        TempoSection.getInstance(settings).waitFor(fieldName, sectionName);
        return TempoSectionField.getInstance(settings).containsMultiple(fieldValues, fieldName, sectionName);
    }

    /**
     * Verifies a field with a placeholder contains a specific value.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| verify field with placeholder | PLACEHOLDER | contains | VALUES |</code><br>
     *
     * @param placeholder Value for the placeholder
     * @param fieldValues Values to compare field value against
     * @return True, if the field contains the value
     */
    public boolean verifyFieldWithPlaceholderContains(String placeholder, String[] fieldValues) {
        TempoField.getInstance(settings).waitFor(Constants.FIELD_LOCATOR_PLACEHOLDER + placeholder);
        return TempoFieldFactory.getInstance(settings)
                .containsMultiple(fieldValues, Constants.FIELD_LOCATOR_PLACEHOLDER + placeholder);
    }

    /**
     * Verifies a field with instructions contains a specific value.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| verify field with instructions | INSTRUCTIONS | contains | VALUES |</code><br>
     *
     * @param instructions Value for the instructions
     * @param fieldValues  Values to compare field value against
     * @return True, if the field contains the value
     */
    public boolean verifyFieldWithInstructionsContains(String instructions, String[] fieldValues) {
        TempoField.getInstance(settings).waitFor(Constants.FIELD_LOCATOR_INSTRUCTIONS + instructions);
        return TempoFieldFactory.getInstance(settings)
                .containsMultiple(fieldValues, Constants.FIELD_LOCATOR_INSTRUCTIONS + instructions);
    }

    /**
     * Verifies a field with a tooltip contains a specific value.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| verify field with tooltip | TOOLTIP | contains | VALUES |</code><br>
     *
     * @param tooltip     Value for the tooltip
     * @param fieldValues Values to compare field value against
     * @return True, if the field contains the value
     */
    public boolean verifyFieldWithTooltipContains(String tooltip, String[] fieldValues) {
        TempoField.getInstance(settings).waitFor(Constants.FIELD_LOCATOR_TOOLTIP + tooltip);
        return TempoFieldFactory.getInstance(settings)
                .containsMultiple(fieldValues, Constants.FIELD_LOCATOR_TOOLTIP + tooltip);
    }

    /**
     * Verifies a field contains a validation message.<br>
     * <br>
     * FitNesse Example: <code>| verify field | FIELD_LABEL or [FIELD_INDEX] or FIELD_LABEL[INDEX] | contains validation messages |</code>
     *
     * @param fieldName          Field label or label and index
     * @param validationMessages Validation messages, separated by a comma.
     * @return True, if fields contains value
     */
    public boolean verifyFieldContainsValidationMessage(String fieldName, String[] validationMessages) {
        TempoFieldValidation.getInstance(settings).waitForMultiple(validationMessages, fieldName);
        return true;
    }

    /**
     * Returns the validation message from a field.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get field | FIELD_NAME | validation message |</code> - Simply returns a string<br>
     * <code>| $error= | get field | FIELD_NAME | validation message | </code> - Stores the validation message in error, which can later be
     * accessed using $error<br>
     * <code>| check | get field | FIELD_NAME | validation message | VALIDATION_MESSAGE |</code> - Returns true if the validation message
     * matches the VALIDATION_MESSAGE input.
     *
     * @param fieldName Name of name and index of the field
     * @return Validation message
     */
    public String getFieldValidationMessage(String fieldName) {
        TempoField.getInstance(settings).waitFor(fieldName);
        return TempoFieldValidation.getInstance(settings).capture(fieldName);
    }

    /**
     * Verifies if field is displayed in the interface. This is useful to test dynamic forms.<br>
     * <br>
     * FitNesse Example: <code>| verify field | FIELD_LABEL or [FIELD_INEDX] or FIELD_LABEL[INDEX] | is present |</code>
     *
     * @param fieldName Field name to find in interface
     * @return True, if field is located in the interface
     */
    public boolean verifyFieldIsPresent(String fieldName) {
        TempoField.getInstance(settings).waitFor(fieldName);
        return true;
    }

    /**
     * Verifies if field is not displayed in the interface. This is useful to test dynamic forms.<br>
     * <br>
     * FitNesse Example: <code>| verify field | FIELD_LABEL or [FIELD_INEDX] or FIELD_LABEL[INDEX] | is not present |</code>
     *
     * @param fieldName Field name to not find in interface
     * @return True, if field is not located in the interface
     */
    public boolean verifyFieldIsNotPresent(String fieldName) {
        return TempoField.getInstance(settings).waitForReturn(false, settings.getNotPresentTimeoutSeconds(), fieldName);
    }

    /**
     * Populates fields in a grid. This method is useful for populating the following types of fields: Text, Paragraph, EncryptedText,
     * Integer, Decimal, Date, Datetime, Select, MultipleSelect, Radio, Checkbox, FileUpload, UserPicker, GroupPicker, UserGroupPicker,
     * DocumentPicker, FolderPicker, DocumentFolderPicker, CustomPicker.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| populate grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] | with | VALUE |</code>
     * <br>
     * <code>| populate grid | [1] | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | with | VALUE |</code> - If the grid does not have a
     * title, an index can be used to select the grid, e.g. [1] would be the first grid in the interface.<br>
     * <code>| populate grid | GRID_NAME | column | [2] | row | ROW_INDEX | with | VALUE |</code> - The column index can be used if the
     * columns do not have titles. As with Appian indexing starts with [1] and if the table has checkboxes, they are considered to be in the
     * first column.<br>
     * <code>| populate grid | GRID_NAME[3] | column | COLUMN_NAME | row | ROW_INDEX | with | VALUE |</code> - If there are multiple grids
     * with the same name then use an index, e.g. 'Grid Name[3]' with select the third grid with the name of 'Grid Name' in the interface
     *
     * @param gridName    Name of grid
     * @param columnName  Name or index of the column
     * @param rowNum      Index of the row
     * @param fieldValues Values to populate
     */
    public void populateGridColumnRowWith(String gridName, String columnName, String rowNum, String[] fieldValues) {
        TempoGridCell.getInstance(settings).waitFor(gridName, columnName, rowNum);
        TempoGridCell.getInstance(settings).populateMultiple(fieldValues, gridName, columnName, rowNum);
    }

    /**
     * Populates a picker field in a grid with inputs that display suggestions that aren't an exact match of the inputs. This is useful when
     * selecting a picker suggestion that only partially contains the input value.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| populate grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] | with | VALUE |
     * partially matching picker field suggestion </code>
     * <br>
     * <code>| populate grid | [1] | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | with | VALUE | partially matching picker field suggestion </code>
     * - If the grid does not have a title, an index can be used to select the grid, e.g. [1] would be the first grid in the interface.<br>
     * <code>| populate grid | GRID_NAME | column | [2] | row | ROW_INDEX | with | VALUE | partially matching picker field suggestion </code> - The column
     * index can be used if the columns do not have titles. As with Appian indexing starts with [1] and if the table has checkboxes, they are considered
     * to be in the first column.<br>
     * <code>| populate grid | GRID_NAME[3] | column | COLUMN_NAME | row | ROW_INDEX | with | VALUE | partially matching picker field suggestion </code> -
     * If there are multiple grids with the same name then use an index, e.g. 'Grid Name[3]' with select the third grid with the name of 'Grid Name' in
     * the interface
     *
     * @param gridName    Name of grid
     * @param columnName  Name or index of the column
     * @param rowNum      Index of the row
     * @param fieldValues Values to populate
     */
    public void populateGridColumnRowWithPartiallyMatchingPickerFieldSuggestion(String gridName, String columnName,
                                                                                String rowNum, String[] fieldValues) {
        TempoGridCell.getInstance(settings).waitFor(gridName, columnName, rowNum);
        TempoGridCell.getInstance(settings).populateMultiple(fieldValues, true, gridName, columnName, rowNum);
    }

    /**
     * Populates a grid field with a single value that may contain a comma. This is useful is selecting an option that contains a comma.<br>
     * <br>
     * FitNesse Example:
     * <code>populate grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] | with value | VALUE |</code>
     *
     * @param gridName   Name of grid
     * @param columnName Name or index of the column
     * @param rowNum     Index of the row
     * @param fieldValue Value to populate
     */
    public void populateGridColumnRowWithValue(String gridName, String columnName, String rowNum, String fieldValue) {
        populateGridColumnRowWith(gridName, columnName, rowNum, new String[] {fieldValue});
    }

    /**
     * Used to clear a field in a grid<br>
     * <br>
     * This method currently works for <br>
     * FitNesse Example:
     * <code>clear grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] |</code><br>
     *
     * @param gridName   Name of grid
     * @param columnName Name or index of the column
     * @param rowNum     Index of the row
     */
    public void clearGridColumnRow(String gridName, String columnName, String rowNum) {
        TempoGridCell.getInstance(settings).waitFor(gridName, columnName, rowNum);
        TempoGridCell.getInstance(settings).clear(gridName, columnName, rowNum);
    }

    /**
     * Clicks a grid cell. This is useful for deleting grid rows.<br>
     * <br>
     * FitNesse Example:
     * <code>| click on grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] |</code>
     *
     * @param gridName   Name of grid
     * @param columnName Name or index of the column
     * @param rowNum     Index of the row
     */
    public void clickOnGridColumnRow(String gridName, String columnName, String rowNum) {
        TempoGridCell.getInstance(settings).waitFor(gridName, columnName, rowNum);
        TempoGridCell.getInstance(settings).click(gridName, columnName, rowNum);
    }

    /**
     * Returns the value of a field.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] | value |</code>
     * - Simply returns a string<br>
     * <code>| $fieldValue= | get grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | value | </code> - Stores
     * the field value in fieldValue, which can later be accessed using $fieldValue<br>
     * <code>| check | get grid | GRID_NAME_OR_INDEX | column | COLUMN_NAME_OR_INDEX | row | ROW_INDEX | value | FIELD_VALUE |</code> -
     * Returns true if the field value title matches the FIELD_VALUE input. For file upload fields, do not include the full path. This will
     * not work for relative date and datetime fields. Image field cells return the alt text of the image.
     *
     * @param gridName   Name of the grid
     * @param columnName Name or index of the column
     * @param rowNum     Index of the row
     * @return The field value
     */
    public String getGridColumnRowValue(String gridName, String columnName, String rowNum) {
        TempoGridCell.getInstance(settings).waitFor(gridName, columnName, rowNum);
        return TempoGridCell.getInstance(settings).capture(gridName, columnName, rowNum);
    }

    /**
     * Returns a string that matches the regex, this could be useful in extracting a system generated value from a specific grid cell.<br>
     * <br>
     * FitNesse Example:
     * <code>| get regex | [A-z]{3}-[0-9]{4} | group | GROUP | from grid | GRID_NAME | cell in column | COLUMN_NAME_OR_INDEX | Row | [Row_Number] | value | </code>
     *
     * @param regex      Regular expression string to search for within the form
     * @param group      Regular expression group to return
     * @param gridName   Name of grid
     * @param columnName Name or index of column to retrieve the cell from
     * @param rowNum     Index of column to retrieve the cell from
     * @return String that matches the regular expression
     */
    public String getRegexGroupFromGridColumnRowValue(String regex, Integer group, String gridName, String columnName,
                                                      String rowNum) {
        TempoGrid.getInstance(settings).waitFor(gridName, columnName, rowNum);
        return TempoGridCell.getInstance(settings).regexCapture(regex, group, gridName, columnName, rowNum);
    }

    /**
     * Verifies a field contains a specific value.<br>
     * <br>
     * FitNesse Example:
     * <code>| verify grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] | contains | VALUES |</code>
     * Image field cells return the alt text of the image.
     *
     * @param gridName    Name of the grid
     * @param columnName  Name or index of the column
     * @param rowNum      Index of the row
     * @param fieldValues Field values to compare against
     * @return True, if the field contains the value
     */
    public boolean verifyGridColumnRowContains(String gridName, String columnName, String rowNum,
                                               String[] fieldValues) {
        TempoGridCell.getInstance(settings).waitFor(gridName, columnName, rowNum);
        return TempoGridCell.getInstance(settings).containsMultiple(fieldValues, gridName, columnName, rowNum);
    }

    /**
     * Verifies a grid field with a single value that may contain a comma. This is useful in verifying that an option that contains a comma
     * has
     * been selected.<br>
     * <br>
     * FitNesse Example:
     * <code>| verify grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] | contains value | VALUE |</code>
     *
     * @param gridName   Name of the grid
     * @param columnName Name or index of the column
     * @param rowNum     Index of the row
     * @param fieldValue Field value, can contain a comma.
     * @return True, if fields contains value
     */
    public boolean verifyGridColumnRowContainsValue(String gridName, String columnName, String rowNum,
                                                    String fieldValue) {
        return verifyGridColumnRowContains(gridName, columnName, rowNum, new String[] {fieldValue});
    }

    /**
     * Selects a row in an editable or paging grid.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| select grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | row | [1] |</code><br>
     *
     * @param gridName Can either be the grid name or grid name with index, e.g. 'Grid Name' or 'Grid Name[2]'
     * @param rowNum   Index of row to select, e.g. [2]
     */
    public void selectGridRow(String gridName, String rowNum) {
        TempoGrid.getInstance(settings).waitFor(gridName, rowNum);
        TempoGridRow.getInstance(settings).click(gridName, rowNum);
    }

    /**
     * Verifies if a grid row is selected<br>
     * <br>
     * FitNesse Example: <code>| verify grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | row | ROW_NUMBER | is selected |</code>
     *
     * @param gridName Name or name and index of grid
     * @param rowNum   Row number
     * @return True, if row is selected
     */
    public boolean verifyGridRowIsSelected(String gridName, String rowNum) {
        TempoGrid.getInstance(settings).waitFor(gridName, rowNum);
        return TempoGridRow.getInstance(settings).contains(gridName, rowNum);
    }

    /**
     * Count rows in an editable or paging grid.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| count grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | rows |</code><br>
     *
     * @param gridName Can either be the grid name or grid name with index, e.g. 'Grid Name' or 'Grid Name[2]'
     * @return Number of grid rows
     */
    public Integer countGridRows(String gridName) {
        TempoGrid.getInstance(settings).waitFor(gridName);
        return TempoGridRow.getInstance(settings).count(gridName);
    }

    /**
     * Returns the value of a field.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get grid | GRID_NAME_OR_INDEX | total count |</code> - Simply returns the chosen grid's total count<br>
     * <code>| $gridTotalCount= | get grid | GRID_NAME_OR_INDEX | total count | </code> - Stores
     * the grid's total count value in $gridTotalCount<br>
     *
     * @param gridName Name or index of the grid
     * @return The grid's total count
     */
    public int getGridTotalCount(String gridName) {
        TempoGrid.getInstance(settings).waitFor(gridName);
        return TempoGridTotalCount.getInstance(settings).capture(gridName);
    }

    /**
     * Returns the value of a field.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get grid | GRID_NAME_OR_INDEX | row count |</code> - Simply returns the chosen grid's current page row count<br>
     * <code>| $gridRowCount= | get grid | GRID_NAME_OR_INDEX | row count | </code> - Stores
     * the grid's row count value in $gridRowCount<br>
     *
     * @param gridName Name or index of the grid
     * @return The grid's current page row count
     */
    public int getGridRowCount(String gridName) {
        TempoGrid.getInstance(settings).waitFor(gridName);
        return TempoGridRowCount.getInstance(settings).capture(gridName);
    }

    /**
     * Clicks on the add row link for a grid<br>
     * <br>
     * FitNesse Example: <code>| click on grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | add row link |</code>
     *
     * @param gridName Name or name and index of grid
     */
    public void clickOnGridAddRowLink(String gridName) {
        TempoGrid.getInstance(settings).waitFor(gridName);
        TempoGridAddRow.getInstance(settings).click(gridName);
    }

    /**
     * Clicks on the page link below a paging grid<br>
     * <br>
     * FitNesse Example: <code>| click on grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | navigation | NAV_REFERENCE |</code> - nav
     * reference only takes "first",
     * previous, next, or "last"
     *
     * @param gridName  Name or name and index of grid
     * @param navOption "first", previous, next, or "last"
     */

    public void clickOnGridNavigation(String gridName, String navOption) {
        TempoGrid.getInstance(settings).waitFor(gridName);
        TempoGridNavigation.getInstance(settings).click(gridName, navOption);
    }

    /**
     * Select all rows in a grid<br>
     * <br>
     * FitNesse Example: <code>| select all rows in grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] |</code>
     *
     * @param gridName Name or name and index of grid
     */

    public void selectAllRowsInGrid(String gridName) {
        TempoGridSelectAll.getInstance(settings).waitFor(gridName);
        TempoGridSelectAll.getInstance(settings).click(gridName);
    }

    /**
     * Sort a grid by a column<br>
     * <br>
     * FitNesse Example:
     * <code>| sort grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | by column | COLUMN_NAME or [COLUMN_INDEX] |</code> -
     *
     * @param gridName   Name or name and index of grid
     * @param columnName Name or index of column
     */
    public void sortGridByColumn(String gridName, String columnName) {
        TempoGrid.getInstance(settings).waitFor(gridName);
        TempoGridColumn.getInstance(settings).click(gridName, columnName);
    }

    /**
     * Verifies a grid field is not blank.<br>
     * <br>
     * FitNesse Example:
     * <code>| verify grid | GRID_NAME or GRID_NAME[INDEX] or [GRID_INDEX] | column | COLUMN_NAME or [COLUMN_INDEX] | row | [ROW_INDEX] | is not blank|</code>
     *
     * @param gridName   Name of the grid
     * @param columnName Name or index of the column
     * @param rowNum     Index of the row
     * @return True, if the field is not blank
     */
    public boolean verifyGridColumnRowIsNotBlank(String gridName, String columnName, String rowNum) {
        TempoGridCell.getInstance(settings).waitFor(gridName, columnName, rowNum);
        return TempoGridCell.getInstance(settings).isNotBlank(gridName, columnName, rowNum);
    }

    /**
     * Returns the validation message from a grid column row.<br>
     * <br>
     * FitNesse Example: <code>| get grid column row | GRID_NAME | COLUMN_NAME | ROW_NUM | validation message |</code><br>
     *
     * @param gridName Grid name or index
     * @param columnName Column name or index
     * @param rowNum Row number or index
     * @return Validation message from the grid column row
     */
    public String getGridColumnRowValidationMessage(String gridName, String columnName, String rowNum) {
        return TempoGridCellValidation.getInstance(settings).capture(gridName, columnName, rowNum);
    }

    /**
     * Clicks on the first link that matches the linkName.<br>
     * <br>
     * FitNesse Example: <code>| click on link | LINK_NAME or LINK_NAME[INDEX] |</code>
     *
     * @param linkName Name of link to click
     */
    public void clickOnLink(String linkName) {
        TempoLink.getInstance(settings).waitFor(linkName);
        TempoLink.getInstance(settings).click(linkName);
    }

    /**
     * Verifies there is a link with the specified name.
     * The method will wait for the timeout period before failing.<br>
     * <br>
     * FitNesse Example: <code>| verify link | LINK_NAME or LINK_NAME[INDEX] | is present |</code>
     *
     * @param linkName Name of link to verify is present
     * @return True, if link field is located
     */
    public boolean verifyLinkIsPresent(String linkName) {
        TempoLink.getInstance(settings).waitFor(linkName);
        return true;
    }

    /**
     * Verifies there is a not link with the specified name.
     * The method will wait for the not present timeout period before failing.<br>
     * <br>
     * FitNesse Example: <code>| verify link | LINK_NAME or LINK_NAME[INDEX] | is not present |</code>
     *
     * @param linkName Name of link to verify is not present
     * @return True, if link field is not located
     */
    public boolean verifyLinkIsNotPresent(String linkName) {
        return TempoLink.getInstance(settings).waitForReturn(false, linkName);
    }

    /**
     * Verifies if a link field's URL contains a specific value.<br>
     * <br>
     * FitNesse Example: <code>| verify link | LINK_NAME or LINK_NAME[INDEX] | URL contains | URL_TEXT |</code>
     *
     * @param linkName Name of link to look for URL
     * @param urlText  Values to verify that the link URL contains
     * @return True, if the link URL does contain the value
     */
    public boolean verifyLinkURLContains(String linkName, String urlText) {
        TempoLink.getInstance(settings).waitFor(linkName);
        return TempoLinkUrl.getInstance(settings).contains(linkName, urlText);
    }

    /**
     * Returns the URL of a link field.<br>
     * <br>
     * FitNesse example: <code>| $VARIABLE_NAME= | get link | LINK_NAME or LINK_NAME[INDEX] | URL |</code> Use $VARIABLE_NAME to access the
     * variable
     * containing the link URL of the link field specified
     *
     * @param linkName Name of link
     * @return Link URL
     */
    public String getLinkURL(String linkName) {
        return TempoLinkUrl.getInstance(settings).capture(linkName);
    }

    /**
     * Clicks on the first icon link that matches the icon alt text.<br>
     * <br>
     * FitNesse Example: <code>| click on icon link |ALT_TEXT_NAME or ALT_TEXT_NAME[INDEX]|</code>
     *
     * @param iconAltTextName alt text of icon to click
     */
    public void clickOnIconLink(String iconAltTextName) {
        TempoIconLink.getInstance(settings).waitFor(iconAltTextName);
        TempoIconLink.getInstance(settings).click(iconAltTextName);
    }

    /**
     * Clicks on the first document image link that matches the icon alt text. It will not click on document image links
     * that are inside an imageField where thumbnail is true.<br>
     * <br>
     * FitNesse Example: <code>| click on document image link | ALT_TEXT_NAME or ALT_TEXT_NAME[INDEX]|</code>
     *
     * @param documentImageLinkAltTextName alt text of document image to link
     */
    public void clickOnDocumentImageLink(String documentImageLinkAltTextName) {
        TempoDocumentImageLink.getInstance(settings).waitFor(documentImageLinkAltTextName);
        TempoDocumentImageLink.getInstance(settings).click(documentImageLinkAltTextName);
    }

    /**
     * Download the image of the chart with chartLabel, if it's enabled for the chart<br>
     * <br>
     * FitNesse Example: <code>| download chart image | CHART_NAME or CHART_LABEL[INDEX] |</code>
     *
     * @param chartLabel the label, including the index if necessary, of the chart to download
     */
    public void downloadChartImage(String chartLabel) {
        TempoChart.getInstance(settings).clickOnDownloadIcon(chartLabel);
    }

    /**
     * Clicks on a specific bar of a bar chart on a page.<br>
     * <br>
     * FitNesse Example: <code>| click on bar chart | CHART_LABEL or CHART_LABEL[INDEX] | bar | [INDEX] |</code>
     *
     * @param barChartLabel The index of the bar chart out of all bar charts on the page
     * @param barNumber     The index of a bar in the bar chart.
     *                      Corresponds to the order of data in the chartSeries in its definition.
     */
    public void clickOnBarChartBar(String barChartLabel, String barNumber) {
        TempoClickableChart.getInstance(settings).waitFor("BAR", barChartLabel, barNumber);
        TempoClickableChart.getInstance(settings).click("BAR", barChartLabel, barNumber);
    }

    /**
     * Clicks on a specific column of a column chart on a page.<br>
     * <br>
     * FitNesse Example: <code>| click on column chart | CHART_LABEL or CHART_LABEL[INDEX] | column | [INDEX] |</code>
     *
     * @param columnChartLabel The index of the column chart out of all column charts on the page
     * @param columnNumber     The index of a column in the column chart.
     *                         Corresponds to the order of data in the chartSeries in its definition.
     */
    public void clickOnColumnChartColumn(String columnChartLabel, String columnNumber) {
        TempoClickableChart.getInstance(settings).waitFor("COLUMN", columnChartLabel, columnNumber);
        TempoClickableChart.getInstance(settings).click("COLUMN", columnChartLabel, columnNumber);
    }

    /**
     * Clicks on a specific slice of a pie chart on a page.<br>
     * <br>
     * FitNesse Example: <code>| click on pie chart | CHART_LABEL or CHART_LABEL[INDEX] | pie slice | [INDEX] |</code>
     *
     * @param pieChartLabel  The index of the pie chart out of all pie charts on the page
     * @param pieSliceNumber The index of the slice in the pie chart.
     *                       Corresponds to the order of chartSeries in its definition.
     */
    public void clickOnPieChartPieSlice(String pieChartLabel, String pieSliceNumber) {
        TempoClickableChart.getInstance(settings).waitFor("PIE", pieChartLabel, pieSliceNumber);
        TempoClickableChart.getInstance(settings).click("PIE", pieChartLabel, pieSliceNumber);
    }

    /**
     * Clicks on a specific point of a line chart on a page.<br>
     * <br>
     * FitNesse Example: <code>| click on line chart | CHART_LABEL or CHART_LABEL[INDEX] | point | [INDEX] |</code>
     *
     * @param lineChartLabel The index of the line chart out of all line charts on the page
     * @param pointNumber    The index of the point from going from left to right in the line chart.
     *                       Corresponds to the order of data in the chartSeries in its definition.
     */
    public void clickOnLineChartPoint(String lineChartLabel, String pointNumber) {
        TempoClickableChart.getInstance(settings).waitFor("LINE", lineChartLabel, pointNumber);
        TempoClickableChart.getInstance(settings).click("LINE", lineChartLabel, pointNumber);
    }

    /**
     * Click a specified action inside a record action field with styling of MENU or MENU_ICON
     * <br>
     * FitNesse Example: <code>| click on record action field | [INDEX] | menu action | ACTION_NAME or [INDEX] |</code>
     *
     * @param indexOfField The index of the record action field out of all record action fields on the page
     * @param action       The action from the dropdown that the user wants to click on
     */
    public void clickOnRecordActionFieldMenuAction(String indexOfField, String action) {
        TempoRecordActionField.getInstance(settings).waitFor(indexOfField);
        TempoRecordActionField.getInstance(settings).click(indexOfField, action);
    }

    /**
     * Clicks on button with tooltip.<br>
     * <br>
     * FitNesse Example: <code>| click on button with tooltip | TOOLTIP |</code>
     *
     * @param tooltip tooltip
     */
    public void clickOnButtonWithTooltip(String tooltip) {
        TempoButton.getInstance(settings).waitFor(Constants.FIELD_LOCATOR_TOOLTIP + tooltip);
        TempoButton.getInstance(settings).click(Constants.FIELD_LOCATOR_TOOLTIP + tooltip);
    }

    /**
     * Clicks on the first button that matches the buttonName.<br>
     * <br>
     * FitNesse Example: <code>| click on button | BUTTON_NAME or BUTTON_NAME[INDEX] |</code>
     *
     * @param buttonName Name of button to click
     */
    public void clickOnButton(String buttonName) {
        TempoButton.getInstance(settings).waitFor(buttonName);
        TempoButton.getInstance(settings).click(buttonName);
    }

    /**
     * Verifies if button with given label is present in the user interface. This is useful for determining if conditionals to show buttons
     * have worked correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify button | BUTTON_NAME or BUTTON_NAME[INDEX] | is present |</code>
     *
     * @param buttonName Name of the button
     * @return True, if button is located
     */
    public boolean verifyButtonIsPresent(String buttonName) {
        TempoButton.getInstance(settings).waitFor(buttonName);
        return true;
    }

    /**
     * Verifies if button with given label is not present in the user interface. This is useful for determining if conditionals to hide
     * buttons have worked correctly.<br>
     * <br>
     * FitNesse Example: <code>| verify button with label | BUTTON_NAME or BUTTON_NAME[INDEX] | is not present |</code><br>
     * <br>
     * Use this rather than <code>| reject | verify button | BUTTON_NAME or BUTTON_NAME[INDEX] | is present |</code> as it will not refresh
     * and wait.
     *
     * @param buttonName Name of the button
     * @return True, if button is not located
     */
    public boolean verifyButtonIsNotPresent(String buttonName) {
        return TempoButton.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), buttonName);
    }

    /**
     * Verifies if button with given label is not disabled in the user interface.
     * <br>
     * FitNesse Example: <code>| verify button | BUTTON_NAME or BUTTON_NAME[INDEX] | is enabled |</code>
     *
     * @param buttonName Name of the button
     * @return True, if button is enabled
     */
    public boolean verifyButtonIsEnabled(String buttonName) {
        return TempoButton.getInstance(settings).isEnabled(buttonName);
    }

    /**
     * Verifies if button with given label is disabled in the user interface.
     * <br>
     * FitNesse Example: <code>| verify button | BUTTON_NAME or BUTTON_NAME[INDEX] | is disabled |</code>
     *
     * @param buttonName Name of the button
     * @return True, if button is disabled
     */
    public boolean verifyButtonIsDisabled(String buttonName) {
        return !TempoButton.getInstance(settings).isEnabled(buttonName);
    }

    /**
     * Clicks on the save changes link.<br>
     * <br>
     * FitNesse Example: <code>| click on button | BUTTON_NAME or BUTTON_NAME[INDEX] |</code>
     */
    public void clickOnSaveChanges() {
        TempoSaveChanges.getInstance(settings).waitFor();
        TempoSaveChanges.getInstance(settings).click();
    }

    /**
     * Verifies the Signature Field with the specified label exists.
     * <br>
     * FitNesse Example: <code>| verify signature field | SIGNATURE_FIELD or SIGNATURE_FIELD[INDEX] | is present |</code>
     *
     * @param label Label of the signature field
     * @return True, if signature field is present
     */
    public boolean verifySignatureFieldIsPresent(String label) {
        return TempoSignatureField.getInstance(settings).waitForReturn(true, label);
    }

    /**
     * Clicks on the Signature Field button with the specified label.
     * <br>
     * FitNesse Example: <code>| click on signature field | SIGNATURE_FIELD or SIGNATURE_FIELD[INDEX] |</code>
     *
     * @param label Label of the signature field
     */
    public void clickOnSignatureField(String label) {
        TempoSignatureField.getInstance(settings).waitFor(label);
        TempoSignatureField.getInstance(settings).click(label);
    }

    /**
     * Draws a dummy signature in the signature panel.
     * <br>
     * FitNesse Example: <code>| draw signature |</code>
     */
    public void drawSignature() {
        TempoSignatureField.getInstance(settings).drawSignature();
    }

    /**
     * Clicks on a milestone step.<br>
     * <br>
     * FitNesse Example: <code>| click on milestone | MILESTONE or MILESTONE[INDEX] or [INDEX] | step | STEP or [INDEX] |</code>
     *
     * @param milestone Name or Name[Index] of milestone
     * @param step      Name or [Index] of step
     */
    public void clickOnMilestoneStep(String milestone, String step) {
        TempoFieldFactory.getInstance(settings).waitFor("MILESTONE", milestone);
        TempoMilestoneFieldStep.getInstance(settings).click(milestone, step);
    }

    /**
     * Clicks on a card.<br>
     * <br>
     * Example: <code>| click on card | CARD_LINK_NAME |</code>
     *
     * @param linkName Name of tempo menu, e.g. Records, Tasks, News
     */
    public void clickOnCard(String linkName) {
        TempoCard.getInstance(settings).waitFor(linkName);
        TempoCard.getInstance(settings).click(linkName);
    }

    /**
     * Clicks on a card using direct click method with enhanced reliability.<br>
     * This method tries to find clickable elements inside the card first,<br>
     * then falls back to direct click, and finally uses JavaScript click.<br>
     * <br>
     * FitNesse Example: <code>| click on card direct | CARD_NAME or CARD_NAME[INDEX] |</code>
     *
     * @param cardName Name of card to click
     */
    public void clickOnCardDirect(String cardName) {
        TempoCardDirect.getInstance(settings).waitFor(cardName);
        TempoCardDirect.getInstance(settings).click(cardName);
    }

    /**
     * Clicks on a card using TempoTermCard click method with enhanced reliability.<br>
     * This method tries to find clickable elements inside the card first,<br>
     * then falls back to direct click, and finally uses JavaScript click.<br>
     * <br>
     * FitNesse Example: <code>| click on card TempoTermCard | CARD_NAME or CARD_NAME[INDEX] |</code>
     *
     * @param cardName Name of card to click
     */
    public void clickOnTermCard(String cardName) {
        TempoTermCard.getInstance(settings).waitFor(cardName);
        TempoTermCard.getInstance(settings).click(cardName);
    }
    /**
     * Clicks on text within the interface.
     * This is particularly useful for nested card items where "click on card" doesn't work.<br>
     * <br>
     * Example: <code>| click on text | TEXT |</code>
     *
     * @param text Text found on the screen
     */
    public void clickOnText(String text) {
        TempoGeneralizedText.getInstance(settings).waitFor(text);
        TempoGeneralizedText.getInstance(settings).click(text);
    }

    /**
     * Verifies the percentage of a gauge field.<br>
     * <br>
     * FitNesse Example: <code>| verify gauge field | GAUGE FIELD or GAUGE FIELD[INDEX] percentage is | PERCENTAGE |</code>
     *
     * @param gaugeField Name or Name[Index] of gauge field
     * @param percentage Value of percentage
     * @return True, if the current percentage matches
     */
    public boolean verifyGaugeFieldPercentageIs(String gaugeField, String percentage) {
        TempoFieldFactory.getInstance(settings).waitFor(gaugeField);
        return TempoFieldFactory.getInstance(settings).contains(gaugeField, percentage);
    }

    /**
     * Gets the percentage of a gauge field.<br>
     * <br>
     * FitNesse Example: <code>| get gauge field | GAUGE FIELD or GAUGE FIELD[INDEX] | percentage |</code>
     *
     * @param gaugeField Name or Name[Index] of gauge field
     * @return Gauge field percentage
     */
    public String getGaugeFieldPercentage(String gaugeField) {
        TempoFieldFactory.getInstance(settings).waitFor(gaugeField);
        return TempoFieldFactory.getInstance(settings).capture(gaugeField);
    }

    /**
     * Verifies a stamp field is present based on label.<br>
     * <br>
     * FitNesse Example: <code>| verify stamp field | STAMP FIELD or STAMP FIELD[INDEX] | is present |</code>
     *
     * @param stampField Name or Name[Index] of stamp field
     * @return Presence of stamp field
     */
    public boolean verifyStampFieldIsPresent(String stampField) {
        return TempoStampField.getInstance(settings).waitForReturn(true, stampField);
    }

    /**
     * Verifies a stamp field is present based on label.<br>
     * <br>
     * FitNesse Example: <code>| verify stamp field | STAMP FIELD or STAMP FIELD[INDEX] | contains text | TEXT |</code>
     *
     * @param stampField Name or Name[Index] of stamp field
     * @param text       Text expected in the stamp field
     * @return Stamp field with label and desired text exists
     */
    public boolean verifyStampFieldContainsText(String stampField, String text) {
        TempoStampField.getInstance(settings).waitFor(stampField);
        WebElement webElement = TempoStampField.getInstance(settings).getWebElement(stampField);
        return TempoStampField.getInstance(settings).contains(webElement, text);
    }

    /**
     * Verifies if a milestone is currently on a particular step.<br>
     * <br>
     * FitNesse Example: <code>| verify milestone | MILESTONE or MILESTONE[INDEX] | step is | STEP or [INDEX] |</code>
     *
     * @param milestone Name or Name[Index] of milestone
     * @param step      Name or [Index] of step
     * @return True, if the current step matches
     */
    public boolean verifyMilestoneStepIs(String milestone, String[] step) {
        TempoFieldFactory.getInstance(settings).waitFor(milestone);
        return TempoFieldFactory.getInstance(settings).containsMultiple(step, milestone);
    }

    /**
     * Verifies if a milestone is currently on a particular step.<br>
     * <br>
     * FitNesse Example: <code>| get milestone | MILESTONE or MILESTONE[INDEX] | step |</code>
     *
     * @param milestone Name or Name[Index] of milestone
     * @return Milestone step
     */
    public String getMilestoneStep(String milestone) {
        TempoFieldFactory.getInstance(settings).waitFor(milestone);
        return TempoFieldFactory.getInstance(settings).capture(milestone);
    }

    /**
     * Clicks on the first radio option that matches the optionName. This is useful if the radio field does not have a label.<br>
     * <br>
     * FitNesse Example: <code>| click on radio option | OPTION_NAME |</code> <code>| click on radio option | OPTION_NAME[INDEX] |</code>
     *
     * @param optionName Name of radio option to click
     */
    public void clickOnRadioOption(String optionName) {
        TempoRadioFieldOption.getInstance(settings).waitFor(optionName);
        TempoRadioFieldOption.getInstance(settings).click(optionName);
    }

    /**
     * Clicks on the first checkbox option that matches the optionName. This is useful if the checkbox field does not have a label.<br>
     * <br>
     * FitNesse Example: <code>| click on checkbox option | OPTION_NAME |</code>
     * <code>| click on checkbox option | OPTION_NAME[INDEX] |</code>
     *
     * @param optionName Name of checkbox option to click
     */
    public void clickOnCheckboxOption(String optionName) {
        TempoCheckboxFieldOption.getInstance(settings).waitFor(optionName);
        TempoCheckboxFieldOption.getInstance(settings).click(optionName);
    }

    /**
     * Verifies if a section contains a specific error. Useful for testing section validation.<br>
     * <br>
     * FitNesse Example: <code>| verify section | SECTION_NAME | contains validation message | VALIDATION_MESSAGE(S) |</code>
     *
     * @param sectionName        Name of section to look for error
     * @param validationMessages Validation messages to verify
     * @return True, if error is located
     */
    public boolean verifySectionContainsValidationMessage(String sectionName, String[] validationMessages) {
        TempoSectionValidation.getInstance(settings).waitForMultiple(validationMessages, sectionName);
        return true;
    }

    /**
     * Returns the validation message from a section.<br>
     * <br>
     * FitNesse Examples:<br>
     * <code>| get section | SECTION_NAME | validation message |</code> - Simply returns a string<br>
     * <code>| $error= | get section | SECTION_NAME | validation message | </code> - Stores the validation message in error, which can later
     * be accessed using $error<br>
     * <code>| check | get section | SECTION_NAME | validation message | VALIDATION_MESSAGE |</code> - Returns true if the section validation
     * message matches the VALIDATION_MESSAGE input.
     *
     * @param sectionName Name of section
     * @return Validation message
     */
    public String getSectionValidationMessage(String sectionName) {
        TempoSection.getInstance(settings).waitFor(sectionName);
        return TempoSectionValidation.getInstance(settings).capture(sectionName);
    }

    /**
     * Verifies a chart containing a specific label is present.
     * The method will wait for the timeout period and refresh up to the configured number of refresh times before failing.<br>
     * <br>
     * FitNesse Example: <code>| verify chart | CHART_LABEL | is present |</code>
     *
     * @param chartLabel Text to search for chart label
     * @return True, if chart is located with specific text
     */
    public boolean verifyChartIsPresent(String chartLabel) {
        TempoChart.getInstance(settings).waitFor(chartLabel);
        return true;
    }

    /**
     * Verifies a chart containing a specific label is not present.<br>
     * <br>
     * FitNesse Example: <code>| verify chart| CHART_LABEL | is not present |</code><br>
     * <br>
     * The reason to use than <code>| reject | verify chart | CHART_LABEL | is present |</code> is that this method will not
     * refresh and wait.
     *
     * @param chartLabel Text to search for chart label
     * @return True, if no chart is located with specific text
     */
    public boolean verifyChartIsNotPresent(String chartLabel) {
        return TempoChart.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), chartLabel);
    }

    /**
     * Verifies a box is present
     * The method will wait for the timeout period before failing.<br>
     * <br>
     * FitNesse Example: <code>| verify box | BOX_LABEL | is present |</code>
     *
     * @param boxLabel Text to search for box label
     * @return True, if box is located with specific text
     */
    public boolean verifyBoxIsPresent(String boxLabel) {
        TempoBox.getInstance(settings).waitFor(boxLabel);
        return true;
    }

    /**
     * Verifies a box is not present
     * The method will wait for the not present timeout period before declaring the box not present.<br>
     * <br>
     * FitNesse Example: <code>| verify box | BOX_LABEL | is not present |</code>
     *
     * @param boxLabel Text to search for box label
     * @return True, if box is not located with specific text
     */
    public boolean verifyBoxIsNotPresent(String boxLabel) {
        return TempoBox.getInstance(settings).waitForReturn(false, settings.getNotPresentTimeoutSeconds(), boxLabel);
    }

    /**
     * Verifies a section is present
     * The method will wait for the timeout period before failing.<br>
     * <br>
     * FitNesse Example: <code>| verify section | SECTION_LABEL | is present |</code>
     *
     * @param sectionLabel Text to search for section label
     * @return True, if section is located with specific text
     */
    public boolean verifySectionIsPresent(String sectionLabel) {
        TempoSection.getInstance(settings).waitFor(sectionLabel);
        return true;
    }

    /**
     * Verifies a section is not present
     * The method will wait for the not present timeout before it declares section not present.<br>
     * <br>
     * FitNesse Example: <code>| verify section | SECTION_LABEL | is not present |</code>
     *
     * @param sectionLabel Text to search for section label
     * @return True, if section is not located with specific text
     */
    public boolean verifySectionIsNotPresent(String sectionLabel) {
        return TempoSection.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), sectionLabel);
    }

    /**
     * Verifies a card is present
     * The method will wait for the timeout period before failing.<br>
     * <br>
     * FitNesse Example: <code>| verify card | CARD_LABEL | is present |</code>
     *
     * @param cardLabel Text to search for card accessibility text
     * @return True, if card is located with specific accessibility text
     */
    public boolean verifyCardIsPresent(String cardLabel) {
        TempoCard.getInstance(settings).waitFor(cardLabel);
        return true;
    }

    /**
     * Verifies a card is not present
     * The method will wait for the not present timeout before it declares card not present.<br>
     * <br>
     * FitNesse Example: <code>| verify card | CARD_LABEL | is not present |</code>
     *
     * @param cardLabel Text to search for card accessibility text
     * @return True, if card is not located with specific accessibility text
     */
    public boolean verifyCardIsNotPresent(String cardLabel) {
        return TempoCard.getInstance(settings).waitForReturn(false, settings.getNotPresentTimeoutSeconds(), cardLabel);
    }

    /**
     * Clicks on a card in a card choice field.<br>
     * <br>
     * Example: <code>| click on card | CARD_NAME | in card choice field | FIELD_NAME |</code>
     *
     * @param cardName  Name of the card choice
     * @param fieldName Name of the card choice field the card is in
     */
    public void clickOnCardChoice(String cardName, String fieldName) {
        TempoCardChoice.getInstance(settings).waitFor(cardName, fieldName);
        TempoCardChoice.getInstance(settings).click(cardName, fieldName);
    }

    /**
     * Verifies if card is selected in card choice field.<br>
     * <br>
     * Example: <code>| verify card | CARD_NAME | in card choice field | FIELD_NAME | is selected |</code>
     *
     * @param cardName  Name of the card choice
     * @param fieldName Name of the card choice field the card is in
     * @return True, if card is selected
     */
    public boolean verifyCardChoiceIsSelected(String cardName, String fieldName) {
        TempoCardChoice.getInstance(settings).waitFor(cardName, fieldName);
        return TempoCardChoiceField.getInstance(settings).contains(cardName, fieldName);
    }

    /**
     * Verifies if video is present in the user interface.<br>
     * <br>
     * FitNesse Example: <code>| verify video | VIDEO_SOURCE or VIDEO_SOURCE[INDEX] | is present |</code>
     *
     * @param videoSource Source of video
     * @return True, if video is not located with specific source
     */
    public boolean verifyVideoIsPresent(String videoSource) {
        TempoVideo.getInstance(settings).waitFor(videoSource);
        return true;
    }

    /**
     * Verifies if video is not present in the user interface.<br>
     * <br>
     * FitNesse Example: <code>| verify report | VIDEO_SOURCE or VIDEO_SOURCE[INDEX] | is not present |</code><br>
     *
     * @param videoSource Source of video
     * @return True, if video is not located with specific source
     */
    public boolean verifyVideoIsNotPresent(String videoSource) {
        return TempoVideo.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), videoSource);
    }

    /**
     * Verifies if web content is present in the user interface.<br>
     * <br>
     * FitNesse Example: <code>| verify video | WEB_CONTENT_SOURCE or WEB_CONTENT_SOURCE[INDEX] | is present |</code>
     *
     * @param webContentSource Source of web content
     * @return True, if web content is located with specific source
     */
    public boolean verifyWebContentIsPresent(String webContentSource) {
        TempoWebContent.getInstance(settings).waitFor(webContentSource);
        return true;
    }

    /**
     * Verifies if web content is not present in the user interface.<br>
     * <br>
     * FitNesse Example: <code>| verify report | WEB_CONTENT_SOURCE or WEB_CONTENT_SOURCE[INDEX] | is not present |</code><br>
     *
     * @param webContentSource Source of web content
     * @return True, if web content is not located with specific source
     */
    public boolean verifyWebContentIsNotPresent(String webContentSource) {
        return TempoWebContent.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), webContentSource);
    }

    /**
     * Returns true if there is an error on tempo
     *
     * @return True, if error is present
     */
    public boolean errorIsPresent() {
        return TempoError.getInstance(settings).waitForReturn(true);
    }

    /**
     * Verifies if text is present in the user interface.<br>
     * <br>
     * FitNesse Example: <code>| verify text | TEXT_ON_INTERFACE | is present |</code><br>
     *
     * @param textOnInterface text user is seeking to verify
     * @return True, if text is present
     */
    public boolean verifyTextIsPresent(String textOnInterface) {
        return TempoAssertion.getInstance(settings).waitForReturn(true, textOnInterface);
    }

    /**
     * Verifies if text is not present in the user interface.<br>
     * <br>
     * FitNesse Example: <code>| verify text | TEXT_ON_INTERFACE | is not present |</code><br>
     *
     * @param textOnInterface text user is seeking to verify
     * @return True, if text is not present
     */
    public boolean verifyTextIsNotPresent(String textOnInterface) {
        return TempoAssertion.getInstance(settings)
                .waitForReturn(false, settings.getNotPresentTimeoutSeconds(), textOnInterface);
    }
}

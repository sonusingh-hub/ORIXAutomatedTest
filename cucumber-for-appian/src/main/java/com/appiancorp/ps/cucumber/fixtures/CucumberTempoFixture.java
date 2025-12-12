package com.appiancorp.ps.cucumber.fixtures;

import com.appiancorp.ps.automatedtest.common.Settings;
import com.appiancorp.ps.automatedtest.fixture.TempoFixture;
import com.appiancorp.ps.cucumber.utils.ExcelWriter;
import com.appiancorp.ps.cucumber.utils.TestDataManager;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import java.util.List;

public class CucumberTempoFixture {

    private static TempoFixture fixture = new TempoFixture();

    public CucumberTempoFixture() {
        fixture.setSettings(CucumberBaseFixture.getSettings());
    }

    @Given("^I click on menu \"([^\"]*)\"$")
    public void clickOnMenu(String tempoMenu) {
        fixture.clickOnMenu(tempoMenu);
    }

    @Given("^I click on menu widget \"([^\"]*)\"$")
    public void clickOnMenuWidget(String menuWidget) {
        fixture.clickOnMenuWidget(menuWidget);
    }

    @Given("^I search for \"([^\"]*)\"$")
    public void searchFor(String searchTerm) {
        fixture.searchFor(searchTerm);
    }

    @Given("^I clear search value$")
    public void clearSearchValue() {
        fixture.clearSearchValue();
    }

    @Given("^I logout$")
    public void logout() {
        fixture.logout();
    }

    @Given("^I verify news feed containing text \"([^\"]*)\" is present$")
    public boolean verifyNewsFeedContainingTextIsPresent(String newsText) {
        if (!fixture.verifyNewsFeedContainingTextIsPresent(newsText)) {
            throw new RuntimeException("Could not verify news feed containing text is present");
        }
        return true;
    }

    @Given("^I verify news feed containing text \"([^\"]*)\" is not present$")
    public boolean verifyNewsFeedContainingTextIsNotPresent(String newsText) {
        if (!fixture.verifyNewsFeedContainingTextIsNotPresent(newsText)) {
            throw new RuntimeException("Could not verify news feed containing text is not present");
        }
        return true;
    }

    @Given("^I toggle more info for news feed containing text \"([^\"]*)\"$")
    public void toggleMoreInfoForNewsFeedContainingText(String newsText) {
        fixture.toggleMoreInfoForNewsFeedContainingText(newsText);
    }

    @Given("^I delete news post \"([^\"]*)\"$")
    public void deleteNewsPost(String newsText) {
        fixture.deleteNewsPost(newsText);
    }

    public void deleteAllNewsPosts() {
        fixture.deleteAllNewsPosts();
    }

    @Given("^I verify news feed containing text \"([^\"]*)\" and more info with label \"([^\"]*)\" and value \"([^\"]*)\" is present$")
    public boolean verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(String newsText, String label,
                                                                                     String value) {
        if (!fixture.verifyNewsFeedContainingTextAndMoreInfoWithLabelAndValueIsPresent(newsText, label, value)) {
            throw new RuntimeException("Could not verify the expected news feed is present");
        }
        return true;
    }

    @Given("^I verify news feed containing text \"([^\"]*)\" tagged with \"([^\"]*)\" is present$")
    public boolean verifyNewsFeedContainingTextTaggedWithIsPresent(String newsText, String newsTag) {
        if (!fixture.verifyNewsFeedContainingTextTaggedWithIsPresent(newsText, newsTag)) {
            throw new RuntimeException("Could not verify the expected news feed with tags is present");
        }
        return true;
    }

    @Given("^I verify news feed containing text \"([^\"]*)\" commented with \"([^\"]*)\" is present$")
    public boolean verifyNewsFeedContainingTextCommentedWithIsPresent(String newsText, String newsComment) {
        if (!fixture.verifyNewsFeedContainingTextCommentedWithIsPresent(newsText, newsComment)) {
            throw new RuntimeException("Could not verify the expected news feed with comments is present");
        }
        return true;
    }

    @Given("^I get regex \"([^\"]*)\" group \"([^\"]*)\" from news feed containing text \"([^\"]*)\"$")
    public String getRegexGroupFromNewsFeedContainingText(String regex, String group, String newsText) {
        return fixture.getRegexGroupFromNewsFeedContainingText(regex, Integer.parseInt(group), newsText);
    }

    @Given("^I get regex \"([^\"]*)\" group \"([^\"]*)\" from news feed containing text \"([^\"]*)\" commented with \"([^\"]*)\"$")
    public String getRegexGroupFromNewsFeedContainingTextCommentedWith(String regex, String group, String newsText,
                                                                       String newsComment) {
        return fixture.getRegexGroupFromNewsFeedContainingTextCommentedWith(regex, Integer.parseInt(group), newsText,
                newsComment);
    }

    @Given("^I click on news feed \"([^\"]*)\" record tag \"([^\"]*)\"$")
    public void clickOnNewsFeedRecordTag(String newsText, String recordTag) {
        fixture.clickOnNewsFeedRecordTag(newsText, recordTag);
    }

    @Given("^I send kudos \"([^\"]*)\" to \"([^\"]*)\"$")
    public void sendKudosTo(String message, String recipient) {
        fixture.sendKudosTo(message, recipient);
    }

    @Given("^I send \"([^\"]*)\" message \"([^\"]*)\" to \"([^\"]*)\"$")
    public void sendMessageTo(String lockStatus, String message, List<String> recipients) {
        fixture.sendMessageTo(lockStatus, message, recipients);
    }

    @Given("^I send task \"([^\"]*)\" to \"([^\"]*)\"$")
    public void sendTaskTo(String message, String recipient) {
        fixture.sendTaskTo(message, recipient);
    }

    @Given("^I send post \"([^\"]*)\" to \"([^\"]*)\"$")
    public void sendPostTo(String message, List<String> participants) {
        fixture.sendPostTo(message, participants);
    }

    @Given("^I send post \"([^\"]*)\"$")
    public void sendPostTo(String message) {
        fixture.sendPost(message);
    }

    @Given("^I star post containing \"([^\"]*)\"$")
    public void starPostContaining(String newsText) {
        fixture.starPostContaining(newsText);
    }

    @Given("^I verify post \"([^\"]*)\" is starred$")
    public boolean verifyPostIsStarred(String newsText) {
        if (!fixture.verifyPostIsStarred(newsText)) {
            throw new RuntimeException("Could not verify post is starred");
        }
        return true;
    }

    @Given("^I add comment \"([^\"]*)\" to post containing \"([^\"]*)\"$")
    public void addCommentToPostContaining(String comment, String newsText) {
        fixture.addCommentToPostContaining(comment, newsText);
    }

    @Given("^I filter news on \"([^\"]*)\"$")
    public void filterNewsOn(String filterName) {
        fixture.filterNewsOn(filterName);
    }

    @Given("^I verify news feed containing \"([^\"]*)\" link navigation$")
    public boolean verifyNewsFeedContainingLinkNavigation(String newsText) {
        if (!fixture.verifyNewsFeedContainingLinkNavigation(newsText)) {
            throw new RuntimeException("Could not verify news feed contains expected link navigation");
        }
        return true;
    }

    @Given("^I verify hover over news poster circle on post containing \"([^\"]*)\"$")
    public boolean verifyHoverOverNewsPosterCircleOnPostContaining(String newsText) {
        if (!fixture.verifyHoverOverNewsPosterCircleOnPostContaining(newsText)) {
            throw new RuntimeException("Could not verify hover over news poster");
        }
        return true;
    }

    @Given("^I verify hover over news poster link on post containing \"([^\"]*)\"$")
    public boolean verifyHoverOverNewsPosterLinkOnPostContaining(String newsText) {
        if (!fixture.verifyHoverOverNewsPosterLinkOnPostContaining(newsText)) {
            throw new RuntimeException("Could not verify hover over news poster link");
        }
        return true;
    }

    @Given("^I click user profile circle on post containing \"([^\"]*)\"$")
    public void clickUserProfileCircleOnPostContaining(String newsText) {
        fixture.clickUserProfileCircleOnPostContaining(newsText);
    }

    @Given("^I click user profile link on post containing \"([^\"]*)\"$")
    public void clickUserProfileLinkOnPostContaining(String newsText) {
        fixture.clickUserProfileLinkOnPostContaining(newsText);
    }

    @Given("^I verify task feed containing text \"([^\"]*)\" is not present$")
    public boolean verifyTaskFeedContainingTextIsNotPresent(String taskText) {
        if (!fixture.verifyTaskFeedContainingTextIsNotPresent(taskText)) {
            throw new RuntimeException("Could not verify task feed containing text is present");
        }
        return true;
    }

    @Given("^I close social task containing \"([^\"]*)\" with comment \"([^\"]*)\"$")
    public void closeSocialTaskContainingWithComment(String comment, String taskText) {
        fixture.closeSocialTaskContainingWithComment(comment, taskText);
    }

    public void sortByNewest() {
        fixture.sortByNewest();
    }

    public void sortByOldest() {
        fixture.sortByOldest();
    }

    public boolean verifySortLabel(String value) {
        return fixture.verifySortLabel(value);
    }

    @Given("^I click on task \"([^\"]*)\"$")
    public void clickOnTask(String taskName) {
        fixture.clickOnTask(taskName);
    }

    @Given("^I get regex \"([^\"]*)\" group \"([^\"]*)\" from task name containing text \"([^\"]*)\"$")
    public String getRegexGroupFromTaskNameContainingText(String regex, String group, String taskText) {
        return fixture.getRegexGroupFromTaskNameContainingText(regex, Integer.parseInt(group), taskText);
    }

    @Given("^I verify task \"([^\"]*)\" is present$")
    public boolean verifyTaskIsPresent(String taskName) {
        if (!fixture.verifyTaskIsPresent(taskName)) {
            throw new RuntimeException("Could not verify task is present");
        }
        return true;
    }

    @Given("^I verify task \"([^\"]*)\" is not present$")
    public boolean verifyTaskIsNotPresent(String taskName) {
        if (!fixture.verifyTaskIsNotPresent(taskName)) {
            throw new RuntimeException("Could not verify task is not present");
        }
        return true;
    }

    @Given("^I verify task \"([^\"]*)\" has deadline of \"([^\"]*)\"$")
    public boolean verifyTaskHasDeadlineOf(String taskName, String deadline) {
        if (!fixture.verifyTaskHasDeadlineOf(taskName, deadline)) {
            throw new RuntimeException("Could not verify task has the expected deadline");
        }
        return true;
    }

    @Given("^I click on task report \"([^\"]*)\"$")
    public void clickOnTaskReport(String taskReport) {
        fixture.clickOnTaskReport(taskReport);
    }

    @Given("^I accept task$")
    public void acceptTask() {
        fixture.acceptTask();
    }

    @Given("^I click on record type \"([^\"]*)\"$")
    public void clickOnRecordType(String typeName) {
        fixture.clickOnRecordType(typeName);
    }

    @Given("^I populate record type user filter \"([^\"]*)\" with \"([^\"]*)\"$")
    public void populateRecordTypeUserFilterWith(String userFilter, String value) {
        fixture.populateRecordTypeUserFilterWith(userFilter, value);
    }

    @Given("^I clear record type user filter \"([^\"]*)\"$")
    public void clearRecordTypeUserFilter(String userFilter) {
        fixture.clearRecordTypeUserFilter(userFilter);
    }

    @Given("^I click on record \"([^\"]*)\"$")
    public void clickOnRecord(String recordName) {
        fixture.clickOnRecord(recordName);
    }

    @Given("^I click on record from excel \"([^\"]*)\"$")
    public void clickOnRecordFromExcel(String excelRef) {

        // Remove "excel:" prefix
        String cleaned = excelRef.replace("excel:", "");

        // Split into Sheet.Key
        String sheet = cleaned.substring(0, cleaned.indexOf("."));
        String key   = cleaned.substring(cleaned.indexOf(".") + 1);

        // Read value from Excel
        String recordValue = TestDataManager.get(sheet, key);

        // Use the retrieved value to click record
        fixture.clickOnRecord(recordValue);
    }

    @Given("^I get regex \"([^\"]*)\" group \"([^\"]*)\" from record name containing text \"([^\"]*)\"$")
    public String getRegexGroupFromRecordNameContainingText(String regex, String group, String recordText) {
        return fixture.getRegexGroupFromRecordNameContainingText(regex, Integer.parseInt(group), recordText);
    }

    @Given("^I verify record \"([^\"]*)\" is present$")
    public boolean verifyRecordIsPresent(String recordName) {
        if (!fixture.verifyRecordIsPresent(recordName)) {
            throw new RuntimeException("Could not verify record is present");
        }
        return true;
    }

    @Given("^I verify record \"([^\"]*)\" is not present$")
    public boolean verifyRecordIsNotPresent(String recordName) {
        if (!fixture.verifyRecordIsNotPresent(recordName)) {
            throw new RuntimeException("Could not verify record is not present");
        }
        return true;
    }

    @Given("^I click on record view \"([^\"]*)\"$")
    public void clickOnRecordView(String viewName) {
        fixture.clickOnRecordView(viewName);
    }

    @Given("^I click on record related action \"([^\"]*)\"$")
    public void clickOnRecordRelatedAction(String relatedActionName) {
        fixture.clickOnRecordRelatedAction(relatedActionName);
    }

    @Given("^I verify record related action \"([^\"]*)\" is present$")
    public boolean verifyRecordRelatedActionIsPresent(String relatedActionName) {
        if (!fixture.verifyRecordRelatedActionIsPresent(relatedActionName)) {
            throw new RuntimeException("Could not verify record related action is present");
        }
        return true;
    }

    @Given("^I verify record related action \"([^\"]*)\" is not present$")
    public boolean verifyRecordRelatedActionIsNotPresent(String relatedActionName) {
        if (!fixture.verifyRecordRelatedActionIsNotPresent(relatedActionName)) {
            throw new RuntimeException("Could not verify recrod related action is not present");
        }
        return true;
    }

    @Given("^I get grid index \"([^\"]*)\"$")
    public String getGridIndex(String headers) {
        return fixture.getGridIndex(headers);
    }

    @Given("^I sort record grid by column \"([^\"]*)\"$")
    public void sortRecordGridByColumn(String columnName) {
        fixture.sortRecordGridByColumn(columnName);
    }

    @Given("^I click on record grid navigation \"([^\"]*)\"$")
    public void clickOnRecordGridNavigation(String navOption) {
        fixture.clickOnRecordGridNavigation(navOption);
    }

    @Given("^I click on report \"([^\"]*)\"$")
    public void clickOnReport(String reportName) {
        fixture.clickOnReport(reportName);
    }

    @Given("^I verify report \"([^\"]*)\" is present$")
    public boolean verifyReportIsPresent(String reportName) {
        if (!fixture.verifyReportIsPresent(reportName)) {
            throw new RuntimeException("Could not verify report is present");
        }
        return true;
    }

    @Given("^I verify report \"([^\"]*)\" is not present$")
    public boolean verifyReportIsNotPresent(String reportName) {
        if (!fixture.verifyReportIsNotPresent(reportName)) {
            throw new RuntimeException("Could not verify report is not present");
        }
        return true;
    }

    @Given("^I click on action \"([^\"]*)\"$")
    public void clickOnAction(String actionName) {
        fixture.clickOnAction(actionName);
    }

    @Given("^I star action \"([^\"]*)\"$")
    public void starAction(String actionName) {
        fixture.starAction(actionName);
    }

    @Given("^I verify action \"([^\"]*)\" is present$")
    public boolean verifyActionIsPresent(String actionName) {
        if (!fixture.verifyActionIsPresent(actionName)) {
            throw new RuntimeException("Could not verify action is present");
        }
        return true;
    }

    @Given("^I verify action \"([^\"]*)\" is not present$")
    public boolean verifyActionIsNotPresent(String actionName) {
        if (!fixture.verifyActionIsNotPresent(actionName)) {
            throw new RuntimeException("Could not verify action is not present");
        }
        return true;
    }

    @Given("^I click on application filter \"([^\"]*)\"$")
    public void clickOnApplicationFilter(String appFilter) {
        fixture.clickOnApplicationFilter(appFilter);
    }

    @Given("^I verify application filter \"([^\"]*)\" is present$")
    public boolean verifyApplicationFilterIsPresent(String applicationName) {
        if (!fixture.verifyApplicationFilterIsPresent(applicationName)) {
            throw new RuntimeException("Could not verify application filter is present");
        }
        return true;
    }

    @Given("^I verify application filter \"([^\"]*)\" is not present$")
    public boolean verifyApplicationFilterIsNotPresent(String applicationName) {
        if (!fixture.verifyApplicationFilterIsNotPresent(applicationName)) {
            throw new RuntimeException("Could not verify application filter is not present");
        }
        return true;
    }

    @Given("^I get form title$")
    public String getFormTitle() {
        return fixture.getFormTitle();
    }

    @Given("^I get regex \"([^\"]*)\" group \"([^\"]*)\" from form title$")
    public String getRegexGroupFromFormTitle(String regex, String group) {
        return fixture.getRegexGroupFromFormTitle(regex, Integer.parseInt(group));
    }

    @Given("^I get form instructions$")
    public String getFormInstructions() {
        return fixture.getFormInstructions();
    }

    @Given("^I populate field \"([^\"]*)\" with$")
    public void populateFieldWith(String fieldName, List<String> fieldValues) {
        fixture.populateFieldWith(fieldName, parseListToArray(fieldValues));
    }

    @Given("^I populate field \"([^\"]*)\" with \"([^\"]*)\"$")
    public void populateFieldWith(String fieldName, String fieldValue) {
        fixture.populateFieldWith(fieldName, new String[] {fieldValue});
    }

    @Given("^I populate field \"([^\"]*)\" with excel \"([^\"]*)\"$")
    public void populateFieldWithExcel(String fieldName, String fieldValue) {
        String finalValue = fieldValue.startsWith("excel:")
                ? TestDataManager.get(fieldValue.replace("excel:", ""))
                : fieldValue;
        fixture.populateFieldWith(fieldName, new String[]{finalValue});
    }

    @Given("^I populating field \"([^\"]*)\" with \"([^\"]*)\"$")
    public void populatingFieldWith(String fieldName, String fieldValue) {
        fixture.populatingFieldWith(fieldName, new String[] {fieldValue});
    }

    @Given("^I populating field \"([^\"]*)\" with excel \"([^\"]*)\"$")
    public void populatingFieldWithExcel(String fieldName, String fieldValue) {
        String finalValue = fieldValue.startsWith("excel:")
                ? TestDataManager.get(fieldValue.replace("excel:", ""))
                : fieldValue;
        fixture.populatingFieldWith(fieldName, new String[]{finalValue});
    }

    @Given("^I populates field \"([^\"]*)\" with \"([^\"]*)\"$")
    public void populatesFieldWith(String fieldName, String fieldValue) {
        fixture.populatesFieldWith(fieldName, new String[] {fieldValue});
    }

    @Given("^I populates field \"([^\"]*)\" with excel \"([^\"]*)\"$")
    public void populatesFieldWithExcel(String fieldName, String fieldValue) {
        String finalValue = fieldValue.startsWith("excel:")
                ? TestDataManager.get(fieldValue.replace("excel:", ""))
                : fieldValue;
        fixture.populatesFieldWith(fieldName, new String[]{finalValue});
    }

    @Given("^I populate field with placeholder \"([^\"]*)\" with$")
    public void populateFieldWithPlaceholderWith(String placeholder, List<String> fieldValues) {
        fixture.populateFieldWithPlaceholderWith(placeholder, parseListToArray(fieldValues));
    }

    @Given("^I populate field with placeholder \"([^\"]*)\" with \"([^\"]*)\"$")
    public void populateFieldWithPlaceholderWith(String placeholder, String fieldValue) {
        fixture.populateFieldWithPlaceholderWith(placeholder, new String[] {fieldValue});
    }

    @Given("^I populate field with instructions \"([^\"]*)\" with$")
    public void populateFieldWithInstructionsWith(String instructions, List<String> fieldValues) {
        fixture.populateFieldWithInstructionsWith(instructions, parseListToArray(fieldValues));
    }

    @Given("^I populate field with instructions \"([^\"]*)\" with \"([^\"]*)\"$")
    public void populateFieldWithInstructionsWith(String instructions, String fieldValue) {
        fixture.populateFieldWithInstructionsWith(instructions, new String[] {fieldValue});
    }

    @Given("^I populate field with tooltip \"([^\"]*)\" with$")
    public void populateFieldWithTooltipWith(String tooltip, List<String> fieldValues) {
        fixture.populateFieldWithTooltipWith(tooltip, parseListToArray(fieldValues));
    }

    @Given("^I populate field with tooltip \"([^\"]*)\" with \"([^\"]*)\"$")
    public void populateFieldWithTooltipWith(String tooltip, String fieldValue) {
        fixture.populateFieldWithTooltipWith(tooltip, new String[] {fieldValue});
    }

    @Given("^I get field with placeholder \"([^\"]*)\" value$")
    public String getFieldWithPlaceholderValue(String placeholder) {
        return fixture.getFieldWithPlaceholderValue(placeholder);
    }

    @Given("^I get field with instructions \"([^\"]*)\" value$")
    public String getFieldWithInstructionsValue(String instructions) {
        return fixture.getFieldWithInstructionsValue(instructions);
    }

    @Given("^I get field with tooltip \"([^\"]*)\" value$")
    public String getFieldWithTooltipValue(String tooltip) {
        return fixture.getFieldWithTooltipValue(tooltip);
    }

    @Given("^I verify field with placeholder \"([^\"]*)\" contains$")
    public boolean verifyFieldWithPlaceHolderContains(String placeholder, List<String> fieldValues) {
        if (!fixture.verifyFieldWithPlaceholderContains(placeholder, parseListToArray(fieldValues))) {
            throw new RuntimeException("Could not verify field with placeholder contains the expected values");
        }
        return true;
    }

    @Given("^I verify field with placeholder \"([^\"]*)\" contains \"([^\"]*)\"$")
    public boolean verifyFieldWithPlaceHolderContains(String placeholder, String fieldValue) {
        if (!fixture.verifyFieldWithPlaceholderContains(placeholder, new String[] {fieldValue})) {
            throw new RuntimeException("Could not verify field with placeholder contains the expected value");
        }
        return true;
    }

    @Given("^I verify field with instructions \"([^\"]*)\" contains$")
    public boolean verifyFieldWithInstructionsContains(String instructions, List<String> fieldValues) {
        if (!fixture.verifyFieldWithPlaceholderContains(instructions, parseListToArray(fieldValues))) {
            throw new RuntimeException("Could not verify field with instructions contains the expected values");
        }
        return true;
    }

    @Given("^I verify field with instructions \"([^\"]*)\" contains \"([^\"]*)\"$")
    public boolean verifyFieldWithInstructionsContains(String instructions, String fieldValue) {
        if (!fixture.verifyFieldWithInstructionsContains(instructions, new String[] {fieldValue})) {
            throw new RuntimeException("Could not verify field with instructions contains the expected value");
        }
        return true;
    }

    @Given("^I verify field with tooltip \"([^\"]*)\" contains$")
    public boolean verifyFieldWithTooltipContains(String tooltip, List<String> fieldValues) {
        if (!fixture.verifyFieldWithTooltipContains(tooltip, parseListToArray(fieldValues))) {
            throw new RuntimeException("Could not verify field with instructions contains the expected values");
        }
        return true;
    }

    @Given("^I verify field with tooltip \"([^\"]*)\" contains \"([^\"]*)\"$")
    public boolean verifyFieldWithTooltipContains(String tooltip, String fieldValue) {
        if (!fixture.verifyFieldWithTooltipContains(tooltip, new String[] {fieldValue})) {
            throw new RuntimeException("Could not verify field with instructions contains the expected value");
        }
        return true;
    }

    @Given("^I populate picker field \"([^\"]*)\" with partially matching suggestions for$")
    public void populateFieldWithPartiallyMatchingPickerFieldSuggestion(String fieldName, List<String> fieldValues) {
        fixture.populateFieldWithContains(fieldName, parseListToArray(fieldValues));
    }

    @Given("^I populate picker field \"([^\"]*)\" with partially matching suggestions for \"([^\"]*)\"$")
    public void populateFieldWithPartiallyMatchingPickerFieldSuggestion(String fieldName, String fieldValue) {
        fixture.populateFieldWithContains(fieldName, new String[] {fieldValue});
    }

    @Given("^I populate field type \"([^\"]*)\" named \"([^\"]*)\" with$")
    public void populateFieldWith(String fieldType, String fieldName, List<String> fieldValues) {
        fixture.populateFieldWith(fieldType, fieldName, parseListToArray(fieldValues));
    }

    @Given("^I populate field type \"([^\"]*)\" named \"([^\"]*)\" with \"([^\"]*)\"$")
    public void populateFieldWith(String fieldType, String fieldName, String fieldValue) {
        fixture.populateFieldWith(fieldType, fieldName, new String[] {fieldValue});
    }

    @Given("^I populate field \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void populateFieldWithValue(String fieldName, String fieldValue) {
        fixture.populateFieldWithValue(fieldName, fieldValue);
    }

    @Given("^I populate field \"([^\"]*)\" in section \"([^\"]*)\" with$")
    public void populateFieldInSectionWith(String fieldName, String sectionName, List<String> fieldValues) {
        fixture.populateFieldInSectionWith(fieldName, sectionName, parseListToArray(fieldValues));
    }

    @Given("^I populate field \"([^\"]*)\" in section \"([^\"]*)\" with \"([^\"]*)\"$")
    public void populateFieldInSectionWith(String fieldName, String sectionName, String fieldValue) {
        fixture.populateFieldInSectionWith(fieldName, sectionName, new String[] {fieldValue});
    }

    @Given("^I toggle section \"([^\"]*)\" visibility$")
    public void toggleSectionVisibility(String sectionName) {
        fixture.toggleSectionVisibility(sectionName);
    }

    @Given("^I toggle box \"([^\"]*)\" visibility$")
    public void toggleBoxVisibility(String boxName) {
        fixture.toggleBoxVisibility(boxName);
    }

    @Given("^I clear field \"([^\"]*)\"$")
    public void clearField(String fieldName) {
        fixture.clearField(fieldName);
    }

    @Given("^I clear field \"([^\"]*)\" of$")
    public void clearFieldOf(String fieldName, List<String> fieldValues) {
        fixture.clearFieldOf(fieldName, parseListToArray(fieldValues));
    }

    @Given("^I clear field \"([^\"]*)\" of \"([^\"]*)\"$")
    public void clearFieldOf(String fieldName, String fieldValue) {
        fixture.clearFieldOf(fieldName, new String[] {fieldValue});
    }

    @Given("^I verify tag field \"([^\"]*)\" is present$")
    public boolean verifyTagFieldIsPresent(String tagFiled) {
        if (!fixture.verifyTagFieldIsPresent(tagFiled)) {
            throw new RuntimeException("Could not verify tag field was present");
        }
        return true;
    }

    @Given("^I verify tag item \"([^\"]*)\" is present$")
    public boolean verifyTagItemIsPresent(String tagItem) {
        if (!fixture.verifyTagItemIsPresent(tagItem)) {
            throw new RuntimeException("Could not verify tag item was present");
        }
        return true;
    }

    @Given("^I click on tag field \"([^\"]*)\" tag item \"([^\"]*)\"$")
    public void clickOnTagFieldTagItem(String tagField, String tagItem) {
        fixture.clickOnTagFieldTagItem(tagField, tagItem);
    }

    @Given("^I click on tag item \"([^\"]*)\"$")
    public void clickOnTagItem(String tagItem) {
        fixture.clickOnTagItem(tagItem);
    }

    @Given("^I get field \"([^\"]*)\" value$")
    public String getFieldValue(String fieldName) {
        return fixture.getFieldValue(fieldName);
    }

    @Given("^I get field \"([^\"]*)\" value and store in excel \"([^\"]*)\"$")
    public void getFieldValueAndStoreInExcel(String fieldName, String excelRef) {

        // Capture value from UI
        String capturedValue = fixture.getFieldValue(fieldName);

        // Expect format: excel:SheetName.KeyName
        if (!excelRef.startsWith("excel:") || !excelRef.contains(".")) {
            throw new RuntimeException("Invalid excel reference format. Expected: excel:SheetName.KeyName");
        }

        // Parse reference → excel:SheetName.KeyName
        String ref = excelRef.replace("excel:", "");   // → VehicleData.Vehicle Category
        String sheetName = ref.substring(0, ref.indexOf("."));  // → VehicleData
        String keyName = ref.substring(ref.indexOf(".") + 1);   // → Vehicle Category

        // Store in Excel
        ExcelWriter.write("TestData.xlsx", sheetName, keyName, capturedValue);

        System.out.println("Stored field '" + fieldName + "' value '" + capturedValue +
                "' into sheet '" + sheetName + "' with key '" + keyName + "'");
    }


    @Given("^I get regex \"([^\"]*)\" group \"([^\"]*)\" from field \"([^\"]*)\" value$")
    public String getRegexGroupFromFieldValue(String regex, String group, String fieldName) {
        return fixture.getRegexGroupFromFieldValue(regex, Integer.parseInt(group), fieldName);
    }

    @Given("^I get field \"([^\"]*)\" in section \"([^\"]*)\" value$")
    public String getFieldInSectionValue(String fieldName, String sectionName) {
        return fixture.getFieldInSectionValue(fieldName, sectionName);
    }

    @Given("^I get regex \"([^\"]*)\" group \"([^\"]*)\" from field \"([^\"]*)\" in section \"([^\"]*)\" value$")
    public String getRegexGroupFromFieldInSectionValue(String regex, String group, String fieldName,
                                                       String sectionName) {
        return fixture.getRegexGroupFromFieldInSectionValue(regex, Integer.parseInt(group), fieldName, sectionName);
    }

    @Given("^I verify field \"([^\"]*)\" contains$")
    public boolean verifyFieldContains(String fieldName, List<String> fieldValues) {
        if (!fixture.verifyFieldContains(fieldName, parseListToArray(fieldValues))) {
            throw new RuntimeException("Could not verify field contains the expected values");
        }
        return true;
    }

    @Given("^I verify field \"([^\"]*)\" contains \"([^\"]*)\"$")
    public boolean verifyFieldContains(String fieldName, String fieldValue) {
        if (!fixture.verifyFieldContains(fieldName, new String[] {fieldValue})) {
            throw new RuntimeException("Could not verify field contains the expected value");
        }
        return true;
    }

    @Given("^I verify field \"([^\"]*)\" contains excel \"([^\"]*)\"$")
    public boolean verifyFieldContainsExcel(String fieldName, String fieldValue) {

        String finalValue = fieldValue.startsWith("excel:")
                ? TestDataManager.get(fieldValue.replace("excel:", ""))
                : fieldValue;

        if (!fixture.verifyFieldContains(fieldName, new String[]{finalValue})) {
            throw new RuntimeException("Verification failed: Expected field '"
                    + fieldName + "' to contain value '" + finalValue + "'");
        }
        return true;
    }


    @Given("^I verify field \"([^\"]*)\" contains value \"([^\"]*)\"$")
    public boolean verifyFieldContainsValue(String fieldName, String fieldValue) {
        if (!fixture.verifyFieldContainsValue(fieldName, fieldValue)) {
            throw new RuntimeException("Could not verify field contains the expected value");
        }
        return true;
    }

    @Given("^I verify field \"([^\"]*)\" is not blank$")
    public boolean verifyFieldIsNotBlank(String fieldName) {
        if (!fixture.verifyFieldIsNotBlank(fieldName)) {
            throw new RuntimeException("Could not verify field is not blank");
        }
        return true;
    }

    @Given("^I verify field \"([^\"]*)\" in section \"([^\"]*)\" contains$")
    public boolean verifyFieldInSectionContains(String fieldName, String sectionName, List<String> fieldValues) {
        if (!fixture.verifyFieldInSectionContains(fieldName, sectionName, parseListToArray(fieldValues))) {
            throw new RuntimeException("Could not verify field contains the expected values");
        }
        return true;
    }

    @Given("^I verify field \"([^\"]*)\" in section \"([^\"]*)\" contains \"([^\"]*)\"$")
    public boolean verifyFieldInSectionContains(String fieldName, String sectionName, String fieldValue) {
        if (!fixture.verifyFieldInSectionContains(fieldName, sectionName, new String[] {fieldValue})) {
            throw new RuntimeException("Could not verify field contains the expected value");
        }
        return true;
    }

    @Given("^I verify field \"([^\"]*)\" contains validation message$")
    public boolean verifyFieldContainsValidationMessage(String fieldName, List<String> validationMessages) {
        if (!fixture.verifyFieldContainsValidationMessage(fieldName, parseListToArray(validationMessages))) {
            throw new RuntimeException("Could not verify field contains the expected validation messages");
        }
        return true;
    }

    @Given("^I verify field \"([^\"]*)\" contains validation message \"([^\"]*)\"$")
    public boolean verifyFieldContainsValidationMessage(String fieldName, String validationMessage) {
        if (!fixture.verifyFieldContainsValidationMessage(fieldName, new String[] {validationMessage})) {
            throw new RuntimeException("Could not verify field contains the expected validation message");
        }
        return true;
    }

    @Given("^I get field \"([^\"]*)\" validation message$")
    public String getFieldValidationMessage(String fieldName) {
        return fixture.getFieldValidationMessage(fieldName);
    }

    @Given("^I verify field \"([^\"]*)\" is present$")
    public boolean verifyFieldIsPresent(String fieldName) {
        if (!fixture.verifyFieldIsPresent(fieldName)) {
            throw new RuntimeException("Could not verify field is present");
        }
        return true;
    }

    @Given("^I verify field \"([^\"]*)\" is not present$")
    public boolean verifyFieldIsNotPresent(String fieldName) {
        if (!fixture.verifyFieldIsNotPresent(fieldName)) {
            throw new RuntimeException("Could not verify field is not present");
        }
        return true;
    }

    @Given("^I populate grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" with$")
    public void populateGridColumnRowWith(String gridName, String columnName, String rowNum, List<String> fieldValues) {
        fixture.populateGridColumnRowWith(gridName, columnName, rowNum, parseListToArray(fieldValues));
    }

    @Given("^I populate grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" with \"([^\"]*)\"$")
    public void populateGridColumnRowWith(String gridName, String columnName, String rowNum, String fieldValue) {
        fixture.populateGridColumnRowWith(gridName, columnName, rowNum, new String[] {fieldValue});
    }

    @Given("^I populate grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" with partially matching picker field suggestions for$")
    public void populateGridColumnRowWithPartiallyMatchingPickerFieldSuggestion(String gridName, String columnName,
                                                                                String rowNum,
                                                                                List<String> fieldValues) {
        fixture.populateGridColumnRowWithPartiallyMatchingPickerFieldSuggestion(gridName, columnName, rowNum,
                parseListToArray(fieldValues));
    }

    @Given("^I populate grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" with partially matching picker field suggestions for \"([^\"]*)\"$")
    public void populateGridColumnRowWithPartiallyMatchingPickerFieldSuggestion(String gridName, String columnName,
                                                                                String rowNum, String fieldValue) {
        fixture.populateGridColumnRowWithPartiallyMatchingPickerFieldSuggestion(gridName, columnName, rowNum,
                new String[] {fieldValue});
    }

    @Given("^I populate grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" with value \"([^\"]*)\"$")
    public void populateGridColumnRowWithValue(String gridName, String columnName, String rowNum, String fieldValue) {
        fixture.populateGridColumnRowWithValue(gridName, columnName, rowNum, fieldValue);
    }

    @Given("^I clear grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\"$")
    public void clearGridColumnRow(String gridName, String columnName, String rowNum) {
        fixture.clearGridColumnRow(gridName, columnName, rowNum);
    }

    @Given("^I click on grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\"$")
    public void clickOnGridColumnRow(String gridName, String columnName, String rowNum) {
        fixture.clickOnGridColumnRow(gridName, columnName, rowNum);
    }

    @Given("^I get grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" value$")
    public String getGridColumnRowValue(String gridName, String columnName, String rowNum) {
        return fixture.getGridColumnRowValue(gridName, columnName, rowNum);
    }

    @Given("^I get grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" value and store in excel \"([^\"]*)\"$")
    public void getGridColumnRowValueAndStoreExcel(
            String gridName,
            String columnName,
            String rowNum,
            String excelRef) {

        // Get the UI value from grid
        String uiValue = fixture.getGridColumnRowValue(gridName, columnName, rowNum);

        // Parse excel reference "excel:SheetName.KeyName"
        if (!excelRef.startsWith("excel:")) {
            throw new RuntimeException("Excel reference must start with 'excel:' → provided: " + excelRef);
        }

        String ref = excelRef.replace("excel:", "");  // VehicleData.Vehicle Category
        String[] parts = ref.split("\\.");

        if (parts.length != 2) {
            throw new RuntimeException("Excel reference must be in format excel:Sheet.Key → provided: " + excelRef);
        }

        String sheet = parts[0];
        String key = parts[1];

        // Write / Update Excel row
        ExcelWriter.write("TestData.xlsx", sheet, key, uiValue);

        System.out.println("Stored Grid Value in Excel → Sheet: " + sheet + ", Key: " + key + ", Value: " + uiValue);
    }

    @Given("^I get regex \"([^\"]*)\" group \"([^\"]*)\" from grid \"([^\"]*)\" column \"([^\"]*)\" Row \"([^\"]*)\" value$")
    public String getRegexGroupFromGridColumnRowValue(String regex, String group, String gridName, String columnName,
                                                      String rowNum) {
        return fixture.getRegexGroupFromGridColumnRowValue(regex, Integer.parseInt(group), gridName, columnName,
                rowNum);
    }

    @Given("^I verify grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" contains$")
    public boolean verifyGridColumnRowContains(String gridName, String columnName, String rowNum,
                                               List<String> fieldValues) {
        if (!fixture.verifyGridColumnRowContains(gridName, columnName, rowNum, parseListToArray(fieldValues))) {
            throw new RuntimeException("Could not verify grid cell contains the expected values");
        }
        return true;
    }

    @Given("^I verify grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" contains \"([^\"]*)\"$")
    public boolean verifyGridColumnRowContains(String gridName, String columnName, String rowNum, String fieldValue) {
        if (!fixture.verifyGridColumnRowContains(gridName, columnName, rowNum, new String[] {fieldValue})) {
            throw new RuntimeException("Could not verify grid cell contains the expected value");
        }
        return true;
    }

    @Given("^I verify grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" contains excel data \"([^\"]*)\"$")
    public boolean verifyGridColumnRowContainsExcelData(
            String gridName,
            String columnName,
            String rowNum,
            String excelRef) {

        // Must start with excel:
        if (!excelRef.startsWith("excel:")) {
            throw new RuntimeException("Excel reference must start with 'excel:' → provided: " + excelRef);
        }

        // Remove prefix
        String cleaned = excelRef.replace("excel:", "");  // e.g., ApplicationGridData.Last Updated User
        String[] parts = cleaned.split("\\.");

        if (parts.length != 2) {
            throw new RuntimeException("Excel reference must be in format excel:Sheet.Key → provided: " + excelRef);
        }

        // Extract sheet + key
        String sheet = parts[0];
        String key   = parts[1];

        // Fetch expected value from Excel
        String expectedValue = TestDataManager.get(sheet, key);

        // Perform UI verification
        boolean result = fixture.verifyGridColumnRowContains(
                gridName,
                columnName,
                rowNum,
                new String[]{expectedValue}
        );

        if (!result) {
            throw new RuntimeException("Verification failed: Grid [" + gridName + "] column [" +
                    columnName + "] row [" + rowNum + "] DOES NOT contain expected Excel value: " + expectedValue);
        }

        return true;
    }

    @Given("^I verify grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" contains value \"([^\"]*)\"$")
    public boolean verifyGridColumnRowContainsValue(String gridName, String columnName, String rowNum,
                                                    String fieldValue) {
        if (!fixture.verifyGridColumnRowContainsValue(gridName, columnName, rowNum, fieldValue)) {
            throw new RuntimeException("Could not verify grid cell contains the expected value");
        }
        return true;
    }

    @Given("^I select grid \"([^\"]*)\" row \"([^\"]*)\"$")
    public void selectGridRow(String gridName, String rowNum) {
        fixture.selectGridRow(gridName, rowNum);
    }

    @Given("^I verify grid \"([^\"]*)\" row \"([^\"]*)\" is selected$")
    public boolean verifyGridRowIsSelected(String gridName, String rowNum) {
        if (!fixture.verifyGridRowIsSelected(gridName, rowNum)) {
            throw new RuntimeException("Could not verify grid row is selected");
        }
        return true;
    }

    @Given("^I count grid \"([^\"]*)\" rows$")
    public Integer countGridRows(String gridName) {
        return fixture.countGridRows(gridName);
    }

    @Given("^I get grid \"([^\"]*)\" total count$")
    public int getGridTotalCount(String gridName) {
        return fixture.getGridTotalCount(gridName);
    }

    @Given("^I get grid \"([^\"]*)\" row count$")
    public int getGridRowCount(String gridName) {
        return fixture.getGridRowCount(gridName);
    }

    @Given("^I click on grid \"([^\"]*)\" add row link$")
    public void clickOnGridAddRowLink(String gridName) {
        fixture.clickOnGridAddRowLink(gridName);
    }

    @Given("^I click on grid \"([^\"]*)\" navigation \"([^\"]*)\"$")
    public void clickOnGridNavigation(String gridName, String navOption) {
        fixture.clickOnGridNavigation(gridName, navOption);
    }

    @Given("^I select all rows in grid \"([^\"]*)\"$")
    public void selectAllRowsInGrid(String gridName) {
        fixture.selectAllRowsInGrid(gridName);
    }

    @Given("^I sort grid \"([^\"]*)\" by column \"([^\"]*)\"$")
    public void sortGridByColumn(String gridName, String columnName) {
        fixture.sortGridByColumn(gridName, columnName);
    }

    @Given("^I verify grid \"([^\"]*)\" column \"([^\"]*)\" row \"([^\"]*)\" is not blank$")
    public boolean verifyGridColumnRowIsNotBlank(String gridName, String columnName, String rowNum) {
        if (!fixture.verifyGridColumnRowIsNotBlank(gridName, columnName, rowNum)) {
            throw new RuntimeException("Could not verify grid cell is not blank");
        }
        return true;
    }

    @Given("^I click on link \"([^\"]*)\"$")
    public void clickOnLink(String linkName) {
        fixture.clickOnLink(linkName);
    }

    @Given("^I verify link \"([^\"]*)\" is present$")
    public boolean verifyLinkIsPresent(String linkName) {
        if (!fixture.verifyLinkIsPresent(linkName)) {
            throw new RuntimeException("Could not verify link is present");
        }
        return true;
    }

    @Given("^I verify link \"([^\"]*)\" is not present$")
    public boolean verifyLinkIsNotPresent(String linkName) {
        if (!fixture.verifyLinkIsNotPresent(linkName)) {
            throw new RuntimeException("Could not verify link is not present");
        }
        return true;
    }

    @Given("^I verify link \"([^\"]*)\" URL contains \"([^\"]*)\"$")
    public boolean verifyLinkURLContains(String linkName, String urlText) {
        if (!fixture.verifyLinkURLContains(linkName, urlText)) {
            throw new RuntimeException("Could not verify link URL contains expected value");
        }
        return true;
    }

    @Given("^I get link \"([^\"]*)\" URL$")
    public String getLinkURL(String linkName) {
        return fixture.getLinkURL(linkName);
    }

    @Given("^I click on icon link \"([^\"]*)\"$")
    public void clickOnIconLink(String iconAltTextName) {
        fixture.clickOnIconLink(iconAltTextName);
    }

    @Given("^I click on document image link \"([^\"]*)\"$")
    public void clickOnDocumentImageLink(String documentImageLinkAltTextName) {
        fixture.clickOnDocumentImageLink(documentImageLinkAltTextName);
    }

    @Given("^I click on bar chart \"([^\"]*)\" bar \"([^\"]*)\"$")
    public void clickOnBarChartBar(String barChartNumber, String barNumber) {
        fixture.clickOnBarChartBar(barChartNumber, barNumber);
    }

    @Given("^I click on column chart \"([^\"]*)\" column \"([^\"]*)\"$")
    public void clickOnColumnChartColumn(String columnChartNumber, String columnNumber) {
        fixture.clickOnColumnChartColumn(columnChartNumber, columnNumber);
    }

    @Given("^I click on pie chart \"([^\"]*)\" pie slice \"([^\"]*)\"$")
    public void clickOnPieChartPieSlice(String pieChartNumber, String pieSliceNumber) {
        fixture.clickOnPieChartPieSlice(pieChartNumber, pieSliceNumber);
    }

    @Given("^I click on line chart \"([^\"]*)\" point \"([^\"]*)\"$")
    public void clickOnLineChartPoint(String lineChartNumber, String pointNumber) {
        fixture.clickOnLineChartPoint(lineChartNumber, pointNumber);
    }

    @Given("^I click on record action field \"([^\"]*)\" menu action \"([^\"]*)\"$")
    public void clickOnRecordActionFieldMenuAction(String indexOfField, String action) {
        fixture.clickOnRecordActionFieldMenuAction(indexOfField, action);
    }

    @Given("^I populate dropdown \"([^\"]*)\" search box with \"([^\"]*)\"$")
    public void populateDropdownSearchBoxWith(String fieldName, String searchValue) {
        fixture.populateDropdownSearchBoxWith(fieldName, searchValue);
    }

    @Given("^I verify confirmation dialog header \"([^\"]*)\" is present$")
    public boolean verifyConfirmationDialogHeaderIsPresent(String headerText) {
        return fixture.verifyConfirmationDialogHeaderIsPresent(headerText);
    }

    @Given("^I verify confirmation dialog message \"([^\"]*)\" is present$")
    public boolean verifyConfirmationDialogMessageIsPresent(String mesageText) {
        return fixture.verifyConfirmationDialogMessageIsPresent(mesageText);
    }

    @Given("^I click on button \"([^\"]*)\"$")
    public void clickOnButton(String buttonName) {
        fixture.clickOnButton(buttonName);
    }

    @Given("^I click on button from excel \"([^\"]*)\"$")
    public void clickOnButtonFromExcel(String excelRef) {

        // Only process excel reference if it starts with "excel:"
        String buttonName = excelRef.startsWith("excel:")
                ? TestDataManager.get(excelRef.replace("excel:", ""))
                : excelRef;

        fixture.clickOnButton(buttonName);
    }

    @Given("^I click on button with tooltip \"([^\"]*)\"$")
    public void clickOnButtonWithTooltip(String tooltip) {
        fixture.clickOnButtonWithTooltip(tooltip);
    }

    @Given("^I verify button \"([^\"]*)\" is present$")
    public boolean verifyButtonIsPresent(String buttonName) {
        if (!fixture.verifyButtonIsPresent(buttonName)) {
            throw new RuntimeException("Could not verify button is present");
        }
        return true;
    }

    @Given("^I verify button \"([^\"]*)\" is not present$")
    public boolean verifyButtonIsNotPresent(String buttonName) {
        if (!fixture.verifyButtonIsNotPresent(buttonName)) {
            throw new RuntimeException("Could not verify button is not present");
        }
        return true;
    }

    @Given("^I verify button \"([^\"]*)\" is enabled$")
    public boolean verifyButtonIsEnabled(String buttonName) {
        if (!fixture.verifyButtonIsEnabled(buttonName)) {
            throw new RuntimeException("Could not verify button is enabled");
        }
        return true;
    }

    @Given("^I verify button \"([^\"]*)\" is disabled$")
    public boolean verifyButtonIsDisabled(String buttonName) {
        if (!fixture.verifyButtonIsDisabled(buttonName)) {
            throw new RuntimeException("Could not verify button is disabled");
        }
        return true;
    }

    @Given("^I click on save changes$")
    public void clickOnSaveChanges() {
        fixture.clickOnSaveChanges();
    }

    @Given("^I click on milestone \"([^\"]*)\" step \"([^\"]*)\"$")
    public void clickOnMilestoneStep(String milestone, String step) {
        fixture.clickOnMilestoneStep(milestone, step);
    }

    @Given("^I click on card \"([^\"]*)\"$")
    public void clickOnCard(String linkName) {
        fixture.clickOnCard(linkName);
    }

    @Given("^I click on card direct \"([^\"]*)\"$")
    public void clickOnCardDirect(String cardName) {
        fixture.clickOnCardDirect(cardName);
    }

    @Given("^I click on card direct from excel \"([^\"]*)\"$")
    public void clickOnCardDirectFromExcel(String excelRef) {

        // Resolve Excel reference (sheet.key)
        String finalCardName = excelRef.startsWith("excel:")
                ? TestDataManager.get(excelRef.replace("excel:", ""))
                : excelRef;

        fixture.clickOnCardDirect(finalCardName);
    }


    @Given("^I click on text \"([^\"]*)\"$")
    public void clickOnText(String text) {
        fixture.clickOnText(text);
    }

    @Given("^I click on element with text from excel \"([^\"]*)\"$")
    public void clickOnElementWithTextFromExcel(String excelRef) {

        // Resolve Excel reference (sheet.key)
        String finalText = excelRef.startsWith("excel:")
                ? TestDataManager.get(excelRef.replace("excel:", ""))
                : excelRef;

        fixture.clickOnText(finalText);
    }


    @Given("^I click on term card \"([^\"]*)\"$")
    public void clickOnTermCard(String cardName) {fixture.clickOnTermCard(cardName);
    }

    @Given("^I click on term card from excel \"([^\"]*)\"$")
    public void clickOnTermCardFromExcel(String excelRef) {

        // Resolve sheet.key from TestData.xlsx
        String finalCardName = excelRef.startsWith("excel:")
                ? TestDataManager.get(excelRef.replace("excel:", ""))
                : excelRef;

        fixture.clickOnTermCard(finalCardName);
    }

    @Given("^I click on card choice\"([^\"]*)\" field \"([^\"]*)\"$")
    public void clickOnCardChoice(String cardName, String fieldName) { fixture.clickOnCardChoice(cardName, fieldName);
    }

    @Given("^I verify milestone \"([^\"]*)\" step is$")
    public boolean verifyMilestoneStepIs(String milestone, List<String> step) {
        if (!fixture.verifyMilestoneStepIs(milestone, parseListToArray(step))) {
            throw new RuntimeException("Could not verify milestone step is the expected steps");
        }
        return true;
    }

    @Given("^I verify milestone \"([^\"]*)\" step is \"([^\"]*)\"$")
    public boolean verifyMilestoneStepIs(String milestone, String step) {
        if (!fixture.verifyMilestoneStepIs(milestone, new String[] {step})) {
            throw new RuntimeException("Could not verify milestone step is the expected step");
        }
        return true;
    }

    @Given("^I verify gauge field \"([^\"]*)\" percentage is \"([^\"]*)\"$")
    public boolean verifyGaugeFieldPercentageIs(String gaugeField, String percentage) {
        if (!fixture.verifyGaugeFieldPercentageIs(gaugeField, percentage)) {
            throw new RuntimeException("Could not verify gauge field percentage is the expected percentage");
        }
        return true;
    }

    @Given("^I get gauge field \"([^\"]*)\" percentage$")
    public String getGaugeFieldPercentage(String gaugeField) {
        return fixture.getGaugeFieldPercentage(gaugeField);
    }

    @Given("^I verify stamp field \"([^\"]*)\" is present$")
    public boolean verifyStampFieldIsPresent(String stampField) {
        if (!fixture.verifyStampFieldIsPresent(stampField)) {
            throw new RuntimeException("Could not verify stamp field was present");
        }
        return true;
    }

    @Given("^I verify stamp field \"([^\"]*)\" contains text \"([^\"]*)\"$")
    public boolean verifyStampFieldContainsText(String stampField, String text) {
        if (fixture.verifyStampFieldContainsText(stampField, text)) {
            throw new RuntimeException("Could not verify stamp field contained the expected text");
        }
        return true;
    }

    @Given("^I get milestone \"([^\"]*)\" step$")
    public String getMilestoneStep(String milestone) {
        return fixture.getMilestoneStep(milestone);
    }

    @Given("^I click on radio option \"([^\"]*)\"$")
    public void clickOnRadioOption(String optionName) {
        fixture.clickOnRadioOption(optionName);
    }

    @Given("^I click on radio option from excel \"([^\"]*)\"$")
    public void clickOnRadioOptionFromExcel(String excelRef) {

        // Resolve the value from Excel
        String finalOption = excelRef.startsWith("excel:")
                ? TestDataManager.get(excelRef.replace("excel:", ""))
                : excelRef;

        fixture.clickOnRadioOption(finalOption);
    }

    @Given("^I click on checkbox option \"([^\"]*)\"$")
    public void clickOnCheckboxOption(String optionName) {
        fixture.clickOnCheckboxOption(optionName);
    }

    @Given("^I verify section \"([^\"]*)\" contains validation message$")
    public boolean verifySectionContainsValidationMessage(String sectionName, List<String> validationMessages) {
        if (!fixture.verifySectionContainsValidationMessage(sectionName, parseListToArray(validationMessages))) {
            throw new RuntimeException("Could not verify section contains the expected validation messages");
        }
        return true;
    }

    @Given("^I verify section \"([^\"]*)\" contains validation message \"([^\"]*)\"$")
    public boolean verifySectionContainsValidationMessage(String sectionName, String validationMessage) {
        if (!fixture.verifySectionContainsValidationMessage(sectionName, new String[] {validationMessage})) {
            throw new RuntimeException("Could not verify section contains the expected validation message");
        }
        return true;
    }

    public String getSectionValidationMessage(String sectionName) {
        return fixture.getSectionValidationMessage(sectionName);
    }

    @Given("^I verify section \"([^\"]*)\" is present$")
    public boolean verifySectionIsPresent(String sectionLabel) {
        if (!fixture.verifySectionIsPresent(sectionLabel)) {
            throw new RuntimeException("Could not verify section is present");
        }
        return true;
    }

    @Given("^I verify section \"([^\"]*)\" is not present$")
    public boolean verifySectionIsNotPresent(String sectionLabel) {
        if (!fixture.verifySectionIsNotPresent(sectionLabel)) {
            throw new RuntimeException("Could not verify section is not present");
        }
        return true;
    }

    @Given("^I verify box \"([^\"]*)\" is present$")
    public boolean verifyBoxIsPresent(String boxLabel) {
        if (!fixture.verifyBoxIsPresent(boxLabel)) {
            throw new RuntimeException("Could not verify box is present");
        }
        return true;
    }

    @Given("^I verify box \"([^\"]*)\" is not present$")
    public boolean verifyBoxIsNotPresent(String boxLabel) {
        if (!fixture.verifyBoxIsNotPresent(boxLabel)) {
            throw new RuntimeException("Could not verify box is not present");
        }
        return true;
    }

    @Given("^I verify card \"([^\"]*)\" is present$")
    public boolean verifyCardIsPresent(String cardLabel) {
        if (!fixture.verifyCardIsPresent(cardLabel)) {
            throw new RuntimeException("Could not verify card is present");
        }
        return true;
    }

    @Given("^I verify card \"([^\"]*)\" is not present$")
    public boolean verifyCardIsNotPresent(String cardLabel) {
        if (!fixture.verifyCardIsNotPresent(cardLabel)) {
            throw new RuntimeException("Could not verify card is not present");
        }
        return true;
    }

    @Given("^I verify chart \"([^\"]*)\" is present$")
    public boolean verifyChartIsPresent(String chartLabel) {
        if (!fixture.verifyChartIsPresent(chartLabel)) {
            throw new RuntimeException("Could not verify chart is present");
        }
        return true;
    }

    @Given("^I verify chart \"([^\"]*)\" is not present$")
    public boolean verifyChartIsNotPresent(String chartLabel) {
        if (!fixture.verifyChartIsNotPresent(chartLabel)) {
            throw new RuntimeException("Could not verify chart is not present");
        }
        return true;
    }

    @Given("^I verify video \"([^\"]*)\" is present$")
    public boolean verifyVideoIsPresent(String videoSource) {
        if (!fixture.verifyVideoIsPresent(videoSource)) {
            throw new RuntimeException("Could not verify video is present");
        }
        return true;
    }

    @Given("^I verify video \"([^\"]*)\" is not present$")
    public boolean verifyVideoIsNotPresent(String videoSource) {
        if (!fixture.verifyVideoIsNotPresent(videoSource)) {
            throw new RuntimeException("Could not verify video is not present");
        }
        return true;
    }

    @Given("^I verify web content \"([^\"]*)\" is present$")
    public boolean verifyWebContentIsPresent(String webContentSource) {
        if (!fixture.verifyWebContentIsPresent(webContentSource)) {
            throw new RuntimeException("Could not verify web content is present");
        }
        return true;
    }

    @Given("^I verify web content \"([^\"]*)\" is not present$")
    public boolean verifyWebContentIsNotPresent(String webContentSource) {
        if (!fixture.verifyWebContentIsNotPresent(webContentSource)) {
            throw new RuntimeException("Could not verify web content is not present");
        }
        return true;
    }

    public boolean errorIsPresent() {
        return fixture.errorIsPresent();
    }

    @Given("^I verify text \"([^\"]*)\" is present$")
    public boolean verifyTextIsPresent(String textOnInterface) {
        if (!fixture.verifyTextIsPresent(textOnInterface)) {
            throw new RuntimeException("Could not verify text is present");
        }
        return true;
    }

    @Given("^I verify text \"([^\"]*)\" is not present$")
    public boolean verifyTextIsNotPresent(String textOnInterface) {
        if (!fixture.verifyTextIsNotPresent(textOnInterface)) {
            throw new RuntimeException("Could not verify text is not present");
        }
        return true;
    }

    @Given("^I verify signature field \"([^\"]*)\" is present$")
    public boolean verifySignatureFieldIsPresent(String label) {
        if (!fixture.verifySignatureFieldIsPresent(label)) {
            throw new RuntimeException("Could not verify signature field is present");
        }
        return true;
    }

    @Given("^I click on signature field \"([^\"]*)\"$")
    public void clickOnSignatureField(String label) {
        fixture.clickOnSignatureField(label);
    }

    @Given("^I draw signature$")
    public void drawSignature() {
        fixture.drawSignature();
    }

    public static Settings getSettings() {
        return fixture.getSettings();
    }

    public String[] parseListToArray(List<String> table) {
        return table.toArray(new String[table.size()]);
    }
}

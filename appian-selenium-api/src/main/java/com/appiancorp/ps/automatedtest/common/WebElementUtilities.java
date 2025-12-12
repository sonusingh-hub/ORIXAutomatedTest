package com.appiancorp.ps.automatedtest.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public final class WebElementUtilities {

    private WebElementUtilities() {
    }

    public static boolean xpathExistsInField(String xpath, WebElement fieldLayout) {
        try {
            fieldLayout.findElement(By.xpath(xpath));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

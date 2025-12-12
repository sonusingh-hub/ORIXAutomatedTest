package com.appiancorp.ps.automatedtest.properties;

public interface WaitForReturn extends WaitFor {

    public boolean waitForReturn(boolean waitForPresent, int timeout, String... params);

    public boolean waitForReturn(boolean waitForPresent, String... params);

}

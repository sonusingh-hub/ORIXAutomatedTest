package com.appiancorp.ps.automatedtest.properties;

import org.json.JSONObject;

public interface ContainerCaptureable {

    public JSONObject getContents(String... params);
}

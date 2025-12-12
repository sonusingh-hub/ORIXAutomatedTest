package com.appiancorp.ps.automatedtest.test;

import com.appiancorp.ps.automatedtest.common.Constants;
import com.appiancorp.ps.automatedtest.common.PropertiesUtilities;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RecordingDownloadExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final Logger LOG = LogManager.getLogger(RecordingDownloadExtension.class);
    private final HttpClient client = HttpClient.newHttpClient();
    private String jobId = null;
    private String fileName = null;
    private final String videoUrl = String.format("http://%s:%s/video/", "localhost", "6101");

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        Pair<String, String> videoInfo = startVideoRecording(extensionContext.getDisplayName());
        this.jobId = videoInfo.getLeft();
        this.fileName = videoInfo.getRight();
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        if (this.jobId != null) {
            endRecording(this.jobId);
            if (extensionContext.getExecutionException().isPresent()) {
                downloadVideo(this.jobId, this.fileName);
            }
        }
    }


    public Pair<String, String> startVideoRecording(String testName) {
        String jobId = null;
        String videoFileName = null;

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
        String fileName = String.format("%s_%s.mp4", testName, time);
        JSONObject payload = new JSONObject();
        payload.put("file_name", fileName);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.videoUrl + "start"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();

        String r = sendRequest(request, HttpResponse.BodyHandlers.ofString());
        if (r != null) {
            JSONObject response = new JSONObject(r);
            JSONObject message = response.getJSONObject("message");
            jobId = message.getString("id");
            videoFileName = message.getString("file_name");
        }
        return Pair.of(jobId, videoFileName);
    }

    private void endRecording(String jobId) {
        JSONObject payload = new JSONObject();
        payload.put("id", jobId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.videoUrl + "stop"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        sendRequest(request, HttpResponse.BodyHandlers.ofString());
    }

    private <T> T sendRequest(HttpRequest request, HttpResponse.BodyHandler<T> handler) {
        try {
            HttpResponse<T> response = client.send(request, handler);
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                LOG.warn("Video server returned ({}): {}", response.statusCode(), response.body().toString());
            }
        } catch (java.net.http.HttpConnectTimeoutException e) {
            LOG.warn("Video rest server is not available or not able to connect");
        } catch (Exception e) {
            LOG.warn("An error occurred: {}", e.getMessage());
        }
        return null;
    }

    private void downloadVideo(String jobId, String fileName) {
        File videoDir = new File(PropertiesUtilities.getProps().getProperty(Constants.DOWNLOAD_DIRECTORY));
        if (!videoDir.exists()) {
            videoDir.mkdir();
        }

        String videoApiUrl = this.videoUrl + "video";
        JSONObject payload = new JSONObject();
        payload.put("id", jobId);

        String localPath = new File(videoDir, fileName).getAbsolutePath();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(videoApiUrl))
                .method("GET", HttpRequest.BodyPublishers.ofString(payload.toString()))
                .header("Content-Type", "application/json")
                .build();

        try {
            try (InputStream in = sendRequest(request, HttpResponse.BodyHandlers.ofInputStream());
                 FileOutputStream out = new FileOutputStream(localPath)) {
                if (in != null) {
                    in.transferTo(out);
                }
            }
        } catch (IOException e) {
            LOG.warn("Error occurred while getting file: {}", e.getMessage());
        }
    }
}

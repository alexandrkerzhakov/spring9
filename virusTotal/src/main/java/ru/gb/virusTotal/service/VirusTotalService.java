package ru.gb.virusTotal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.virusTotal.properties.VirusTotalProperties;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class VirusTotalService {

    private final Logger logger = LoggerFactory.getLogger(VirusTotalService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private VirusTotalProperties virusTotalProperties;

    public Request createGetRequest(String baseUrl, String endpoint, String requestedParameter, String additionalPartForUrl) {
        String URL = baseUrl + endpoint + requestedParameter + additionalPartForUrl;
        logger.info("createGetRequest on {}", URL);
        Request.Builder requestBuilder = new Request.Builder()
                .url(URL)
                .get()
                .header("x-apikey", virusTotalProperties.getAPI_KEY())
                .addHeader("accept", "application/json");
        return requestBuilder.build();
    }

    public Optional<String> getResponseString(Request request) {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
        OkHttpClient okHttpClient = okHttpClientBuilder.build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            return Optional.ofNullable(response.body())
                    .map(responseBody -> {
                        try {
                            String responseString = responseBody.string();
                            logger.info("getResponseString {}", responseString);
                            return responseString;
                        } catch (IOException e) {
                            logger.error("Error occurred while fetching response", e);
                            return null;
                        }
                    });
        } catch (IOException e) {
            logger.error("Error occurred while fetching response", e);
            return Optional.empty();
        }
    }

    public <T> T getInstance(String json, Class<T> tClass) {
        T instance = null;
        try {
            instance = objectMapper.readValue(json, tClass);
            logger.info("getInstance {}", instance);
        } catch (JsonProcessingException e) {
            logger.error("Error processing getInstance JSON: {}", e.getMessage(), e);
        }
        return instance;
    }

    public Request createPostRequest(String baseUrl, String endpoint, MultipartFile file) throws IOException {
        String URL = baseUrl + endpoint;
        logger.info("createPostRequest on {}", URL);

        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getOriginalFilename(),
                        okhttp3.RequestBody.create(file.getBytes(), MediaType.parse("application/octet-stream")));

        Request.Builder requestBuilder = new Request.Builder()
                .url(URL)
                .post(multipartBuilder.build())
                .header("x-apikey", virusTotalProperties.getAPI_KEY())
                .addHeader("content-type", "multipart/form-data")
                .addHeader("accept", "application/json");

        return requestBuilder.build();

    }
}

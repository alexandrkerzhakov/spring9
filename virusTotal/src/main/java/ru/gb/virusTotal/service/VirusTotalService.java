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
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class VirusTotalService {

    private final Logger logger = LoggerFactory.getLogger(VirusTotalService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private VirusTotalProperties virusTotalProperties;

    public Request createGetRequest(String endpoint, String requestedParameter, String additionalPartForUrl) {
        String Url = createUrl(virusTotalProperties.getBASE_URL(), endpoint, requestedParameter, additionalPartForUrl);
        logger.info("createGetRequest on {}", Url);
        return getRequest(Url);
    }

    public String createUrl(String... parts) {
        return String.join("", parts);
    }

    public Request getRequest(String Url) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(Url)
                .get()
                .header("x-apikey", virusTotalProperties.getAPI_KEY())
                .addHeader("accept", "application/json");
        return requestBuilder.build();
    }

    public Optional<String> createResponseString(Request request) {
        OkHttpClient okHttpClient = createOkHttpClient();

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

    public OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);
        return okHttpClientBuilder.build();
    }

    public <T> T getInstance(String json, Class<T> tClass) {
        T instance = null;
        try {
            instance = objectMapper.readValue(json, tClass);
            logger.info("getInstance {}", instance);
        } catch (JsonProcessingException e) {
            logger.error("Error processing in getInstance: {}", e.getMessage(), e);
        }
        return instance;
    }

    public Request createPostRequest(String endpoint, MultipartFile file) throws IOException {
        String Url = createUrl(virusTotalProperties.getBASE_URL(), endpoint);
        logger.info("createPostRequest on {}", Url);
        MultipartBody multipartBody = createMultipartBodyForPostRequest(file);
        return postRequest(Url, multipartBody);
    }

    public Request createOtherPostRequest(String dopUrl, MultipartFile file) throws IOException {
        logger.info("createDopPostRequest on {}", dopUrl);
        MultipartBody multipartBody = createMultipartBodyForPostRequest(file);
        return postRequest(dopUrl, multipartBody);
    }

    public Request postRequest(String Url, MultipartBody multipartBody) {
        Request.Builder requestBuilder = new Request.Builder()
                .url(Url)
                .post(multipartBody)
                .header("x-apikey", virusTotalProperties.getAPI_KEY())
                .addHeader("accept", "application/json");
        return requestBuilder.build();
    }

    public MultipartBody createMultipartBodyForPostRequest(MultipartFile multipartFile) throws IOException {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
        return multipartBuilder
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", multipartFile.getOriginalFilename(),
                        okhttp3.RequestBody.create(multipartFile.getBytes(), MediaType.parse("application/octet-stream")))
                .build();
    }
}

package ru.gb.kaspersky;

import okhttp3.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import ru.gb.kaspersky.model.DomainInfo;
import ru.gb.kaspersky.model.IpInfo;
import ru.gb.kaspersky.properties.KasperskyProperties;
import ru.gb.kaspersky.service.KasperskyService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class KasperskyApplicationTests {

    @Mock
    private KasperskyProperties kasperskyProperties;
    @Autowired
    private KasperskyService kasperskyService;

    @BeforeEach
    public void setUp() {
        when(kasperskyProperties.getAPI_KEY()).thenReturn("jBRRo//wQ1ewWHTda5322Q==");
        when(kasperskyProperties.getBASE_URL()).thenReturn("https://opentip.kaspersky.com/api/v1/");
    }

    @Test
    public void createGetRequestTest() {
        Request request = kasperskyService.createGetRequest("search/", "ip?request=", "185.220.101.42");

        assertEquals(kasperskyProperties.getBASE_URL() + "search/ip?request=185.220.101.42", request.url().toString());
        assertEquals("GET", request.method());
        assertEquals(kasperskyProperties.getAPI_KEY(), request.headers().get("x-api-key"));
        assertEquals("application/json", request.headers().get("accept"));
    }


    @Test
    public void createPostRequestTest() throws IOException {
        Request request = kasperskyService.createPostRequest("scan/", "file?filename=",
                new MockMultipartFile("file", "test.txt", "text/plain",
                        "text".getBytes(StandardCharsets.UTF_8)));

        assertEquals(kasperskyProperties.getBASE_URL() + "scan/file?filename=test.txt", request.url().toString());
        assertEquals("POST", request.method());
        assertEquals(kasperskyProperties.getAPI_KEY(), request.headers().get("x-api-key"));
        assertEquals("application/json", request.headers().get("accept"));
    }

    @Test
    public void getInstanceIpInfoTest() {
        Request request = kasperskyService.createGetRequest("search/", "ip?request=", "185.220.101.42");
        String jsonString = kasperskyService.getResponseString(request).orElseThrow();
        IpInfo ipInfo = kasperskyService.getInstance(jsonString, IpInfo.class);

        assertEquals("185.220.101.42", ipInfo.getIpGeneralInfo().getIp());
    }

    @Test
    public void getInstanceDomainInfoTest() {
        Request request = kasperskyService.createGetRequest("search/", "domain?request=", "yandex.ru");
        String jsonString = kasperskyService.getResponseString(request).orElseThrow();
        DomainInfo domainInfo = kasperskyService.getInstance(jsonString, DomainInfo.class);

        assertEquals("yandex.ru", domainInfo.getDomainGeneralInfo().getDomain());
    }
}


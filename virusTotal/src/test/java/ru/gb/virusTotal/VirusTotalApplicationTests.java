package ru.gb.virusTotal;

import okhttp3.MultipartBody;
import okhttp3.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import ru.gb.virusTotal.model.DomainInfo;
import ru.gb.virusTotal.model.IpInfo;
import ru.gb.virusTotal.properties.VirusTotalProperties;
import ru.gb.virusTotal.service.VirusTotalService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class VirusTotalApplicationTests {

	@Mock
	private VirusTotalProperties virusTotalProperties;
	@Autowired
	private VirusTotalService virusTotalService;

	@BeforeEach
	public void setUp() {
		when(virusTotalProperties.getAPI_KEY()).thenReturn("05c9a3dde9d7257a4968674470599f6639f35dfb14477c924da548ea4f85e2a9");
		when(virusTotalProperties.getBASE_URL()).thenReturn("https://www.virustotal.com/api/v3/");
	}

	@Test
	public void createGetRequestTest() {
		Request request = virusTotalService.createGetRequest("ip_addresses/", "185.220.101.42", "");

		assertEquals(virusTotalProperties.getBASE_URL() + "ip_addresses/185.220.101.42", request.url().toString());
		assertEquals("GET", request.method());
		assertEquals(virusTotalProperties.getAPI_KEY(), request.headers().get("x-apikey"));
		assertEquals("application/json", request.headers().get("accept"));
	}


	@Test
	public void createPostRequestTest() throws IOException {
		Request request = virusTotalService.createPostRequest( "files",
				new MockMultipartFile("file","test.txt", "text/plain",
						"text".getBytes(StandardCharsets.UTF_8)));

		assertEquals(virusTotalProperties.getBASE_URL() + "files", request.url().toString());
		assertEquals("POST", request.method());
		assertEquals(virusTotalProperties.getAPI_KEY(), request.headers().get("x-apikey"));
		assertEquals("application/json", request.headers().get("accept"));
	}

	@Test
	public void getInstanceIpInfoTest() {
		Request request = virusTotalService.createGetRequest("ip_addresses/", "185.220.101.42", "");
		String jsonString = virusTotalService.createResponseString(request).orElseThrow();
		IpInfo ipInfo = virusTotalService.getInstance(jsonString, IpInfo.class);

		assertEquals("185.220.101.42", ipInfo.getData().getId());
		assertEquals("ip_address", ipInfo.getData().getType());
	}

	@Test
	public void getInstanceDomainInfoTest() {
		Request request = virusTotalService.createGetRequest("domains/", "yandex.ru", "");
		String jsonString = virusTotalService.createResponseString(request).orElseThrow();
		DomainInfo domainInfo = virusTotalService.getInstance(jsonString, DomainInfo.class);

		assertEquals("yandex.ru", domainInfo.getData().getId());
		assertEquals("domain", domainInfo.getData().getType());
	}
}

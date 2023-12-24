package com.Nickode;

import com.Nickode.security.NiCloudAuthRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NiCloudIntegrationTests {
    private final TestRestTemplate testRestTemplate = new TestRestTemplate();
    private static GenericContainer<?> genericContainer = new GenericContainer<>("NiCloudImage").withExposedPorts(8888);
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static NiCloudAuthRequest niCloudAuthRequest = new NiCloudAuthRequest();

    @BeforeAll
    public static void setUp() {
        niCloudAuthRequest.setUsername("Nikolai");
        niCloudAuthRequest.setPassword("Nikolaipassword");
    }

    @Test
    void NiCloudTest01() throws JsonProcessingException {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<>(objectMapper.writeValueAsString(niCloudAuthRequest), httpHeaders);
        ResponseEntity<String> responseEntityStr = testRestTemplate.
                postForEntity("http://localhost:8888" + "/login", request, String.class);
        JsonNode jsonNode = objectMapper.readTree(responseEntityStr.getBody());
        String token = jsonNode.get("auth-token").toString();
        Assertions.assertTrue(token != null && !token.isEmpty());
    }

}

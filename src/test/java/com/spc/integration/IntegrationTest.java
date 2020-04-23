package com.spc.integration;

import com.spc.Application;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class IntegrationTest {
    @Inject
    Environment environment;


    @Test
    public void integrationTest() throws IOException {
        String testport = environment.getProperty("local.server.port");
        int statusCode = Request.Get("http://localhost:" + testport + "/auth")
                .execute().returnResponse().getStatusLine().getStatusCode();
//        Assertions.assertTrue(Request.Get("http://localhost:" + testport + "/auth")
//                .execute().returnContent().toString().contains("用户没登录"));
        Assertions.assertEquals(200, statusCode);
    }
}

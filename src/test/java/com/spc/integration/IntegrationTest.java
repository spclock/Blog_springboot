package com.spc.integration;

import com.spc.Application;
import org.apache.http.client.fluent.Request;
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
//        fluent-ch api
        int statusCode = Request.Get("http://localhost:" + testport + "/auth")
                .execute().returnResponse().getStatusLine().getStatusCode();
        //contentType isnt utf-8 ,how set utf-8
        Assertions.assertTrue(Request.Get("http://localhost:" + testport + "/auth")
                .execute().returnContent().toString().contains("false"));
        Assertions.assertEquals(200, statusCode);
    }
}

package com.example.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class UsersApiTest extends ApiTest {
    @Test
    public void should_get_empty_user_lists_success() throws Exception {
        given().
            standaloneSetup(new UsersApi()).
        when().
            get("/users").
        then().
            statusCode(200);
    }

    @Test
    public void should_create_user_success() throws Exception {
        Map<String, Object> createUserParameter = new HashMap<String, Object>() {{
            put("username", "aisensiy");
        }};

        given()
            .standaloneSetup(new UsersApi())
            .contentType("application/json")
            .body(createUserParameter)
            .when().post("/users")
            .then().statusCode(201);

    }

    @Test
    public void should_get_400_error_message_with_wrong_parameter_when_create_user() throws Exception {

        Map<String, Object> wrongParameter = new HashMap<String, Object>() {{
            put("name", "aisensiy");
        }};

        given()
            .standaloneSetup(
                MockMvcBuilders
                    .standaloneSetup(new UsersApi())
                    .setHandlerExceptionResolvers(createExceptionResolver()))
            .contentType("application/json")
            .body(wrongParameter)
            .when().post("/users")
            .then().statusCode(400)
            .body("fieldErrors[0].field", equalTo("username"))
            .body("fieldErrors.size()", equalTo(1));
    }
}

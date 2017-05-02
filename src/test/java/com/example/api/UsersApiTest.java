package com.example.api;

import com.example.domain.User;
import com.example.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UsersApiTest extends ApiTest {

    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository = mock(UserRepository.class);
    }

    @Test
    public void should_get_empty_user_lists_success() throws Exception {
        given().
            standaloneSetup(new UsersApi(userRepository)).
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
            .standaloneSetup(new UsersApi(userRepository))
            .contentType("application/json")
            .body(createUserParameter)
            .when().post("/users")
            .then().statusCode(201);

        verify(userRepository).save(any());
    }

    @Test
    public void should_get_400_error_message_with_wrong_parameter_when_create_user() throws Exception {

        Map<String, Object> wrongParameter = new HashMap<String, Object>() {{
            put("name", "aisensiy");
        }};

        given()
            .standaloneSetup(
                MockMvcBuilders
                    .standaloneSetup(new UsersApi(userRepository))
                    .setHandlerExceptionResolvers(createExceptionResolver()))
            .contentType("application/json")
            .body(wrongParameter)
            .when().post("/users")
            .then().statusCode(400)
            .body("fieldErrors[0].field", equalTo("username"))
            .body("fieldErrors.size()", equalTo(1));
    }

    @Test
    public void should_get_one_user_success() throws Exception {
        User user = new User(UUID.randomUUID().toString(), "aisensiy");
        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user));

        given()
            .standaloneSetup(
                MockMvcBuilders
                    .standaloneSetup(new UserApi(userRepository))
                    .setHandlerExceptionResolvers(createExceptionResolver()))
            .when().get("/users/{userId}", user.getId())
            .then().statusCode(200)
            .body("id", equalTo(user.getId()))
            .body("username", equalTo(user.getUsername()))
            .body("links.self", endsWith("/users/" + user.getId()));

    }
}

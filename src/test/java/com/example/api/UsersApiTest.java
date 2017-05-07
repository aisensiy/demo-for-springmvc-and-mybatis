package com.example.api;

import com.example.api.exception.CustomizeExceptionHandler;
import com.example.domain.User;
import com.example.domain.UserRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
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
public class UsersApiTest {

    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository = mock(UserRepository.class);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new UsersApi(userRepository)).setControllerAdvice(new CustomizeExceptionHandler()).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void should_get_empty_user_lists_success() throws Exception {
        given().
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
            .contentType("application/json")
            .body(wrongParameter)
            .when().post("/users")
            .then().statusCode(400)
            .body("fieldErrors[0].field", equalTo("username"))
            .body("fieldErrors.size()", equalTo(1));
    }

    @Test
    public void should_get_400_error_message_with_duplicated_username_when_create_user() throws Exception {
        User user = new User("123", "abc");
        when(userRepository.findByUsername(eq("abc"))).thenReturn(Optional.of(user));

        Map<String, Object> duplicatedUserName = new HashMap<String, Object>() {{
            put("username", "abc");
        }};

        given()
            .contentType("application/json")
            .body(duplicatedUserName)
            .when().post("/users")
            .then().statusCode(400)
            .body("message", equalTo("Error in create user"))
            .body("fieldErrors[0].field", equalTo("username"))
            .body("fieldErrors[0].message", equalTo("duplicated username"))
            .body("fieldErrors.size()", equalTo(1));
    }

    @Test
    public void should_get_one_user_success() throws Exception {
        User user = new User(UUID.randomUUID().toString(), "aisensiy");
        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user));

        given()
            .standaloneSetup(new UserApi(userRepository))
            .when().get("/users/{userId}", user.getId())
            .then().statusCode(200)
            .body("id", equalTo(user.getId()))
            .body("username", equalTo(user.getUsername()))
            .body("links.self", endsWith("/users/" + user.getId()));
    }
}

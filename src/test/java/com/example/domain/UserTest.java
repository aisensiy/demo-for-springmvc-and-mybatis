package com.example.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void should_eq_by_fields() throws Exception {
        User user1 = new User("123", "a");
        User user2 = new User("123", "a");
        assertThat(user1, is(user2));
    }
}

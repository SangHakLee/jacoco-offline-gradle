package com.example.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ApiTest {

    Api api = new Api("django");

    @Test
    public void sumTest() {
        int x = 1;
        int y = 2;

        int result = api.sum(x, y);

        assertEquals(3, result);
    }

}

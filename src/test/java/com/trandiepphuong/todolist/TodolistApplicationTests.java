package com.trandiepphuong.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class TodolistApplicationTests {
    @Test
    public void contextLoads() {
    }
}

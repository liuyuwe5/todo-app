package com.example.todoapp.cotroller

import com.example.todoapp.controller.HelloController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(HelloController::class)
class HelloControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun shouldShowHelloWorld() {
        mockMvc.perform(get("/hello"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").value("Hello World"))
    }

}
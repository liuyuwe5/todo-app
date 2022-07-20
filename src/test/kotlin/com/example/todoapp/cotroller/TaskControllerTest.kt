package com.example.todoapp.cotroller

import com.example.todoapp.controller.TaskController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(TaskController::class)
class TaskControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

}


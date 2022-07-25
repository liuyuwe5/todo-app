package com.example.todoapp.controller
import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.service.EmployeeService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.mockk.every
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(EmployeeController::class)
class EmployeeControllerTest {
    private val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var employeeService: EmployeeService

    @Test
    fun shouldCreateEmployeeWIthTasksListSuccessfully() {
        val emily = Employee(employeeName="Emily", uniqueId = 12345)
        val task1 = Task(taskName = "Clean room")
        val task2 = Task(taskName = "Buy groceries")
        emily.tasksList.add(task1)
        emily.tasksList.add(task2)
        val expected = Employee(id=0, employeeName="Emily", uniqueId = 12345, tasksList = mutableListOf(task1,task2))
        `when`(employeeService.createEmployee(emily)).thenReturn(expected)
        mockMvc.perform(post("/employee").content(mapper.writeValueAsString(emily))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id", `is`(0)))
            .andExpect(jsonPath("$.employeeName", `is`("Emily")))
            .andExpect(jsonPath("$.uniqueId", `is`(12345)))
            .andExpect(jsonPath("$.tasksList").isArray)
            .andExpect(jsonPath("$.tasksList", hasSize<Task>(2)))
            .andExpect(jsonPath("$.tasksList[0].taskName", `is`("Clean room") ))
            .andExpect(jsonPath("$.tasksList[1].taskName", `is`("Buy groceries")))
    }
}
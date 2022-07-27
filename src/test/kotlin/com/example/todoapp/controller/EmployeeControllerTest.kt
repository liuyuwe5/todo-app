package com.example.todoapp.controller
import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.exception.EmployeeNotFoundException
import com.example.todoapp.service.EmployeeService
import com.example.todoapp.exception.EmployeeUniqueIdViolationException
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(EmployeeController::class)
class EmployeeControllerTest {

    private val employeeRequestBody =
        """{"employeeName":"Emily","employeeUniqueId":12345,"tasks":[{"taskName":"task1"},{"taskName":"task2"}]}""".trimMargin()

    private val employeeUniqueIdExceptionRequestBody =
        """{"employeeName":"Emily","employeeUniqueId":12345,"tasks":[]}""".trimMargin()

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var employeeService: EmployeeService

    private val task1 = Task(taskName = "task1")
    private val task2 = Task(taskName = "task2")
    private val expectedEmployee = Employee(
        id = 1,
        employeeName = "Emily",
        employeeUniqueId = 12345,
        tasks = mutableListOf(task1, task2)
    )

    @Test
    fun `should create employee with tasks successfully`() {
        `when`(
            employeeService
                .createEmployee("Emily", 12345, mutableListOf(task1, task2))
        )
            .thenReturn(expectedEmployee)

        mockMvc.perform(
            post("/employees")
                .content(employeeRequestBody)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(1)))
            .andExpect(jsonPath("$.employeeName", `is`("Emily")))
            .andExpect(jsonPath("$.employeeUniqueId", `is`(12345)))
            .andExpect(jsonPath("$.tasks").isArray)
            .andExpect(jsonPath("$.tasks", hasSize<Task>(2)))
            .andExpect(jsonPath("$.tasks[0].taskName", `is`("task1")))
            .andExpect(jsonPath("$.tasks[1].taskName", `is`("task2")))
    }

    @Test
    fun `should get error when the employee unique id already exists with status 400`() {
        val expectedException = EmployeeUniqueIdViolationException("Employee Unique Id Already Exists!")
        `when`( employeeService.createEmployee(employeeName = "Emily", employeeUniqueId = 12345, mutableListOf()))
            .thenThrow(expectedException)

        mockMvc.perform(
            post("/employees")
                .content(employeeUniqueIdExceptionRequestBody)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", `is`("Employee Unique Id Already Exists!")))
    }

    @Test
    fun `should get correct employee by id if exists`() {
        `when`(employeeService.getById(1))
            .thenReturn(expectedEmployee)

        mockMvc.perform(
            get("/employees/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(1)))
            .andExpect(jsonPath("$.employeeName", `is`("Emily")))
            .andExpect(jsonPath("$.employeeUniqueId", `is`(12345)))
            .andExpect(jsonPath("$.tasks").isArray)
            .andExpect(jsonPath("$.tasks", hasSize<Task>(2)))
            .andExpect(jsonPath("$.tasks[0].taskName", `is`("task1")))
            .andExpect(jsonPath("$.tasks[1].taskName", `is`("task2")))
    }

    @Test
    fun `should get error when can't find employee by id`() {
        val expectedException = EmployeeNotFoundException("Can't Find Employee by Id!")
        `when`( employeeService.getById(123)).thenThrow(expectedException)

        mockMvc.perform(
            get("/employees/123"))
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", `is`("Can't Find Employee by Id!")))
    }

}
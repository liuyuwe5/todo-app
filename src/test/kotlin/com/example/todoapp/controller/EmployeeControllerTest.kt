package com.example.todoapp.controller
import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.exception.EmployeeNotFoundException
import com.example.todoapp.service.EmployeeService
import com.example.todoapp.exception.EmployeeUniqueIdViolationException
import org.hamcrest.Matchers.hasSize
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(EmployeeController::class)
class EmployeeControllerTest {

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
        val employeeCreateRequest =
            """{"employeeName":"Emily","employeeUniqueId":12345,"tasks":[{"taskName":"task1"},{"taskName":"task2"}]}""".trimMargin()

        `when`(
            employeeService
                .createEmployee("Emily", 12345, mutableListOf(task1, task2))
        )
            .thenReturn(expectedEmployee)

        mockMvc.perform(
            post("/employees")
                .content(employeeCreateRequest)
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
    fun `should get bad request when the employee unique id already exists`() {
        val expectedException = EmployeeUniqueIdViolationException("Employee Unique Id Already Exists!")
        `when`(employeeService.createEmployee(employeeName = "Emily", employeeUniqueId = 12345, mutableListOf()))
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
        `when`(employeeService.getEmployeeById(1))
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
    fun `should get not found when can't find employee by id`() {
        val expectedException = EmployeeNotFoundException("Can't Find Employee by Id!")
        `when`(employeeService.getEmployeeById(123)).thenThrow(expectedException)

        mockMvc.perform(
            get("/employees/123"))
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", `is`("Can't Find Employee by Id!")))
    }

    @Test
    fun `should update employee correctly by id if exists`() {
        val employeeUpdateRequest =  """{"employeeName":"Emily Liu","employeeUniqueId":11010}""".trimMargin()
        doNothing().`when`(employeeService).updateEmployeeById("Emily Liu", 11010,1)

        mockMvc.perform(
            patch("/employees/1")
                .content(employeeUpdateRequest)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message", `is`("Update Employee Successfully")))

        verify(employeeService, only()).updateEmployeeById("Emily Liu", 11010,1)
   }

    @Test
    fun `should get not found when can't find employee to update`() {
        val employeeUpdateRequest =
            """{"employeeName":"Emily Liu","employeeUniqueId":11011 }""".trimMargin()
        val expectedException = EmployeeNotFoundException("Can't Find Employee to Update!")
        `when`(employeeService.updateEmployeeById("Emily Liu", 11011, 123)).thenThrow(expectedException)

        mockMvc.perform(
            patch("/employees/123")
                .content(employeeUpdateRequest)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", `is`("Can't Find Employee to Update!")))
    }

    @Test
    fun `should get bad request when the employee unique id to update already exists`() {
        val expectedException = EmployeeUniqueIdViolationException("Employee Unique Id to Update Already Exists!")
        val employeeUpdateRequest =
            """{"employeeName":"Emily Liu","employeeUniqueId":11111 }""".trimMargin()
        `when`(employeeService.updateEmployeeById(employeeName = "Emily Liu", employeeUniqueId = 11111, 1))
            .thenThrow(expectedException)

        mockMvc.perform(
            patch("/employees/1")
                .content(employeeUpdateRequest)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", `is`("Employee Unique Id to Update Already Exists!")))
    }

    @Test
    fun `should delete employee correctly by id if exists`() {
        doNothing().`when`(employeeService).deleteEmployeeById(1)

        mockMvc.perform(
            delete("/employees/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message", `is`("Delete Employee Successfully")))

        verify(employeeService, only()).deleteEmployeeById(1)
    }

    @Test
    fun `should get not found when can't find employee to delete`() {
        val expectedException = EmployeeNotFoundException("Can't Find Employee to Delete!")
        `when`(employeeService.deleteEmployeeById(123)).thenThrow(expectedException)

        mockMvc.perform(
            delete("/employees/123"))
            .andExpect(status().isNotFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", `is`("Can't Find Employee to Delete!")))
    }
}
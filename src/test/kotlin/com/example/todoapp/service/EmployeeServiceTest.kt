package com.example.todoapp.service

import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.exception.EmployeeUniqueIdViolationException
import com.example.todoapp.repository.EmployeeRepository
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.dao.DataIntegrityViolationException

class EmployeeServiceTest {

    private val employeeRepository: EmployeeRepository = mock(EmployeeRepository::class.java)

    private val employeeService: EmployeeService = EmployeeService(employeeRepository)

    @Test
    fun `should create an employee successfully`() {
        val expectedEmployee = Employee(employeeName = "Emily", employeeUniqueId = 12345)
        val task1 = Task(taskName = "task1")
        val task2 = Task(taskName = "task2")
        expectedEmployee.tasks = mutableListOf(task1, task2)
        task1.employee = expectedEmployee
        task2.employee = expectedEmployee
        `when`(employeeRepository.save(
            Employee(employeeName = "Emily",
                employeeUniqueId = 12345,
                tasks = mutableListOf(task1,task2)))).thenReturn(expectedEmployee)

        val actualEmployee: Employee = employeeService
            .createEmployee("Emily", 12345, mutableListOf(task1, task2))

        assertEquals(expectedEmployee.id, actualEmployee.id)
        assertEquals(expectedEmployee.employeeName, actualEmployee.employeeName)
        assertEquals(expectedEmployee.employeeUniqueId, actualEmployee.employeeUniqueId)
        assertEquals(expectedEmployee.tasks, actualEmployee.tasks)
    }

    @Test
    fun `should throw an exception if Employee Unique Id already exists`() {
        val expectedException = DataIntegrityViolationException("Employee Unique Id Already Exists!")
        val employee = Employee(employeeName = "Emily", employeeUniqueId = 12345)
        `when`(employeeRepository
            .save(employee))
            .thenThrow(expectedException)

        val actualException = assertThrows(EmployeeUniqueIdViolationException::class.java) {
            employeeService.createEmployee(
                "Emily",
                12345,
                mutableListOf()
            )
        }

        assertEquals("Employee Unique Id Already Exists!", actualException.message)
    }


}
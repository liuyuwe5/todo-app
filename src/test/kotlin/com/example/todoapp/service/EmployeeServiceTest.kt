package com.example.todoapp.service

import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.exception.EmployeeNotFoundException
import com.example.todoapp.exception.EmployeeUniqueIdViolationException
import com.example.todoapp.repository.EmployeeRepository
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.dao.DataIntegrityViolationException
import java.util.*

class EmployeeServiceTest {

    private val employeeRepository: EmployeeRepository = mock(EmployeeRepository::class.java)
    private val employeeService: EmployeeService = EmployeeService(employeeRepository)
    private val expectedEmployee = Employee(employeeName = "Emily", employeeUniqueId = 12345)
    private val task1 = Task(taskName = "task1")
    private val task2 = Task(taskName = "task2")
    @Test
    fun `should create an employee successfully`() {
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
        `when`(employeeRepository
            .save(expectedEmployee))
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

    @Test
    fun `should get correct employee by id if exists`() {
        var expectedEmployeeOptional: Optional<Employee> = Optional.ofNullable(expectedEmployee)
        `when`(employeeRepository.findById(1)).thenReturn(expectedEmployeeOptional)

        val actualEmployee = employeeService.getById(1)

        assertEquals(expectedEmployee.id, actualEmployee?.id)
        assertEquals(expectedEmployee.employeeName, actualEmployee?.employeeName)
        assertEquals(expectedEmployee.employeeUniqueId, actualEmployee?.employeeUniqueId)
        assertEquals(expectedEmployee.tasks, actualEmployee?.tasks)
    }

    @Test
    fun `should throw an exception if id not found`() {
        val expectedException = EmployeeNotFoundException("Can't Find Employee by Id!")
        `when`(employeeRepository
            .getById(123)).thenThrow(expectedException)

        val actualException = assertThrows(EmployeeNotFoundException::class.java) {
            employeeService.getById(123)
        }

        assertEquals("Can't Find Employee by Id!", actualException.message)
    }

}




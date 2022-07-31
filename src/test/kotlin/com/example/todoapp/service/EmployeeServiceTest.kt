package com.example.todoapp.service

import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.exception.EmployeeNotFoundException
import com.example.todoapp.exception.EmployeeToDeleteNotFoundException
import com.example.todoapp.exception.EmployeeToUpdateNotFoundException
import com.example.todoapp.exception.EmployeeUniqueIdViolationException
import com.example.todoapp.repository.EmployeeRepository
import com.example.todoapp.repository.TaskRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.dao.DataIntegrityViolationException
import java.util.*

class EmployeeServiceTest {

    private val employeeRepository: EmployeeRepository = mock(EmployeeRepository::class.java)
    private val taskRepository: TaskRepository = mock(TaskRepository::class.java)
    private val employeeService: EmployeeService = EmployeeService(employeeRepository,taskRepository)
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
        expectedEmployee.id = 1
        val expectedEmployeeOptional: Optional<Employee> = Optional.ofNullable(expectedEmployee)
        `when`(employeeRepository.findById(1)).thenReturn(expectedEmployeeOptional)

        val actualEmployee = employeeService.getEmployeeById(1)

        assertEquals(expectedEmployee.id, actualEmployee.id)
        assertEquals(expectedEmployee.employeeName, actualEmployee.employeeName)
        assertEquals(expectedEmployee.employeeUniqueId, actualEmployee.employeeUniqueId)
        assertEquals(expectedEmployee.tasks, actualEmployee.tasks)
    }

    @Test
    fun `should throw an exception if employee to get not found`() {
        val expectedException = EmployeeNotFoundException("Can't Find Employee to Get!")
        `when`(employeeRepository
            .findById(123)).thenThrow(expectedException)
        val actualException = assertThrows(EmployeeNotFoundException::class.java) {
            employeeService.getEmployeeById(123)
        }

        assertEquals("Can't Find Employee to Get!", actualException.message)
    }


    @Test
    fun `should update employee by id if exists`() {
        `when`(employeeRepository.updateEmployeeById("Emily Liu",11010, 1)).thenReturn(1)

        employeeService.updateEmployeeById("Emily Liu", 11010,1)

        verify(employeeRepository, times(1)).updateEmployeeById("Emily Liu",11010, 1)
    }

    @Test
    fun `should throw an exception if employee to update not found`() {
        val expectedException = EmployeeToUpdateNotFoundException("Can't Find Employee to Update!")
        `when`(employeeRepository
            .updateEmployeeById("Emily Liu", 11011, 123)).thenThrow(expectedException)
        val actualException = assertThrows(EmployeeToUpdateNotFoundException::class.java) {
            employeeService.updateEmployeeById("Emily Liu", 11011, 123)
        }

        assertEquals("Can't Find Employee to Update!", actualException.message)
    }

    @Test
    fun `should delete employee by id if exists`() {
        `when`(employeeRepository.deleteEmployeeById(1)).thenReturn(1)

        employeeService.deleteEmployeeById(1)

        verify(employeeRepository, times(1)).deleteEmployeeById(1)
    }

    @Test
    fun `should throw an exception if employee to delete not found`() {
        val expectedException = EmployeeToDeleteNotFoundException("Can't Find Employee to Delete!")
        `when`(employeeRepository
            .deleteEmployeeById(123)).thenThrow(expectedException)
        val actualException = assertThrows(EmployeeToDeleteNotFoundException::class.java) {
            employeeService.deleteEmployeeById(123)
        }

        assertEquals("Can't Find Employee to Delete!", actualException.message)
    }

}




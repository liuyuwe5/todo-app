package com.example.todoapp.service

import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.repository.EmployeeRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.Runs
import io.mockk.every
import io.mockk.*
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.util.*
import java.util.List


class EmployeeServiceTest {
    private val employeeRepository= mockk<EmployeeRepository>(relaxed = true)
    private val employeeService = EmployeeService(employeeRepository)

    @Test
    fun shouldCreateEmployeeWithGeneratedId() {
        val emily = Employee(employeeName="Emily", uniqueId = 12345)
        every {  employeeRepository.save(emily) } returns emily
        val actual = employeeService.createEmployee(emily)
        verify(exactly = 1) { employeeRepository.save(emily) }
        assertEquals(emily, actual)
    }

    @Test
    fun shouldCreateEmployeeWithTasksList() {
        val emily = Employee(employeeName="Emily", uniqueId = 12345)
        val task1 = Task(taskName = "Clean room", executor = emily)
        val task2 = Task(taskName = "Buy groceries", executor = emily)
        emily.tasksList.add(task1)
        emily.tasksList.add(task2)
        every {  employeeRepository.save(emily) } returns emily
        val actual = employeeService.createEmployee(emily)
        verify(exactly = 1) { employeeRepository.save(emily) }
        assertEquals(emily, actual)
    }
}
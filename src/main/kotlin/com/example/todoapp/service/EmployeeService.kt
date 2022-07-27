package com.example.todoapp.service

import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.exception.EmployeeNotFoundException
import com.example.todoapp.exception.EmployeeUniqueIdViolationException
import com.example.todoapp.repository.EmployeeRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.util.*
import java.util.logging.Logger

@Service
class EmployeeService(var employeeRepository: EmployeeRepository) {
    val logger: Logger = Logger.getLogger(this.javaClass.name)
    fun createEmployee(
        employeeName: String,
        employeeUniqueId: Long,
        tasks: MutableList<Task>
    ): Employee {
        val newEmployee = Employee(employeeName = employeeName, employeeUniqueId = employeeUniqueId, tasks = tasks)
        tasks.forEach {
            it.employee = newEmployee
        }
        try {
            return employeeRepository.save(newEmployee)
        } catch (e: DataIntegrityViolationException) {
            logger.warning(
                "Employee Unique Id Already Exists: $employeeUniqueId; " +
                        "Input Employee Name: $employeeName; Caused by: " + e.stackTraceToString()
            )
            throw EmployeeUniqueIdViolationException("Employee Unique Id Already Exists!")
        }
    }
    fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null);
    fun getById(id: Long): Employee {
        return employeeRepository.findById(id).toNullable()
            ?: throw EmployeeNotFoundException("Can't Find Employee by Id!")
    }
}




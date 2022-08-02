package com.example.todoapp.service

import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.exception.EmployeeNotFoundException
import com.example.todoapp.exception.EmployeeUniqueIdViolationException
import com.example.todoapp.repository.EmployeeRepository
import com.example.todoapp.repository.TaskRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.logging.Logger

@Service
class EmployeeService(
    var employeeRepository: EmployeeRepository,
    var taskRepository: TaskRepository
) {
    val logger: Logger = Logger.getLogger(this.javaClass.name)
    fun createEmployee(
        employeeName: String,
        employeeUniqueId: Long,
        tasks: MutableList<Task>
    ): Employee {
        val newEmployee = Employee(employeeName = employeeName, employeeUniqueId = employeeUniqueId, tasks = tasks)
        newEmployee.tasks.forEach {
            it.employee = newEmployee
        }
        try {
            return employeeRepository.save(newEmployee)
        } catch (e: DataIntegrityViolationException) {
            logger.warning(
                "Employee unique id already exists: $employeeUniqueId; " +
                        "Input employee name: $employeeName; Caused by: " + e.stackTraceToString()
            )
            throw EmployeeUniqueIdViolationException("Employee unique id already exists!")
        }
    }

    fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null);

    fun getEmployeeById(id: Long): Employee {
        return employeeRepository.findById(id).toNullable()
            ?: throw EmployeeNotFoundException("Can't find employee to get!")
    }

    fun updateEmployeeById(
        employeeName: String,
        employeeUniqueId: Long,
        id: Long
    ) {
        try {
            employeeRepository.updateEmployeeById(employeeName, employeeUniqueId, id).takeUnless {
                it > 0
            }?.let {
                throw EmployeeNotFoundException("Can't find employee to update!")
            }
        } catch (e: DataIntegrityViolationException) {
            logger.warning(
                "Employee unique id to update already exists: $employeeUniqueId; " +
                        "Input employee name: $employeeName; Caused by: " + e.stackTraceToString()
            )
            throw EmployeeUniqueIdViolationException("Employee unique id to update already exists!")
        }
    }

    @Transactional
    fun deleteEmployeeById(
        id: Long
    ) {
        taskRepository.deleteTaskByEmployeeId(id)
        employeeRepository.deleteEmployeeById(id).takeUnless {
            it > 0
        }?.let {
            throw EmployeeNotFoundException("Can't find employee to delete!")
        }
    }
}




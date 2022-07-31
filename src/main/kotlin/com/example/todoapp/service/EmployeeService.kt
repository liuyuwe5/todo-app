package com.example.todoapp.service

import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.exception.EmployeeNotFoundException
import com.example.todoapp.exception.EmployeeToDeleteNotFoundException
import com.example.todoapp.exception.EmployeeToUpdateNotFoundException
import com.example.todoapp.exception.EmployeeUniqueIdViolationException
import com.example.todoapp.repository.EmployeeRepository
import com.example.todoapp.repository.TaskRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
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
    fun getEmployeeById(id: Long): Employee {
        return employeeRepository.findById(id).toNullable()
            ?: throw EmployeeNotFoundException("Can't Find Employee to Get!")
    }

    fun updateEmployeeById(
        employeeName: String,
        employeeUniqueId: Long,
        id: Long
    ) = employeeRepository.updateEmployeeById(employeeName, employeeUniqueId, id).takeUnless {
        it > 0
    }?.let {
        throw EmployeeToUpdateNotFoundException("Can't Find Employee to Update!")
    }


    fun deleteEmployeeById(
        id: Long
    ) {
        taskRepository.deleteTaskByEmployeeId(1)
        employeeRepository.deleteEmployeeById(id).takeUnless {
            it > 0
        }?.let {
            throw EmployeeToDeleteNotFoundException("Can't Find Employee to Delete!")
        }
    }
}




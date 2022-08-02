package com.example.todoapp.controller

import com.example.todoapp.controller.dto.*
import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.service.EmployeeService
import org.springframework.web.bind.annotation.*

@RestController
class EmployeeController(val employeeService: EmployeeService) {

    @PostMapping("/employees", produces = ["application/json"])
    fun addEmployee(@RequestBody employeeCreatedRequest: EmployeeDTO): EmployeeDTO {
        val tasks = employeeCreatedRequest.tasks!!.map{Task(taskName = it.taskName, isCompleted = it.isCompleted)}.toMutableList()
        employeeCreatedRequest.tasks!!.forEach { task ->
            Task(
                taskName = task.taskName,
                isCompleted = task.isCompleted
            )
        }
        val newEmployee =
            employeeService.createEmployee(
                employeeCreatedRequest.employeeName,
                employeeCreatedRequest.employeeUniqueId,
                tasks
            )
        val tasksDTO = newEmployee.tasks.map{TaskDTO(id=it.id, taskName = it.taskName, isCompleted = it.isCompleted)}.toMutableList()
        return EmployeeDTO(
            newEmployee.id!!,
            newEmployee.employeeName,
            newEmployee.employeeUniqueId,
            tasksDTO
        )
    }

    @GetMapping("/employees/{id}", produces = ["application/json"])
    fun getEmployeeById(@PathVariable("id") id: Long): EmployeeDTO {
        val employee: Employee = employeeService.getEmployeeById(id)
        val tasksDTO = employee.tasks.map{TaskDTO(id=it.id, taskName = it.taskName, isCompleted = it.isCompleted)}.toMutableList()
        return EmployeeDTO(
            employee.id!!,
            employee.employeeName,
            employee.employeeUniqueId,
            tasksDTO
        )
    }

    @PatchMapping("/employees/{id}", produces = ["application/json"])
    fun updateEmployeeById(@PathVariable("id") id: Long, @RequestBody employeeUpdateRequest :EmployeeDTO): MessageResponse {
        employeeService.updateEmployeeById(
            employeeUpdateRequest.employeeName,
            employeeUpdateRequest.employeeUniqueId,
            id)
        return MessageResponse("Update Employee Successfully")
    }

    @DeleteMapping("/employees/{id}", produces = ["application/json"])
    fun deleteEmployeeById(@PathVariable("id") id: Long): MessageResponse {
        employeeService.deleteEmployeeById(id)
        return MessageResponse("Delete Employee Successfully")
    }
}
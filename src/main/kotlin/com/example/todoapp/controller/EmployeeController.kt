package com.example.todoapp.controller

import com.example.todoapp.controller.dto.EmployeeCreatedRequest
import com.example.todoapp.controller.dto.EmployeeCreatedResponse
import com.example.todoapp.entity.Employee
import com.example.todoapp.service.EmployeeService
import org.springframework.web.bind.annotation.*

@RestController
class EmployeeController(val employeeService: EmployeeService) {

    @PostMapping("/employees", produces = ["application/json"])
    fun addEmployee(@RequestBody employeeCreatedRequest: EmployeeCreatedRequest): EmployeeCreatedResponse {
        val newEmployee =
            employeeService.createEmployee(
                employeeCreatedRequest.employeeName,
                employeeCreatedRequest.employeeUniqueId,
                employeeCreatedRequest.tasks
            )
        return EmployeeCreatedResponse(
            newEmployee.id,
            newEmployee.employeeName,
            newEmployee.employeeUniqueId,
            newEmployee.tasks
        )

    }

    @GetMapping("/employees/{id}", produces = ["application/json"])
    fun getEventById(@PathVariable("id") id: Long): EmployeeCreatedResponse {
        val employee: Employee = employeeService.getById(id)
        return EmployeeCreatedResponse(
            employee.id,
            employee.employeeName,
            employee.employeeUniqueId,
            employee.tasks
        )
    }
}
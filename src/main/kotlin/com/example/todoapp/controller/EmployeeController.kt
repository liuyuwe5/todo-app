package com.example.todoapp.controller

import com.example.todoapp.controller.dto.*
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
    fun getEmployeeById(@PathVariable("id") id: Long): EmployeeGetResponse {
        val employee: Employee = employeeService.getEmployeeById(id)
        return EmployeeGetResponse(
            employee.id!!,
            employee.employeeName,
            employee.employeeUniqueId,
            employee.tasks
        )
    }

    @PatchMapping("/employees/{id}", produces = ["application/json"])
    fun updateEmployeeById(@PathVariable("id") id: Long, @RequestBody employeeUpdateRequest : EmployeeUpdateRequest): EmployeeUpdateResponse {
        employeeService.updateEmployeeById(
            employeeUpdateRequest.employeeName,
            employeeUpdateRequest.employeeUniqueId,
            id)
        return EmployeeUpdateResponse("Update Employee Successfully")
    }

    @DeleteMapping("/employees/{id}", produces = ["application/json"])
    fun deleteEmployeeById(@PathVariable("id") id: Long): EmployeeDeleteResponse {
        employeeService.deleteEmployeeById(id)
        return EmployeeDeleteResponse("Delete Employee Successfully")
    }
}
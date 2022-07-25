package com.example.todoapp.controller

import com.example.todoapp.entity.Employee
import com.example.todoapp.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class EmployeeController(val employeeService: EmployeeService){


    @PostMapping("/employee", produces = ["application/json"])
    fun addEmployee(@RequestBody employee: Employee): ResponseEntity<Employee> {
        val newEmployee = employeeService.createEmployee(employee)

        Object()
        return ResponseEntity(newEmployee, HttpStatus.CREATED)
    }
}
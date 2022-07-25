package com.example.todoapp.service

import com.example.todoapp.entity.Employee
import com.example.todoapp.entity.Task
import com.example.todoapp.repository.EmployeeRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class EmployeeService(var employeeRepository: EmployeeRepository) {
    fun createEmployee(employee: Employee): Employee {
        return employeeRepository.save(employee)
    }
}

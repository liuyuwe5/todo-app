package com.example.todoapp.controller

import com.example.todoapp.controller.dto.EmployeeCreatedRequest
import com.example.todoapp.controller.dto.EmployeeCreatedResponse
import com.example.todoapp.entity.Employee
import com.example.todoapp.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class EmployeeController(val employeeService: EmployeeService){

    @PostMapping("/employees", produces = ["application/json"])
    fun addEmployee(@RequestBody employeeCreatedRequest: EmployeeCreatedRequest): ResponseEntity<Employee> {
        val newEmployee =
            employeeService.createEmployee(
                employeeCreatedRequest.employeeName,
                employeeCreatedRequest.employeeUniqueId,
                employeeCreatedRequest.tasks
            )
        return ResponseEntity(newEmployee, HttpStatus.CREATED)
    }

//    @GetMapping("/{employeeId}")
//    fun getEventById(@PathVariable employeeId: Long?): ResponseEntity<EmployeeResponseBody>? {
//        val employee: Optional<Employee> = employeeService.getEventById(eventId)
//        if (event.isEmpty()) {
//            return ResponseEntity.notFound().build<EventResponseBody?>()
//        }
//        val eventResponseBody: EventResponseBody = EventResponseBodyMapper.fromEvent(event.get())
//        return ResponseEntity.ok<EventResponseBody?>(eventResponseBody)
//    }
}
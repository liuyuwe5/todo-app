package com.example.todoapp.controller.dto

import com.example.todoapp.entity.Task

data class EmployeeGetResponse(
    var id: Long?,

    var employeeName: String,

    var employeeUniqueId: Long,

    var tasks: MutableList<Task>
)
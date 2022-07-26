package com.example.todoapp.controller.dto

import com.example.todoapp.entity.Task
import org.jetbrains.annotations.NotNull

data class EmployeeCreatedRequest(
    var employeeName: String,

    var employeeUniqueId: Long,

    var tasks: MutableList<Task>
)
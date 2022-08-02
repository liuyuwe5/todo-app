package com.example.todoapp.controller.dto

data class TaskDTO (
    val id: Long?,

    val taskName: String,

    val isCompleted: Boolean
)
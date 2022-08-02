package com.example.todoapp.exception

import com.example.todoapp.controller.dto.MessageResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(value = [EmployeeUniqueIdViolationException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleEmployeeUniqueIdExceptionViolation(
        e: EmployeeUniqueIdViolationException
    ): MessageResponse {
        return  MessageResponse(
            e.message!!
        )
    }

    @ExceptionHandler(value = [EmployeeNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleEmployeeNotFound(
        e: Exception
    ):  MessageResponse{
        return  MessageResponse(
           e.message!!
        )
    }
}
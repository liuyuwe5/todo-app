package com.example.todoapp

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
class HelloController {

    @GetMapping("/hello", produces= ["application/json"])
    @ResponseBody
    fun hello(): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.OK).body("Hello World")
    }

}
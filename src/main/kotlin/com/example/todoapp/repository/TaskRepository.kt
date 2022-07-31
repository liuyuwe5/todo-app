package com.example.todoapp.repository

import com.example.todoapp.entity.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface TaskRepository: JpaRepository<Task,Long>{
    @Modifying
    @Transactional
    @Query(value = "delete from Task t where t.employee.id = ?1")
    fun deleteTaskByEmployeeId(id: Long): Int
}

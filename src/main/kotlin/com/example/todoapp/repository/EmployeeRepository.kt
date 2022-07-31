package com.example.todoapp.repository

import com.example.todoapp.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
interface EmployeeRepository: JpaRepository<Employee, Long> {

    @Modifying(clearAutomatically=true)
    @Transactional
    @Query("update Employee e set e.employeeName = ?1, " +
            "e.employeeUniqueId = ?2 " +
            "where e.id = ?3")
    fun updateEmployeeById(employeeName: String, employeeUniqueId: Long, id: Long): Int
    @Modifying
    @Transactional
    @Query(value = "delete from Employee e where e.id = ?1")
    fun deleteEmployeeById(id: Long): Int
}
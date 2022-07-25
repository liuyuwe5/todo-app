package com.example.todoapp.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity(name = "Employee")
@Table(name = "Employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "EMPLOYEE_NAME", nullable = false)
    val employeeName: String,

    @Column(name="UNIQUE_ID", nullable=false, unique=true)
    val uniqueId: Long,

    @OneToMany(mappedBy="taskName", fetch=FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonManagedReference
    var tasksList: MutableList<Task> = mutableListOf()
)
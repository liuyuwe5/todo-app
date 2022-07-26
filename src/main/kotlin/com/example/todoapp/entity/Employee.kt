package com.example.todoapp.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity(name = "Employee")
@Table(name = "Employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name = "EMPLOYEE_NAME", nullable = false)
    val employeeName: String,

    @Column(name="EMPLOYEE_UNIQUE_ID", nullable=false, unique=true)
    val employeeUniqueId: Long,

    @OneToMany(mappedBy="taskName", fetch=FetchType.EAGER, cascade = [CascadeType.ALL])
    @JsonIgnoreProperties("employee")
    var tasks: MutableList<Task> = mutableListOf()
)
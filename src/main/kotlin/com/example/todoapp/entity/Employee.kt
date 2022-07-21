package com.example.todoapp.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity(name = "Employee")
@Table(name = "Employee")
open class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = null

    @Column(name = "EMPLOYEE_NAME", nullable = false)
    var employeeName: String? = null

    @Column(name="UNIQUE_ID", nullable=false, unique=true)
    var uniqueId: Long? = null

    @OneToMany(mappedBy="taskName", fetch=FetchType.LAZY)
    @JsonManagedReference
    var tasksList: List<Task>? = emptyList()

}
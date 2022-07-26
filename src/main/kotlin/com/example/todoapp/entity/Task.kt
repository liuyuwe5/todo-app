package com.example.todoapp.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity(name = "Task")
@Table(name="Task")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name="TASK_NAME", nullable=false, unique=false)
    val taskName: String,

    @Column(name="IS_COMPLETED", nullable=false, unique=false)
    var isCompleted: Boolean = false,

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_ID")
    @JsonIgnoreProperties("tasks")
    var employee: Employee? = null
)
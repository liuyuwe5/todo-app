package com.example.todoapp.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity(name = "Task")
@Table(name="Task")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(name="TASK_NAME", nullable=false, unique=false)
    val taskName: String,

    @Column(name="IF_COMPLETED", nullable=false, unique=false)
    var ifCompleted: Boolean = false,

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "EXECUTOR")
    @JsonManagedReference
    var executor: Employee? = null
)
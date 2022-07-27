package com.example.todoapp.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity(name = "Task")
@Table(name="Task")
data class Task(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    val id: Long? = null,

    @Column(name="TASK_NAME", nullable=false, unique=false)
    val taskName: String,

    @Column(name="IS_COMPLETED", nullable=false, unique=false)
    var isCompleted: Boolean = false,

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_ID")
    var employee: Employee? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false
        if (taskName != other.taskName) return false
        if (isCompleted != other.isCompleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + taskName.hashCode()
        result = 31 * result + isCompleted.hashCode()
        return result
    }

    override fun toString(): String {
        return "Task(id=$id, taskName='$taskName', isCompleted=$isCompleted)"
    }

}
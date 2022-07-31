package com.example.todoapp.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "Employee")
@Table(name = "Employee")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null,

    @Column(name = "EMPLOYEE_NAME", nullable = false)
    var employeeName: String,

    @Column(name="EMPLOYEE_UNIQUE_ID", nullable=false, unique=true)
    var employeeUniqueId: Long,

    @OneToMany(mappedBy="employee", fetch=FetchType.EAGER, cascade = [CascadeType.ALL])
    @JsonIgnore
    var tasks: MutableList<Task> = mutableListOf()
) {
}
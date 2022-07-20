package com.example.todoapp.entity

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name="Task")
data class Task (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name="TASK_NAME", nullable=false, unique=false)
    var name: String? = null,

    @Column(name="IF_COMPLETED", nullable=false, unique=false)
    var ifCompleted: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Task

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , ifCompleted = $ifCompleted )"
    }
}


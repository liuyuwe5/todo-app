package com.example.todoapp.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import org.hibernate.Hibernate
import javax.persistence.*

@Entity(name = "Task")
@Table(name="Task")
data class Task (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(name="TASK_NAME", nullable=false, unique=false)
    var taskName: String? = null,

    @Column(name="IF_COMPLETED", nullable=false, unique=false)
    var ifCompleted: Boolean = false,

    @ManyToOne(fetch= FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "EXECUTOR")
    var executor: Employee? = null
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
        return this::class.simpleName + "(id = $id , taskName = $taskName , ifCompleted = $ifCompleted )"
    }
}


package todo.app.todomodels

import java.util.UUID

class GetToDoByStatusModel(
    val id: UUID,
    val taskName: String,

) {
    override fun toString(): String {
        return "ToDoItem(id='$id', taskName='$taskName')"
    }
}
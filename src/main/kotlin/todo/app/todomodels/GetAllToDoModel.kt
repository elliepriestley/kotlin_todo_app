package todo.app.todomodels

import java.util.*

class GetAllToDoModel(
    val id: UUID,
    val taskName: String,
    val status: ToDoItem.Status
) {
    override fun toString(): String {
        return "ToDoItem(id='$id', taskName='$taskName', status=$status)"
    }
}
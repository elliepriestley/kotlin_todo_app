package todo.app.todomodels

class GetAllToDoModel(
    val id: String,
    val taskName: String,
    val status: ToDoItem.Status
) {
    override fun toString(): String {
        return "ToDoItem(id='$id', taskName='$taskName', status=$status)"
    }
}
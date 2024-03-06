package todo.app.todomodels

class GetToDoByIdModel(
    val taskName: String,
    val status: ToDoItem.Status

) {
    override fun toString(): String {
        return "ToDoItem(taskName=$taskName', status='$status')"
    }
}
package todo.app.todomodels

class GetToDoByStatusModel(
    val id: String,
    val taskName: String,

) {
    override fun toString(): String {
        return "ToDoItem(id='$id', taskName='$taskName')"
    }
}
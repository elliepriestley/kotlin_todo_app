package todo.app.repo

class ToDoItem(
    val id: String,
    var taskName: String,
    var status: Status) {
    override fun toString(): String {
        return "ToDoItem(id='$id', taskName='$taskName', status=$status)"
    }

    enum class Status {
        DONE, NOT_DONE
    }
}

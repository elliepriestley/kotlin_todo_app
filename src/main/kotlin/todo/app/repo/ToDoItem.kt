package todo.app.repo

class ToDoItem(
    val id: String,
    val taskName: String,
    val status: Status) {
    override fun toString(): String {
        return "ToDoItem(id='$id', taskName='$taskName', status=$status)"
    }

    enum class Status {
        DONE, NOT_DONE
    }
}

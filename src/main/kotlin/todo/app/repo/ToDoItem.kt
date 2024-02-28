package todo.app.repo

class ToDoItem(
    val id: String,
    private val taskName: String,
    private val status: Status) {
    override fun toString(): String {
        return "ToDoItem(id='$id', taskName='$taskName', status=$status)"
    }

    enum class Status {
        DONE, NOT_DONE
    }
}

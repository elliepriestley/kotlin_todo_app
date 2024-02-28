package todo.app.repo

class ToDoItem(
    private val id: String,
    private val name: String,
    private val status: Status) {
    override fun toString(): String {
        return "ToDoItem(id='$id', taskName='$name', status=$status)"
    }

    enum class Status {
        DONE, NOT_DONE
    }
}

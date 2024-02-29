package todo.app.repo

interface ToDoRepoInterface {

    fun fetchAllToDoItems(): List<ToDoItem>

    fun fetchToDoItemById(id: String): ToDoItem?

    fun addToDoItem(toDoItem: ToDoItem)

    fun editToDoItemName(id: String, updatedToDoTaskName: String): ToDoItem?

    fun markToDoItemAsDone(id: String): ToDoItem?

    fun markToDoItemAsNotDone(id: String): ToDoItem?


}

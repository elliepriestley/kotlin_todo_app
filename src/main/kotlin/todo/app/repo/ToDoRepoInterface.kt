package todo.app.repo

interface ToDoRepoInterface {

    fun fetchAllToDoItems(): List<String>

    fun fetchToDoItemById(id: String): ToDoItem

    fun addToDoItem(toDoItem: ToDoItem)

    fun editToDoItem(id: String, fieldToUpdate: String, updatedText: String): ToDoItem


}

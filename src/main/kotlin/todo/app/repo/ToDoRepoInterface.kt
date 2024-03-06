package todo.app.repo

import todo.app.todomodels.ToDoItem

interface ToDoRepoInterface {

    fun fetchAllToDoItems(): List<ToDoItem>

    fun fetchToDoItemsByStatus(status: ToDoItem.Status): List<ToDoItem>

    fun fetchToDoItemById(id: String): ToDoItem?

    fun addToDoItem(toDoItem: ToDoItem)

    fun editToDoItemName(id: String, updatedToDoTaskName: String, editedDate: String): ToDoItem?

    fun markToDoItemAsDone(id: String): ToDoItem?

    fun markToDoItemAsNotDone(id: String): ToDoItem?

    fun generateIdNumber(): String


}

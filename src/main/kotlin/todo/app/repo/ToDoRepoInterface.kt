package todo.app.repo

import todo.app.todomodels.ToDoItem
import java.util.*

interface ToDoRepoInterface {

    fun fetchAllToDoItems(): List<ToDoItem>

    fun fetchToDoItemsByStatus(status: ToDoItem.Status): List<ToDoItem>

    fun fetchToDoItemById(id: UUID): ToDoItem?

    fun addToDoItem(toDoItem: ToDoItem)

    fun editToDoItemName(id: UUID, updatedToDoTaskName: String, editedDate: String): ToDoItem?

    fun markToDoItemAsDone(id: UUID): ToDoItem?

    fun markToDoItemAsNotDone(id: UUID): ToDoItem?

}

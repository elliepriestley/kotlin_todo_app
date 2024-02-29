package todo.app.domain

import todo.app.repo.FileTaskRepo

import todo.app.repo.ToDoItem
import todo.app.repo.ToDoRepoInterface


class Domain(private val toDoRepo: ToDoRepoInterface) {

    fun getAllTodos(): List<ToDoItem> {
        return toDoRepo.fetchAllToDoItems()
    }

    fun getToDoById(id: String): ToDoItem? {
        return toDoRepo.fetchToDoItemById(id)
    }

    fun addToDoItem(toDoItem: ToDoItem) {
        toDoRepo.addToDoItem(toDoItem)
    }

    fun editToDoItemName(id: String, updatedToDoTaskName: String): ToDoItem? {
        return toDoRepo.editToDoItemName(id, updatedToDoTaskName)
    }

    fun markToDoItemAsDone(id: String): ToDoItem? {
        return toDoRepo.markToDoItemAsDone(id)

    }

    fun markToDoItemAsNotDone(id: String):ToDoItem? {
        return toDoRepo.markToDoItemAsNotDone(id)
    }


}
fun main() {
    val fileRepo = FileTaskRepo()
    val domain = Domain(fileRepo)

}








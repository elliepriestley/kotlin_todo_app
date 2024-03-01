package todo.app.domain

import todo.app.repo.FileToDoRepo
import todo.app.repo.ToDoItem
import todo.app.repo.ToDoRepoInterface


class Domain(private val toDoRepo: ToDoRepoInterface) {

    fun getAllTodos(): List<ToDoItem> {
        return toDoRepo.fetchAllToDoItems()
    }

    fun getToDosByStatus(status: ToDoItem.Status): List<ToDoItem> {
        return toDoRepo.fetchToDoItemsByStatus(status)
    }

    fun getToDoById(id: String): ToDoItem? {
        return toDoRepo.fetchToDoItemById(id)
    }

    fun addToDoItem(toDoItem: ToDoItem) {
        toDoRepo.addToDoItem(toDoItem)
    }

    fun editToDoItemName(id: String, updatedToDoTaskName: String, editedDate: String): ToDoItem? {
        println("ToDoItem from domain: ${toDoRepo.editToDoItemName(id,updatedToDoTaskName,editedDate)}")
        return toDoRepo.editToDoItemName(id, updatedToDoTaskName, editedDate)
    }

    fun markToDoItemAsDone(id: String): ToDoItem? {
        return toDoRepo.markToDoItemAsDone(id)

    }

    fun markToDoItemAsNotDone(id: String):ToDoItem? {
        return toDoRepo.markToDoItemAsNotDone(id)
    }

    fun generateNewIdNumber(): String {
        return toDoRepo.generateIdNumber()
    }


}
fun main() {
    val fileRepo = FileToDoRepo()
    val domain = Domain(fileRepo)
    println(domain.getToDosByStatus(ToDoItem.Status.NOT_DONE))

}








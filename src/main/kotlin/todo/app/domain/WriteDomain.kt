package todo.app.domain

import todo.app.repo.FileToDoRepo
import todo.app.todomodels.ToDoItem
import todo.app.repo.ToDoRepoInterface

class WriteDomain(private val toDoRepo: ToDoRepoInterface) {

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

    fun markToDoItemAsNotDone(id: String): ToDoItem? {
        return toDoRepo.markToDoItemAsNotDone(id)
    }
}

fun main() {
    val fileRepo = FileToDoRepo()
    val writeDomain = WriteDomain(fileRepo)
}

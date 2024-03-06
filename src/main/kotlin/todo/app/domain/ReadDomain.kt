package todo.app.domain

import todo.app.repo.FileToDoRepo
import todo.app.repo.ToDoItem
import todo.app.repo.ToDoRepoInterface


class ReadDomain(private val toDoRepo: ToDoRepoInterface) {

    fun getAllTodos(): List<ToDoItem> {
        return toDoRepo.fetchAllToDoItems()
    }

    fun getToDosByStatus(status: ToDoItem.Status): List<ToDoItem> {
        return toDoRepo.fetchToDoItemsByStatus(status)
    }

    fun getToDoById(id: String): ToDoItem? {
        return toDoRepo.fetchToDoItemById(id)
    }

    fun generateNewIdNumber(): String {
        return toDoRepo.generateIdNumber()
    }


}
fun main() {
    val fileRepo = FileToDoRepo()
    val readDomain = ReadDomain(fileRepo)
    println(readDomain.getToDosByStatus(ToDoItem.Status.NOT_DONE))

}








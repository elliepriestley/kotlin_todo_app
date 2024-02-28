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


}
fun main() {
    val fileRepo = FileTaskRepo()
    val domain = Domain(fileRepo)
    println(domain.getToDoById("1"))
}








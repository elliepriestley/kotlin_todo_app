package todo.app.domain

import todo.app.repo.FileTaskRepo

import todo.app.repo.ToDoItem
import todo.app.repo.ToDoRepoInterface


class Domain(private val toDoRepo: ToDoRepoInterface) {

    fun getAllTodos(): List<ToDoItem> {
        val unprocessedToDoList = toDoRepo.fetchAllToDoItems()
        return unprocessedToDoList.map { taskName ->
            ToDoItem("randomID", taskName, ToDoItem.Status.NOT_DONE )
        }
    }


}
fun main() {
    val fileRepo = FileTaskRepo()
    val domain = Domain(fileRepo)
    println(domain.getAllTodos())
}








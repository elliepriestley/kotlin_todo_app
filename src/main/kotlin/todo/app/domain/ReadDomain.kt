package todo.app.domain

import todo.app.repo.FileToDoRepo
import todo.app.todomodels.ToDoItem
import todo.app.repo.ToDoRepoInterface
import todo.app.todomodels.GetAllToDoModel
import todo.app.todomodels.GetToDoByStatusModel


class ReadDomain(private val toDoRepo: ToDoRepoInterface) {

    fun getAllTodos(): List<GetAllToDoModel> {
        val listOfToDoItems = toDoRepo.fetchAllToDoItems()
        return listOfToDoItems.map { toDoItem  ->
            GetAllToDoModel(id = toDoItem.id, taskName = toDoItem.taskName, status = toDoItem.status)
        }

    }

    fun getToDosByStatus(status: ToDoItem.Status): List<GetToDoByStatusModel> {
        val listOfToDoItems = toDoRepo.fetchToDoItemsByStatus(status)
        return listOfToDoItems.map { toDoItem ->
            GetToDoByStatusModel(toDoItem.id, toDoItem.taskName)
        }
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
    println(readDomain.getToDosByStatus(ToDoItem.Status.DONE))

}








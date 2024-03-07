package todo.app.domain

import todo.app.eventmodel.EventName
import todo.app.repo.AppendEventRepoInterface
import todo.app.repo.FileAppendEventRepo
import todo.app.repo.FileToDoRepo
import todo.app.todomodels.ToDoItem
import todo.app.repo.ToDoRepoInterface
import todo.app.todomodels.GetAllToDoModel
import todo.app.todomodels.GetToDoByIdModel
import todo.app.todomodels.GetToDoByStatusModel
import java.util.*


class ReadDomain(private val toDoRepo: ToDoRepoInterface, private val eventsRepo: AppendEventRepoInterface) {

    fun getAllTodos(): List<GetAllToDoModel> {
        val listOfToDoItems = toDoRepo.fetchAllToDoItems()
        return listOfToDoItems.map { toDoItem  ->
            GetAllToDoModel(id = toDoItem.id, taskName = toDoItem.taskName, status = toDoItem.status)
        }
//        val eventsList = eventsRepo.fetchEvents()
//         in eventsList) {

        }



    fun getToDosByStatus(status: ToDoItem.Status): List<GetToDoByStatusModel> {
        val listOfToDoItems = toDoRepo.fetchToDoItemsByStatus(status)
        return listOfToDoItems.map { toDoItem ->
            GetToDoByStatusModel(toDoItem.id, toDoItem.taskName)
        }
    }

    fun getToDoById(taskId: UUID): GetToDoByIdModel? {
        val events = eventsRepo.fetchEventsByTaskId(taskId)
        var projectedToDoItem = ToDoItem(taskId, taskName = "filler", ToDoItem.Status.NOT_DONE)
        events.forEach { event ->
            when (event.eventName) {
                EventName.TODO_ITEM_CREATED -> projectedToDoItem.taskName = event.taskName!!
                EventName.TODO_ITEM_NAME_UPDATED -> projectedToDoItem.taskName = event.taskName!!
                EventName.TODO_ITEM_MARKED_AS_DONE -> projectedToDoItem.status = ToDoItem.Status.DONE
                EventName.TODO_ITEM_MARKED_AS_NOT_DONE -> projectedToDoItem.status = ToDoItem.Status.NOT_DONE
            }
        }
        val toDoItemInCorrectFormat = projectedToDoItem.let { GetToDoByIdModel(it.taskName, it.status) }
        return toDoItemInCorrectFormat
    }

    fun generateNewIdNumber(): UUID {
        return UUID.randomUUID()
    }


}
fun main() {

    val eventsRepo = FileAppendEventRepo()
    val todoRepo = FileToDoRepo()
    val readDomain = ReadDomain(todoRepo, eventsRepo)
    println(readDomain.getToDoById(UUID.fromString("ffb13047-9ac1-40dd-9942-fa1da0b041f2")))


}








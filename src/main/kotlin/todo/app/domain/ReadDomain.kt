package todo.app.domain

import todo.app.eventmodel.Event
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
        var listOfProjectedToDoItems = mutableListOf<ToDoItem>()
        val eventsList = eventsRepo.fetchAllEvents()
        val uniqueListOfToDos = eventsList.distinctBy { it.taskId }
        uniqueListOfToDos.forEach {toDoItem ->
            val projectedToDoItem = createProjectedToDoItem(toDoItem.taskId, eventsList)
            listOfProjectedToDoItems.add(projectedToDoItem)
        }

        return listOfProjectedToDoItems.map { toDoItem  ->
            GetAllToDoModel(id = toDoItem.id, taskName = toDoItem.taskName, status = toDoItem.status)
        }

        }



    fun getToDosByStatus(status: ToDoItem.Status): List<GetToDoByStatusModel> {
        val listOfToDoItems = toDoRepo.fetchToDoItemsByStatus(status)
        return listOfToDoItems.map { toDoItem ->
            GetToDoByStatusModel(toDoItem.id, toDoItem.taskName)
        }
    }

    fun getToDoById(taskId: UUID): GetToDoByIdModel? {
        val eventsList = eventsRepo.fetchEventsByTaskId(taskId)
        val projectedToDoItem = createProjectedToDoItem(taskId, eventsList)
        val toDoItemInCorrectFormat = projectedToDoItem.let { GetToDoByIdModel(it.taskName, it.status) }
        return toDoItemInCorrectFormat
    }

    fun generateNewIdNumber(): UUID {
        return UUID.randomUUID()
    }

    private fun createProjectedToDoItem(taskId: UUID, eventsList: List<Event>): ToDoItem {
        var projectedToDoItem = ToDoItem(taskId, taskName = "filler", ToDoItem.Status.NOT_DONE)
        eventsList.forEach { event ->
            when (event.eventName) {
                EventName.TODO_ITEM_CREATED -> projectedToDoItem.taskName = event.taskName!!
                EventName.TODO_ITEM_NAME_UPDATED -> projectedToDoItem.taskName = event.taskName!!
                EventName.TODO_ITEM_MARKED_AS_DONE -> projectedToDoItem.status = ToDoItem.Status.DONE
                EventName.TODO_ITEM_MARKED_AS_NOT_DONE -> projectedToDoItem.status = ToDoItem.Status.NOT_DONE
            }
        }
        return projectedToDoItem

    }


}
fun main() {

    val eventsRepo = FileAppendEventRepo()
    val todoRepo = FileToDoRepo()
    val readDomain = ReadDomain(todoRepo, eventsRepo)
    println(readDomain.getAllTodos())


}








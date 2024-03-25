package todo.app.domain

import todo.app.eventmodel.Event
import todo.app.eventmodel.EventName
import todo.app.repo.AppendEventRepoInterface
import todo.app.repo.FileAppendEventRepo
import todo.app.todomodels.ToDoItem
import todo.app.todomodels.GetAllToDoModel
import todo.app.todomodels.GetToDoByIdModel
import todo.app.todomodels.GetToDoByStatusModel
import java.util.*


class ReadDomain(private val eventsRepo: AppendEventRepoInterface) {

    fun getAllTodos(): List<GetAllToDoModel> {
        val listOfUniqueToDoEvents = eventsRepo.fetchAllEvents().distinctBy { it.taskId }

        return listOfUniqueToDoEvents.map { event ->
            val toDoItem = getToDoByIdFullToDoModel(event.taskId)
            (if (toDoItem != null) {
                GetAllToDoModel(id = toDoItem.id, taskName = toDoItem.taskName, status = toDoItem.status)
            } else {
                null
            })!!

        }
    }

    fun getToDosByStatus(status: String): List<GetToDoByStatusModel> {
        val statusEnum: ToDoItem.Status
        try {
            statusEnum = ToDoItem.Status.valueOf(status.uppercase(Locale.ROOT))
        } catch (e: IllegalArgumentException) {
            return emptyList()
        }
        val projectedToDos = getAllTodos()
        val toDosByStatus = projectedToDos.filter { it.status == statusEnum }
        return toDosByStatus.map { toDoItem ->
            GetToDoByStatusModel(toDoItem.id, toDoItem.taskName)
        }
    }

    fun getToDoById(taskId: String): GetToDoByIdModel? {
        val taskIdAsUUID = UUID.fromString(taskId)
        val eventsList = eventsRepo.fetchEventsByTaskId(taskIdAsUUID)
        return if (eventsList == null) {
            println("error finding todos for that id")
            null
        } else {
            val projectedToDoItem = createProjectedToDoItem(taskIdAsUUID, eventsList)
            val toDoItemInCorrectFormat = projectedToDoItem.let { GetToDoByIdModel(it.taskName, it.status) }
            toDoItemInCorrectFormat
        }
    }

    fun getToDoByIdFullToDoModel(taskId: UUID): ToDoItem? {
        val eventsList = eventsRepo.fetchEventsByTaskId(taskId)
        return if (eventsList != null) {
            val projectedToDoItem = createProjectedToDoItem(taskId, eventsList)
            projectedToDoItem
        } else {
             null
        }


    }

    private fun createProjectedToDoItem(taskId: UUID, eventsList: List<Event>): ToDoItem {
        val projectedToDoItem = ToDoItem(taskId, taskName = "filler", ToDoItem.Status.NOT_DONE)
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
    val readDomain = ReadDomain(eventsRepo)
    println(readDomain.getToDosByStatus("DONE"))


}








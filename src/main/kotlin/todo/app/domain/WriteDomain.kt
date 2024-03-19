package todo.app.domain

import todo.app.eventmodel.Event
import todo.app.eventmodel.EventName
import todo.app.repo.AppendEventRepoInterface
import todo.app.todomodels.ToDoItem

import java.util.*

class WriteDomain(private val appendEventRepo: AppendEventRepoInterface, private val readDomain: ReadDomain) {

    fun addToDoItem(toDoTaskName: String): ToDoItem? {
        val taskId = generateID()
        val event = Event(generateID(), EventName.TODO_ITEM_CREATED, "ellie.priestley", taskId, toDoTaskName)
        appendEventRepo.appendEvent(event)
        return readDomain.getToDoByIdFullToDoModel(taskId)
    }

    fun editToDoItemName(taskId: String, updatedToDoTaskName: String): ToDoItem? {
        val event = Event(generateID(), EventName.TODO_ITEM_NAME_UPDATED, "ellie.priestley", UUID.fromString(taskId), updatedToDoTaskName)
        appendEventRepo.appendEvent(event)
        val projectedToDoItem = readDomain.getToDoByIdFullToDoModel(UUID.fromString(taskId))
        return projectedToDoItem
    }

    fun markToDoItemAsDone(taskId: String): ToDoItem? {
        val eventSourcedToDoItem = readDomain.getToDoById(taskId)

        if (eventSourcedToDoItem != null) {
            val event = Event(generateID(), EventName.TODO_ITEM_MARKED_AS_DONE, "ellie.priestley", UUID.fromString(taskId), null)
            appendEventRepo.appendEvent(event)
        }
        val projectedToDoItem = readDomain.getToDoByIdFullToDoModel(UUID.fromString(taskId))
        return projectedToDoItem
    }

    fun markToDoItemAsNotDone(taskId: String): ToDoItem? {
        val eventSourcedToDoItem = readDomain.getToDoById(taskId)

        if (eventSourcedToDoItem != null) {
            val event = Event(generateID(), EventName.TODO_ITEM_MARKED_AS_NOT_DONE, "ellie.priestley", UUID.fromString(taskId), null)
            appendEventRepo.appendEvent(event)
        }
        val projectedToDoItem = readDomain.getToDoByIdFullToDoModel(UUID.fromString(taskId))
        return projectedToDoItem
    }

    private fun generateID(): UUID {
        return UUID.randomUUID()
    }
}

fun main() {
}

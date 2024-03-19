package todo.app.domain

import todo.app.eventmodel.Event
import todo.app.eventmodel.EventName
import todo.app.repo.AppendEventRepoInterface
import todo.app.todomodels.ToDoItem
import todo.app.repo.ToDoRepoInterface
import java.util.*

class WriteDomain(private val toDoRepo: ToDoRepoInterface, private val appendEventRepo: AppendEventRepoInterface, private val readDomain: ReadDomain) {

    fun addToDoItem(toDoTaskName: String): ToDoItem {
        val newToDoItem = ToDoItem(generateID(), toDoTaskName, ToDoItem.Status.NOT_DONE)
        val event = Event(generateID(), EventName.TODO_ITEM_CREATED, "ellie.priestley", newToDoItem.id, newToDoItem.taskName)
        appendEventRepo.appendEvent(event)

        return readDomain.getToDoByIdFullToDoModel(newToDoItem.id)

    }

    fun editToDoItemName(taskId: String, updatedToDoTaskName: String): ToDoItem? {
        val event = Event(generateID(), EventName.TODO_ITEM_NAME_UPDATED, "ellie.priestley", UUID.fromString(taskId), updatedToDoTaskName)
        appendEventRepo.appendEvent(event)
        val projectedToDoItem = readDomain.getToDoByIdFullToDoModel(UUID.fromString(taskId))
        return projectedToDoItem
    }

    fun markToDoItemAsDone(taskId: String): ToDoItem? {
        val toDoItem = toDoRepo.markToDoItemAsDone(UUID.fromString(taskId))
        if (toDoItem != null) {
            val event = Event(generateID(), EventName.TODO_ITEM_MARKED_AS_DONE, "ellie.priestley", UUID.fromString(taskId), null)
            appendEventRepo.appendEvent(event)
        }
        val projectedToDoItem = readDomain.getToDoByIdFullToDoModel(UUID.fromString(taskId))
        return projectedToDoItem
    }

    fun markToDoItemAsNotDone(taskId: String): ToDoItem? {
        val toDoItem = toDoRepo.markToDoItemAsNotDone(UUID.fromString(taskId))
        if (toDoItem != null) {
            val event = Event(generateID(), EventName.TODO_ITEM_MARKED_AS_NOT_DONE, "ellie.priestley", UUID.fromString(taskId), null)
            appendEventRepo.appendEvent(event)
        }
        return toDoItem
    }

    private fun generateID(): UUID {
        return UUID.randomUUID()
    }
}

fun main() {
}

package todo.app.domain

import todo.app.eventmodel.Event
import todo.app.eventmodel.EventName
import todo.app.repo.AppendEventRepoInterface
import todo.app.repo.FileAppendEventRepo
import todo.app.repo.FileToDoRepo
import todo.app.todomodels.ToDoItem
import todo.app.repo.ToDoRepoInterface
import java.util.*

class WriteDomain(private val toDoRepo: ToDoRepoInterface, private val appendEventRepo: AppendEventRepoInterface, private val readDomain: ReadDomain) {

    fun addToDoItem(toDoItem: ToDoItem) {
        val event = Event(generateID(), EventName.TODO_ITEM_CREATED, "ellie.priestley", toDoItem.id, toDoItem.taskName)
        appendEventRepo.appendEvent(event)

    }

    fun editToDoItemName(taskId: UUID, updatedToDoTaskName: String): ToDoItem? {
        val event = Event(generateID(), EventName.TODO_ITEM_NAME_UPDATED, "ellie.priestley", taskId, updatedToDoTaskName)
        appendEventRepo.appendEvent(event)
        val projectedToDoItem = readDomain.getToDoByIdFullToDoModel(taskId)
        return projectedToDoItem
    }

    fun markToDoItemAsDone(taskId: UUID): ToDoItem? {
        val toDoItem = toDoRepo.markToDoItemAsDone(taskId)
        if (toDoItem != null) {
            val event = Event(generateID(), EventName.TODO_ITEM_MARKED_AS_DONE, "ellie.priestley", taskId, null)
            appendEventRepo.appendEvent(event)
        }
        return toDoItem
    }

    fun markToDoItemAsNotDone(taskId: UUID): ToDoItem? {
        val toDoItem = toDoRepo.markToDoItemAsNotDone(taskId)
        if (toDoItem != null) {
            val event = Event(generateID(), EventName.TODO_ITEM_MARKED_AS_NOT_DONE, "ellie.priestley", taskId, null)
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

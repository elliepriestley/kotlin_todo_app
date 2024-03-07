package todo.app.domain

import todo.app.eventmodel.Event
import todo.app.eventmodel.EventName
import todo.app.repo.AppendEventRepoInterface
import todo.app.repo.FileAppendEventRepo
import todo.app.repo.FileToDoRepo
import todo.app.todomodels.ToDoItem
import todo.app.repo.ToDoRepoInterface
import java.util.*

class WriteDomain(private val toDoRepo: ToDoRepoInterface, private val appendEventRepo: AppendEventRepoInterface) {

    fun addToDoItem(toDoItem: ToDoItem) {
        toDoRepo.addToDoItem(toDoItem)
        val event = Event(generateID(), EventName.TODO_ITEM_CREATED, "ellie.priestley", toDoItem.id, toDoItem.taskName)
        appendEventRepo.appendEvent(event)

    }

    fun editToDoItemName(taskId: UUID, updatedToDoTaskName: String, editedDate: String): ToDoItem? {
        val todoItem = toDoRepo.editToDoItemName(taskId, updatedToDoTaskName, editedDate)
        val event = Event(generateID(), EventName.TODO_ITEM_NAME_UPDATED, "ellie.prietsley", taskId, updatedToDoTaskName)
        appendEventRepo.appendEvent(event)
        return todoItem
    }

    fun markToDoItemAsDone(id: UUID): ToDoItem? {
        return toDoRepo.markToDoItemAsDone(id)

    }

    fun markToDoItemAsNotDone(id: UUID): ToDoItem? {
        return toDoRepo.markToDoItemAsNotDone(id)
    }

    private fun generateID(): UUID {
        return UUID.randomUUID()
    }
}

fun main() {
}

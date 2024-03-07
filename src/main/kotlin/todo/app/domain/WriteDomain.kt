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
        val event = Event(generateID(), EventName.TODO_ITEM_CREATED, "now", "testUser", toDoItem.id)
        appendEventRepo.appendEvent(event)

    }

    fun editToDoItemName(id: UUID, updatedToDoTaskName: String, editedDate: String): ToDoItem? {
        println("ToDoItem from domain: ${toDoRepo.editToDoItemName(id,updatedToDoTaskName,editedDate)}")
        return toDoRepo.editToDoItemName(id, updatedToDoTaskName, editedDate)
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

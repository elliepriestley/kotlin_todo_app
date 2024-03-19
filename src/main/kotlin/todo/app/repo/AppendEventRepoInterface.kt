package todo.app.repo

import todo.app.eventmodel.Event
import java.util.*

interface AppendEventRepoInterface {
    fun appendEvent(event: Event): Event
    fun fetchAllEvents(): List<Event>

    fun fetchEventsByTaskId(taskId: UUID): List<Event>?
}



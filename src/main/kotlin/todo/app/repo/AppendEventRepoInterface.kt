package todo.app.repo

import todo.app.eventmodel.Event

interface AppendEventRepoInterface {
    fun appendEvent(event: Event): Event

}




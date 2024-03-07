package todo.app.eventmodel

import java.util.*

class Event(
    val eventId: UUID,
    val eventName: EventName,
    val eventCreatedDate: String,
    val eventCreator: String,
    val taskId: UUID
) {
    override fun toString(): String {
        return "Event(eventId=$eventId, eventName=$eventName, eventCreatedDate=$eventCreatedDate, eventCreator=$eventCreator, taskId=$taskId)"
    }
}

enum class EventName {
    TODO_ITEM_CREATED, TODO_ITEM_NAME_UPDATED, TODO_ITEM_MARKED_AS_DONE, TODO_ITEM_MARKED_AS_NOT_DONE
}
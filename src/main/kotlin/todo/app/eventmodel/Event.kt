package todo.app.eventmodel

class Event(
    val eventId: String,
    val eventName: EventName,
    val eventCreatedDate: String,
    val eventCreator: String
) {
    override fun toString(): String {
        return "Event(eventId=$eventId, eventName=$eventName, eventCreatedDate=$eventCreatedDate, eventCreator=$eventCreator)"
    }
}

enum class EventName {
    TODO_ITEM_CREATED, TODO_ITEM_NAME_UPDATED, TODO_ITEM_MARKED_AS_DONE, TODO_ITEM_MARKED_AS_NOT_DONE
}
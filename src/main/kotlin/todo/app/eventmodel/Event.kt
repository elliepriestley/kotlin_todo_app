package todo.app.eventmodel

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Event(
    val eventId: UUID,
    val eventName: EventName,
    @JsonProperty("createdEvent")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val eventCreatedDate: String,
    val eventCreator: String,
    val taskId: UUID,
    val taskName: String?
) {
    constructor(eventId: UUID, eventName: EventName, eventCreator: String, taskId: UUID, taskName: String?): this(
        eventId,
        eventName,
        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        eventCreator,
        taskId,
        taskName
    )


    override fun toString(): String {
        return "Event(eventId=$eventId, eventName=$eventName, eventCreatedDate=$eventCreatedDate, eventCreator=$eventCreator, taskId=$taskId, taskName=$taskName)"
    }
}

enum class EventName {
    TODO_ITEM_CREATED, TODO_ITEM_NAME_UPDATED, TODO_ITEM_MARKED_AS_DONE, TODO_ITEM_MARKED_AS_NOT_DONE
}
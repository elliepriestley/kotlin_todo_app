package todo.app.repo

import todo.app.eventmodel.Event
import todo.app.eventmodel.EventName
import java.io.File
import java.io.IOException

class FileAppendEventRepo: AppendEventRepoInterface {
    // needs to connect to todo_events.txt
    private val relativePath = "src/resources/todo_events.txt"
    private val file = File(relativePath)
    var eventItemsList: MutableList<Event> = file.inputStream().bufferedReader().useLines { lines ->
        lines.drop(1).mapNotNull { line ->
            if (line.split(",").size >= 4) {
                val (eventId, eventName,eventCreatedDate,eventCreator) = line.split(",")
                Event(eventId, EventName.valueOf(eventName), eventCreatedDate, eventCreator)
            } else {
                null
            }


        }.toMutableList()
    }

    override fun appendEvent(event: Event): Event {
        eventItemsList.add(event)
        saveEventListToFile()
        return event
    }

    private fun saveEventListToFile() {
        try {
            file.bufferedWriter().use { writer ->
                writer.write("eventId,eventName,eventCreatedDate,eventCreator\n")
                eventItemsList.forEach { event ->
                    writer.write("${event.eventId},${event.eventName},${event.eventCreatedDate},${event.eventCreator}\n")
                }
            }
            println("Events saved successfully.")
        } catch (e: IOException) {
            println("Failed to save the events list: ${e.message}")
            e.printStackTrace()
        }
    }

}


fun main() {
    val eventRepo = FileAppendEventRepo()
    val newEvent = Event("klajsd", EventName.TODO_ITEM_CREATED, "17-09-1995", "elliepriestley")

    eventRepo.appendEvent(newEvent)

}

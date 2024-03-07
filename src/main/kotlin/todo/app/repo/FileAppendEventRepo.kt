package todo.app.repo

import todo.app.eventmodel.Event
import todo.app.eventmodel.EventName
import java.io.File
import java.io.IOException
import java.util.*

class FileAppendEventRepo: AppendEventRepoInterface {
    private val relativePath = "src/resources/todo_events.txt"
    private val file = File(relativePath)
    var eventItemsList: MutableList<Event> = file.inputStream().bufferedReader().useLines { lines ->
        lines.drop(1).mapNotNull { line ->
            if (line.split(",").size >= 5) {
                val (eventId, eventName, eventCreatedDate, eventCreator, taskId) = line.split(",")
                Event(UUID.fromString(eventId), EventName.valueOf(eventName), eventCreatedDate, eventCreator, UUID.fromString(taskId))
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
                writer.write("eventId,eventName,eventCreatedDate,eventCreator,taskId\n")
                eventItemsList.forEach { event ->
                    writer.write("${event.eventId},${event.eventName},${event.eventCreatedDate},${event.eventCreator},${event.taskId}\n")
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


}

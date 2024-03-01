package todo.app.repo

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ToDoItem(
    val id: String,
    var taskName: String,
    @JsonProperty("createdDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdDate: String,
    var status: Status) {

    constructor(id: String, taskName: String, status: Status): this(
        id,
        taskName,
        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        status
    )
    override fun toString(): String {
        return "ToDoItem(id='$id', taskName='$taskName', status=$status)"
    }

    enum class Status {
        DONE, NOT_DONE
    }
}

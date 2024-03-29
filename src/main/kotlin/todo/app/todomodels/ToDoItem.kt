package todo.app.todomodels

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

data class ToDoItem(
    val id: UUID,
    var taskName: String,
    @JsonProperty("createdDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdDate: String,
    @JsonProperty("editedDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var editedDate: String? = null,
    var status: Status
) {

    constructor(id: UUID, taskName: String, status: Status): this(
        id,
        taskName,
        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        null,
        status
    )
    override fun toString(): String {
        return "ToDoItem(id='$id', taskName='$taskName', status=$status)"
    }

    enum class Status {
        DONE, NOT_DONE
    }
}

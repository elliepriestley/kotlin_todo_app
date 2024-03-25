package todo.app.api

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.http4k.core.HttpHandler
import org.http4k.core.Method.PATCH
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import todo.app.domain.ReadDomain
import todo.app.domain.WriteDomain
import todo.app.todomodels.ToDoItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HttpAPI(readDomain: ReadDomain, writeDomain: WriteDomain) {

    val app: HttpHandler = routes(
        "/todos" bind GET to { _ ->
            val toDoList = readDomain.getAllTodos()
            val jsonResponse = mapper.writeValueAsString(toDoList)
            Response(OK).body(jsonResponse)
        },

        "/todos/status/{status}" bind GET to { req ->
            val status: String? = req.path("status")
            if (status != "DONE" && status != "NOT_DONE") {
                Response(BAD_REQUEST).body("Invalid status type in URL")
            } else {
                val filteredToDoList = readDomain.getToDosByStatus(status)
                Response(OK).body(mapper.writeValueAsString(filteredToDoList))
            }
        },

        "/todos/{id}" bind GET to { req ->
            val toDoItemId: String? = req.path("id")
            val toDoItem = toDoItemId?.let { readDomain.getToDoById(toDoItemId) }

            if (toDoItem != null) {
                val jsonResponse = mapper.writeValueAsString(toDoItem)
                Response(OK).body(jsonResponse)
            } else {
                Response(NOT_FOUND).body("To do item not found")
            }
        },

        "/todos" bind POST to { req ->
            val jsonToDoTask = req.bodyString()
            val jsonNode = try {
                mapper.readTree(jsonToDoTask)
            } catch (e: Exception) {
                null
            }

            if (jsonNode != null && jsonNode.has("taskName")) {
                val taskName = jsonNode.get("taskName").asText()
                val newToDoItem = writeDomain.addToDoItem(taskName)
                val jsonResponse = mapper.writeValueAsString(newToDoItem)
                Response(OK).body(jsonResponse)
            } else {
                Response(BAD_REQUEST).body("Invalid JSON format or missing taskName field")
            }
        },

        "todos/{id}" bind PATCH to { req ->
            val toDoItemId: String?  = req.path("id")
            val jsonDataRequestedToPatch = req.bodyString()
            val jsonNodeRequestedToPatch = try {
                mapper.readTree(jsonDataRequestedToPatch)
            } catch (e: Exception) {
                null
            }

            if (jsonNodeRequestedToPatch != null) {
                when (returnWhichPropertyNeedsPatching(jsonNodeRequestedToPatch)) {
                    "taskName" -> updateToDoTaskNameAndGenerateJsonResponse(jsonNodeRequestedToPatch, toDoItemId, writeDomain)
                    "status" -> updateToDoTaskStatusAndGenerateJsonResponse(jsonNodeRequestedToPatch, toDoItemId, writeDomain)
                    "Request To Update Both" -> Response(BAD_REQUEST).body("Invalid Request. You may request to change one field per request")
                    else -> Response(BAD_REQUEST).body("Json Request does not exist or is invalid. You can only update taskName or status")
                }
            } else {
                Response(BAD_REQUEST).body("Json Request Node is null")
            }
        }

    )
    private val mapper = jacksonObjectMapper().apply {
        enable(SerializationFeature.INDENT_OUTPUT)
    }
    private fun returnWhichPropertyNeedsPatching(jsonNode: JsonNode): String {
        return when {
            jsonNode.has("taskName") && jsonNode.has("status") -> {
                "Request To Update Both"
            }
            jsonNode.has("taskName") -> {
                "taskName"
            }
            jsonNode.has("status") -> {
                "status"
            }
            else -> {
                "error"
            }
        }
    }

    private fun updateToDoTaskNameAndGenerateJsonResponse(jsonNodeRequestedToPatch: JsonNode, toDoItemId: String?, writeDomain: WriteDomain): Response {
        val taskName = jsonNodeRequestedToPatch.get("taskName").asText()
        val toDoItem = toDoItemId?.let {writeDomain.editToDoItemName(toDoItemId, taskName) }
        val jsonResponse = mapper.writeValueAsString(toDoItem)
        return Response(OK).body(jsonResponse)
    }

    private fun updateToDoTaskStatusAndGenerateJsonResponse(jsonNodeRequestedToPatch: JsonNode, toDoItemId: String?, readDomain: WriteDomain): Response {
        return if (jsonNodeRequestedToPatch.get("status").asText() == "NOT_DONE") {
            attemptToUpdateToDoItemAsNotDone(toDoItemId, readDomain)
        } else if (jsonNodeRequestedToPatch.get("status").asText() == "DONE") {
            attemptToUpdateToDoItemAsDone(toDoItemId, readDomain)
        } else {
            Response(BAD_REQUEST).body("Invalid Status Type in Request Body")
        }
    }

    private fun attemptToUpdateToDoItemAsDone(toDoItemId: String?, writeDomain: WriteDomain): Response {
        val toDoItem = toDoItemId?.let { writeDomain.markToDoItemAsDone(toDoItemId) }
        return if (toDoItem != null) {
            toDoItem.editedDate = LocalDateTime.now().format((DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            val jsonResponse = mapper.writeValueAsString(toDoItem)
            Response(OK).body(jsonResponse)
        } else {
            Response(NOT_FOUND).body("Problem accessing todo task")
        }
    }

    private fun attemptToUpdateToDoItemAsNotDone(toDoItemId: String?, writeDomain: WriteDomain): Response {
        val toDoItem = toDoItemId?.let { writeDomain.markToDoItemAsNotDone(toDoItemId) }
        return if (toDoItem != null) {
            toDoItem.editedDate = LocalDateTime.now().format((DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            val jsonResponse = mapper.writeValueAsString(toDoItem)
            Response(OK).body(jsonResponse)
        } else {
            Response(NOT_FOUND).body("Problem accessing todo task")
        }
    }




}




package todo.app.api

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
import todo.app.domain.Domain
import todo.app.repo.ToDoItem

class HttpAPI(domain: Domain) {
    private val mapper = jacksonObjectMapper().apply {
        enable(SerializationFeature.INDENT_OUTPUT)
    }

    val app: HttpHandler = routes(
        "/todos" bind GET to { _ ->
            val toDoList = domain.getAllTodos()
            Response(OK).body(toDoList.toString())

        },

        "/todos/{id}" bind GET to { req ->
            val toDoItemId: String? = req.path("id")
            val toDoItem = toDoItemId?.let { domain.getToDoById(it) }

            if (toDoItem != null) {
                Response(OK).body(toDoItem.toString())
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
                val newId = domain.generateNewIdNumber()
                val newToDoItem = ToDoItem(newId, taskName, ToDoItem.Status.NOT_DONE)
                domain.addToDoItem(newToDoItem)
                val jsonResponse = mapper.writeValueAsString(newToDoItem)
                Response(OK).body(jsonResponse)
            } else {
                Response(BAD_REQUEST).body("Invalid JSON format or missing taskName field")
            }
        },

        "/todos/{id}/done" bind PATCH to { req ->
            val toDoItemId: String? = req.path("id")
            val toDoItem = toDoItemId?.let { domain.markToDoItemAsDone(toDoItemId) }

            if (toDoItem != null) {
                Response(OK).body(toDoItem.toString())
            } else {
                Response(NOT_FOUND).body("Problem accessing todo task")
            }
        },

        "/todos/{id}/not_done" bind PATCH to { req ->
            val toDoItemId: String? = req.path("id")
            val toDoItem = toDoItemId?.let { domain.markToDoItemAsNotDone(toDoItemId) }
            if (toDoItem != null) {
                Response(OK).body(toDoItem.toString())
            } else {
                Response(NOT_FOUND).body("Problem accessing todo task")
            }
        },

        "todos/{id}/task_name" bind PATCH to { req ->
            val toDoItemId: String?  = req.path("id")
            val updatedTaskName: String = req.bodyString()
            val toDoItem = toDoItemId?.let {domain.editToDoItemName(toDoItemId, updatedTaskName) }

            if (toDoItem != null) {
                Response(OK).body(toDoItem.toString())
            } else {
                Response(NOT_FOUND).body("Problem accessing todo task")
            }
        }
    )

}




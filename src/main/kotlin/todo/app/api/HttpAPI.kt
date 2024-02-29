package todo.app.api

import org.http4k.core.HttpHandler
import org.http4k.core.Method.PATCH
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import todo.app.domain.Domain
import todo.app.repo.ToDoItem

class HttpAPI(domain: Domain) {
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
            val note = req.bodyString()
            domain.addToDoItem(ToDoItem(id = "test", note, ToDoItem.Status.NOT_DONE))
            Response(OK).body("You have added a note")
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




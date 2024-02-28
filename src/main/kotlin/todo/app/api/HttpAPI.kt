package todo.app.api

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.core.Status.Companion.OK
import org.http4k.lens.Query
import org.http4k.lens.string
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import todo.app.domain.Domain
import todo.app.repo.ToDosModel


var model = ToDosModel()

class HttpAPI(domain: Domain) {
    val optionalIdQuery = Query.string().optional("id")

    val app: HttpHandler = routes(
        "/todos" bind GET to { _ ->
            val toDoList = domain.getAllTodos()
            Response(OK).body(toDoList.toString())

        },
        "/todos/{id}" bind GET to { req ->
            val toDoItemId: String? = req.path("id")

            if (toDoItemId != null) {
                val toDoItem = domain.getToDoById(toDoItemId)
                Response(OK).body(toDoItem.toString())
            } else {
                Response(NOT_FOUND).body("oops")
            }
        },

        "/todos" bind POST to { req ->
            val note = req.bodyString()
            model.addTask(note)
            Response(OK).body("You have added a note")
        }
    )

}




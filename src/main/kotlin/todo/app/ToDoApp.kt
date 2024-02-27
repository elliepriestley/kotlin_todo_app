package todo.app

import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer


//var notes = mutableListOf("Walk the dog", "Feed the cat")
var model = ToDosModel()

val app: HttpHandler = routes(
    "/todos" bind GET to { _ ->
        Response(OK).body(model.returnTasks().toString())
    },

    "/todos" bind POST to { req ->
        val note = req.bodyString()
        model.addTask(note)
        Response(OK).body("You have added a note")
    }
)

fun main() {
    val printingApp: HttpHandler = PrintRequest().then(app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}

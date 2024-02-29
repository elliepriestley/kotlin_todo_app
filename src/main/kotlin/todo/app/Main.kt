package todo.app

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import todo.app.api.HttpAPI
import todo.app.domain.Domain
import todo.app.repo.FileToDoRepo

fun main() {
    val taskRepo = FileToDoRepo()
    val domain = Domain(taskRepo)
    val api = HttpAPI(domain)
    val printingApp: HttpHandler = DebuggingFilters.PrintRequest().then(api.app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}
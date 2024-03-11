package todo.app

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import todo.app.api.HttpAPI
import todo.app.domain.ReadDomain
import todo.app.domain.WriteDomain
import todo.app.repo.FileAppendEventRepo
import todo.app.repo.FileToDoRepo

fun main() {
    val taskRepo = FileToDoRepo()
    val eventRepo = FileAppendEventRepo()
    val readDomain = ReadDomain(eventRepo)
    val writeDomain = WriteDomain(taskRepo, eventRepo)
    val api = HttpAPI(readDomain, writeDomain)
    val printingApp: HttpHandler = DebuggingFilters.PrintRequest().then(api.app)

    val server = printingApp.asServer(SunHttp(9000)).start()

    println("Server started on " + server.port())
}
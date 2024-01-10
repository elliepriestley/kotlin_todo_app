package todo.app

import org.http4k.client.JavaHttpClient
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST


class ToDoAppClient(baseURL: String) {
    private val baseURL = baseURL
    private val client: HttpHandler = JavaHttpClient()


    fun getNotes(): String {
        val requestURL = "$baseURL/notes"
        return client(Request(GET, requestURL)).bodyString()

    }

    fun addNewNote(note: String): Response {
        val requestUrl = "$baseURL/notes"
        val request = Request(POST, requestUrl).body(note)
        return client(request)
    }
}






fun main() {
    val client = ToDoAppClient("http://localhost:9000")
    println(client.getNotes())
    client.addNewNote("Walk the dog")
    println(client.getNotes())


}

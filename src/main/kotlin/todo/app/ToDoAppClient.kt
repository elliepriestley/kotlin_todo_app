package todo.app

import org.http4k.client.JavaHttpClient
import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST



class ToDoAppClient(private val baseURL: String) {
    private val client = JavaHttpClient()


    fun getToDos(): String {
        val requestURL = "$baseURL/todos"
        return client(Request(GET, requestURL)).bodyString()

    }

    fun addNewToDo(note: String): Response {
        val requestUrl = "$baseURL/todos"
        val request = Request(POST, requestUrl).body(note)
        return client(request)
    }
}






fun main() {
    val client = ToDoAppClient("http://localhost:9000")
    println(client.getToDos())
    client.addNewToDo("Walk the dog")
    println(client.getToDos())


}

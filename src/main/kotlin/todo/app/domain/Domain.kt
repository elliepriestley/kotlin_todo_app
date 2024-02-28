package todo.app.domain

import org.http4k.core.*
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import todo.app.repo.TaskRepoInterface


class Domain(private val baseURL: String, taskRepo: TaskRepoInterface) {

//    fun getToDos(): String {
//        val requestURL = "$baseURL/todos"
//        return client(Request(GET, requestURL)).bodyString()
//
//    }

//    fun addNewToDo(note: String): Response {
//        val requestUrl = "$baseURL/todos"
//        val request = Request(POST, requestUrl).body(note)
//        return client(request)
//    }
}







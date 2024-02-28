package todo.app.repo

import java.io.FileNotFoundException


class FileTaskRepo : ToDoRepoInterface {
    private val resourceStream = this::class.java.classLoader.getResourceAsStream("todo_list.txt")

    init {
        if (resourceStream == null) {
            throw FileNotFoundException("No file found")
        }
    }

    private val toDoList: List<ToDoItem> = resourceStream.bufferedReader().useLines { lines ->
        lines.drop(1).map { line ->
            val (id, name, status) = line.split(",")
            ToDoItem(id, name, ToDoItem.Status.valueOf(status))
        }.toList()
    }



    override fun fetchAllToDoItems(): List<ToDoItem> {
        return toDoList
    }

    override fun fetchToDoItemById(id: String): ToDoItem?{
        return toDoList.find { toDoItem ->
            toDoItem.id == id
        }
    }

    override fun addToDoItem(toDoItem: ToDoItem) {
        TODO("Not yet implemented")
    }

    override fun editToDoItem(id: String, fieldToUpdate: String, updatedText: String): ToDoItem {
        TODO("Not yet implemented")
    }

}

fun main() {
    val fileRepo = FileTaskRepo()
    println(fileRepo.fetchToDoItemById("1"))
}
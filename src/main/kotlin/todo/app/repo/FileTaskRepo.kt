package todo.app.repo

import java.io.FileNotFoundException


class FileTaskRepo : ToDoRepoInterface {
    private val resourceStream = this::class.java.classLoader.getResourceAsStream("todo_list.txt")

    init {
        if (resourceStream == null) {
            throw FileNotFoundException("No file found")
        }
    }

    private val toDoList: List<String> = resourceStream.bufferedReader().useLines { it.toList() }

    override fun fetchAllToDoItems(): List<String> {
        return toDoList
    }

    override fun fetchToDoItemById(id: String): ToDoItem {
        TODO("Not yet implemented")
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
    println(fileRepo.fetchAllToDoItems())
}
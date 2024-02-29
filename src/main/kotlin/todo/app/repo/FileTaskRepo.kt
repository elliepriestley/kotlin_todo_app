package todo.app.repo

import java.io.*


class FileTaskRepo : ToDoRepoInterface {

    val absolutePath = "/Users/elliepriestley/IdeaProjects/ToDoApp/src/resources/todo_list.txt"
    val file = File(absolutePath)

    private val toDoList: List<ToDoItem> = file.inputStream().bufferedReader().useLines { lines ->
        lines.drop(1).map { line ->
            val (id, name, status) = line.split(",")
            ToDoItem(id, name, ToDoItem.Status.valueOf(status))
        }.toList()
    }

    override fun fetchAllToDoItems(): List<ToDoItem> {
        return toDoList
    }

    override fun fetchToDoItemById(id: String): ToDoItem? {
        return toDoList.find { toDoItem ->
            toDoItem.id == id
        }
    }

    override fun addToDoItem(toDoItem: ToDoItem) {
        try {
            val toDoItemString = "${toDoItem.id},${toDoItem.taskName},${toDoItem.status}\n"
            val filePath = "src/resources/todo_list.txt"
            val file = File(filePath)
            file.appendText(toDoItemString)
            println("Succeeded")
        } catch (error: IOException) {
            error.printStackTrace()
            println("failed")
        }
    }

    override fun editToDoItem(id: String, fieldToUpdate: String, updatedText: String): ToDoItem {
        TODO("Not yet implemented")
    }

}

fun main() {
    val fileRepo = FileTaskRepo()
    println(fileRepo.fetchToDoItemById("1"))

    println(fileRepo.fetchAllToDoItems())

//    val newToDoItem = ToDoItem("4", "Buy coffee beans", ToDoItem.Status.NOT_DONE)
//    fileRepo.addToDoItem(newToDoItem)

}
package todo.app.repo

import java.io.*


class FileToDoRepo : ToDoRepoInterface {
    private val absolutePath = "/Users/elliepriestley/IdeaProjects/ToDoApp/src/resources/todo_list.txt"
    private val file = File(absolutePath)
    var toDoList: List<ToDoItem> = file.inputStream().bufferedReader().useLines { lines ->
        lines.drop(1).map { line ->
            val (id, name, status) = line.split(",")
            ToDoItem(id, name, ToDoItem.Status.valueOf(status))
        }.toList()
    }

    fun refreshtoDoList() {
        toDoList = file.inputStream().bufferedReader().useLines { lines ->
            lines.drop(1).map { line ->
                val (id, name, status) = line.split(",")
                ToDoItem(id, name, ToDoItem.Status.valueOf(status))
            }.toList()
        }
    }

    override fun fetchAllToDoItems(): List<ToDoItem> {
        return toDoList
    }

    override fun fetchToDoItemsByStatus(status: ToDoItem.Status): List<ToDoItem> {
        return if (status == ToDoItem.Status.DONE) {
            toDoList.filter {it.status == ToDoItem.Status.DONE }
        } else {
            toDoList.filter { it.status == ToDoItem.Status.NOT_DONE }
        }
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

    override fun editToDoItemName(id: String, updatedToDoTaskName: String): ToDoItem? {
        val relevantToDoItem = toDoList.find { it.id == id }

        relevantToDoItem?.let { todoItem ->
            todoItem.taskName = updatedToDoTaskName
            saveToDoListToFile()
            return todoItem
        }
        println("ToDo item with ID $id not found.")
        return null
    }

    override fun markToDoItemAsDone(id: String): ToDoItem? {
        val relevantToDoItem = toDoList.find { it.id == id }

        relevantToDoItem?.let { todoItem ->
            todoItem.status = ToDoItem.Status.DONE
            saveToDoListToFile()
            return todoItem
        }
        println("ToDo item with ID $id not found.")
        return null
    }

    override fun markToDoItemAsNotDone(id: String): ToDoItem? {
        val relevantToDoItem = toDoList.find { it.id == id }

        relevantToDoItem?.let { todoItem ->
            todoItem.status = ToDoItem.Status.NOT_DONE
            saveToDoListToFile()
            return todoItem
        }
        println("ToDo item with ID $id not found.")
        return null
    }

    private fun saveToDoListToFile() {
        try {
            file.bufferedWriter().use { writer ->
                writer.write("id,taskName,status\n") // Write the header
                toDoList.forEach { item ->
                    writer.write("${item.id},${item.taskName},${item.status}\n")
                }
            }
            println("ToDo list saved successfully.")
        } catch (e: IOException) {
            println("Failed to save the todo list: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun generateIdNumber(): String {
        refreshtoDoList()
        val newIdNumber = (toDoList.count()+1).toString()
        return newIdNumber
    }

}

fun main() {
    val fileRepo = FileToDoRepo()
}
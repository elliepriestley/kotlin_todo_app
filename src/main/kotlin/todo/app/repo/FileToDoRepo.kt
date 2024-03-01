package todo.app.repo

import java.io.*


import java.io.*

class FileToDoRepo : ToDoRepoInterface {
    private val absolutePath = "/Users/elliepriestley/IdeaProjects/ToDoApp/src/resources/todo_list.txt"
    private val file = File(absolutePath)
    var toDoList: MutableList<ToDoItem> = file.inputStream().bufferedReader().useLines { lines ->
        lines.drop(1).map { line ->
            val (id, name, createdDate, editedDate, status) = line.split(",")
            ToDoItem(id, name, createdDate, editedDate, ToDoItem.Status.valueOf(status))
        }.toMutableList()
    }

    override fun fetchAllToDoItems(): List<ToDoItem> {
        return toDoList
    }

    override fun fetchToDoItemsByStatus(status: ToDoItem.Status): List<ToDoItem> {
        return toDoList.filter { it.status == status }
    }

    override fun fetchToDoItemById(id: String): ToDoItem? {
        return toDoList.find { it.id == id }
    }

    override fun addToDoItem(toDoItem: ToDoItem) {
        toDoList.add(toDoItem)
        saveToDoListToFile()
    }

    override fun editToDoItemName(id: String, updatedToDoTaskName: String, editedDate: String): ToDoItem? {
        val relevantToDoItem = toDoList.find { it.id == id }

        relevantToDoItem?.let { todoItem ->
            todoItem.taskName = updatedToDoTaskName
            todoItem.editedDate = editedDate
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
                writer.write("id,taskName,createdDate,editedDate,status\n") // Write the header
                toDoList.forEach { item ->
                    writer.write("${item.id},${item.taskName},${item.createdDate},${item.editedDate},${item.status}\n")
                }
            }
            println("ToDo list saved successfully.")
        } catch (e: IOException) {
            println("Failed to save the todo list: ${e.message}")
            e.printStackTrace()
        }
    }

    override fun generateIdNumber(): String {
        val generatedIdNumber = (toDoList.count() + 1).toString()
        return generatedIdNumber
    }
}


fun main() {
    val fileRepo = FileToDoRepo()
}
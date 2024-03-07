package todo.app.repo

import todo.app.todomodels.ToDoItem
import java.io.*
import java.util.*

class FileToDoRepo : ToDoRepoInterface {
    private val relativePath = "src/resources/todo_list.txt"
    private val file = File(relativePath)
    var toDoList: MutableList<ToDoItem> = file.inputStream().bufferedReader().useLines { lines ->
        lines.drop(1).map { line ->
            val (id, name, createdDate, editedDate, status) = line.split(",")
            ToDoItem(UUID.fromString(id), name, createdDate, editedDate, ToDoItem.Status.valueOf(status))
        }.toMutableList()
    }

    override fun fetchAllToDoItems(): List<ToDoItem> {
        return toDoList
    }

    override fun fetchToDoItemsByStatus(status: ToDoItem.Status): List<ToDoItem> {
        return toDoList.filter { it.status == status }
    }

    override fun fetchToDoItemById(id: UUID): ToDoItem? {
        return toDoList.find { it.id == id }
    }

    override fun addToDoItem(toDoItem: ToDoItem) {
        toDoList.add(toDoItem)
        saveToDoListToFile()
    }

    override fun editToDoItemName(id: UUID, updatedToDoTaskName: String, editedDate: String): ToDoItem? {
        val relevantToDoItem = toDoList.find { it.id == id }

        val todoItem = relevantToDoItem?.let { todoItem ->
                todoItem.taskName = updatedToDoTaskName
                todoItem.editedDate = editedDate
                saveToDoListToFile()
                todoItem
            }

        if (todoItem == null)  {
            println("ToDo item with ID $id not found.")
        }
        return todoItem
    }

    override fun markToDoItemAsDone(id: UUID): ToDoItem? {
        val relevantToDoItem = toDoList.find { it.id == id }

        val toDoItem = relevantToDoItem?.let { todoItem ->
            todoItem.status = ToDoItem.Status.DONE
            saveToDoListToFile()
            todoItem
        }

        if (toDoItem == null)  {
            println("ToDo item with ID $id not found.")
        }
        return toDoItem
    }

    override fun markToDoItemAsNotDone(id: UUID): ToDoItem? {
        val relevantToDoItem = toDoList.find { it.id == id }

        val toDoItem = relevantToDoItem?.let { todoItem ->
            todoItem.status = ToDoItem.Status.NOT_DONE
            saveToDoListToFile()
            todoItem
        }
        if (toDoItem == null)  {
            println("ToDo item with ID $id not found.")
        }
        return toDoItem
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

}


fun main() {
}
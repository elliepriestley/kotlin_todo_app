package todo.app

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ToDosModelTest {

    @Test
    fun `Test that addTask function adds task to the tasks list`() {
        val tasks = mutableListOf("Walk the dog", "Feed the cat")
        val model = ToDosModel(tasks)
        model.addTask("Go to the gym")

        assertTrue(model.tasks.contains("Go to the gym"))
    }

    @Test
    fun `Test that clearTasks function removes all elements from tasks list`() {
        val tasks = mutableListOf("Walk the dog", "Feed the cat")
        val model = ToDosModel(tasks)

        model.clearTasks()

        assertTrue(model.tasks.isEmpty())
    }

    @Test
    fun `Test that returnTasks method returns all tasks in task list`() {
        val tasks = mutableListOf("Walk the dog", "Feed the cat")
        val model = ToDosModel(tasks)

        assertEquals(listOf("Walk the dog", "Feed the cat"), model.returnTasks())
    }
}
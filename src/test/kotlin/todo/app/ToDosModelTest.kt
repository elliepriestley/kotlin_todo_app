package todo.app

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ToDosModelTest {

    @Test
    fun `Test that addTask function adds task to the tasks list`() {
        val model = ToDosModel()
        model.addTask("Go to the gym")

        assertTrue(model.tasks.contains("Go to the gym"))
    }

    @Test
    fun `Test that clearTasks function removes all elements from tasks list`() {
        val model = ToDosModel()

        model.addTask("Go to the gym")

        assertFalse(model.tasks.isEmpty())

        model.clearTasks()

        assertTrue(model.tasks.isEmpty())
    }

    @Test
    fun `Test that returnTasks method returns all tasks in task list`() {
        val model = ToDosModel()


        model.addTask("Go to the gym")
        assertEquals(listOf("Go to the gym"), model.returnTasks())
    }
}
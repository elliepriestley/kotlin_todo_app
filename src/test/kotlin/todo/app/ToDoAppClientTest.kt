package todo.app

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ToDoAppClientTest {

    @Test
    fun `Test that addNote method successfully adds a note to Task list`() {
        val client = ToDoAppClient("http://localhost:9000/todos")

//        assertEquals(, client.getNotes())

        client.addNewToDo("Task 1")

        assertEquals("[Task 1]", client.getToDos())

    }
}
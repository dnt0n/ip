package justachillguy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToDoTest {

    @Test
    void testToString_NotDone() {
        ToDo todo = new ToDo("Buy milk");
        assertEquals("[T][ ] Buy milk", todo.toString());
    }

    @Test
    void testToString_Done() {
        ToDo todo = new ToDo("Read book");
        todo.mark();
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    void testSaveFormat_NotDone() {
        ToDo todo = new ToDo("Clean room");
        assertEquals("T | 0 | Clean room", todo.saveFormat());
    }

    @Test
    void testSaveFormat_Done() {
        ToDo todo = new ToDo("Do homework");
        todo.mark();
        assertEquals("T | 1 | Do homework", todo.saveFormat());
    }

    @Test
    void testGetName() {
        ToDo todo = new ToDo("Walk dog");
        assertEquals("Walk dog", todo.getName());
    }

    @Test
    void testMarkAndUnmark() {
        ToDo todo = new ToDo("Exercise");
        assertFalse(todo.isDone());

        todo.mark();
        assertTrue(todo.isDone());

        todo.unmark();
        assertFalse(todo.isDone());
    }
}

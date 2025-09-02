package justachillguy;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void testParseCommandWithoutArgs() throws JustAChillGuyException {
        Object[] result = Parser.parseInputIntoCommandAndArgs("list");
        assertEquals(Command.LIST, result[0]);
        assertEquals("", result[1]); // no args
    }

    @Test
    void testParseCommandWithArgs() throws JustAChillGuyException {
        Object[] result = Parser.parseInputIntoCommandAndArgs("todo buy milk");
        assertEquals(Command.TODO, result[0]);
        assertEquals("buy milk", result[1]);
    }

    @Test
    void testParseUnknownCommand() throws JustAChillGuyException {
        Object[] result = Parser.parseInputIntoCommandAndArgs("lol something");
        assertEquals(Command.UNKNOWN, result[0]);
        assertEquals("something", result[1]);
    }

    @Test
    void testParseEmptyInput_ThrowsException() {
        assertThrows(JustAChillGuyException.class,
                () -> Parser.parseInputIntoCommandAndArgs("   "));
    }

    @Test
    void testParseNullInput_ThrowsException() {
        assertThrows(JustAChillGuyException.class,
                () -> Parser.parseInputIntoCommandAndArgs(null));
    }

    @Test
    void testParseValidDateTime() throws JustAChillGuyException {
        LocalDateTime result = Parser.parseStringIntoLocalDateTime("2003-6-19 1800");
        assertEquals(LocalDateTime.of(2003, 6, 19, 18, 0), result);
    }

    @Test
    void testParseValidDateTime_WithSpaces() throws JustAChillGuyException {
        LocalDateTime result = Parser.parseStringIntoLocalDateTime("   2025-9-3 0930   ");
        assertEquals(LocalDateTime.of(2025, 9, 3, 9, 30), result);
    }

    @Test
    void testParseInvalidDateTime_ThrowsException() {
        assertThrows(JustAChillGuyException.class,
                () -> Parser.parseStringIntoLocalDateTime("19-06-2003 1800")); // wrong format
    }

    @Test
    void testParseGarbageDateTime_ThrowsException() {
        assertThrows(JustAChillGuyException.class,
                () -> Parser.parseStringIntoLocalDateTime("not-a-date"));
    }
}

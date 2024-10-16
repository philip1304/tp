package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class HelpCommandTest {

    @Test
    public void testGeneralHelp() throws PillException {
        // Initialize test environment
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();
        HelpCommand helpCommand = new HelpCommand(null, false);

        // Declare expected output
        String expectedOutput = "Available commands:" + System.lineSeparator() +
                "  help    - Shows this help message" + System.lineSeparator() +
                "  add     - Adds a new item to the list" + System.lineSeparator() +
                "  delete  - Deletes an item from the list" + System.lineSeparator() +
                "  edit    - Edits an item in the list" + System.lineSeparator() +
                "  list    - Lists all items" + System.lineSeparator() +
                "  exit    - Exits the program" + System.lineSeparator() +
                "Type 'help <command>' for more information on a specific command." + System.lineSeparator() +
                "Type 'help <command> -v' for verbose output with examples." + System.lineSeparator();

        // Redirect Output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Test command
        helpCommand.execute(itemMap, storage);

        // Compare output
        String output = outputStream.toString();
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testSpecificHelp() throws PillException {
        // Initialize test environment
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();
        HelpCommand helpCommand = new HelpCommand("add", false);

        // Declare expected output
        String expectedOutput = "add: Adds a new item to the inventory." + System.lineSeparator() +
                System.lineSeparator() +
                "Correct input format: add <name> <quantity>" + System.lineSeparator();

        // Redirect Output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Test command
        helpCommand.execute(itemMap, storage);

        // Compare output
        String output = outputStream.toString();
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testVerboseSpecificHelp() throws PillException {
        // Initialize test environment
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();
        HelpCommand helpCommand = new HelpCommand("add", true);

        // Declare expected output
        String expectedOutput = "add: Adds a new item to the inventory." + System.lineSeparator() +
                "Usage: add <name> <quantity> <price>" + System.lineSeparator() +
                "  <name>     - Name of the item" + System.lineSeparator() +
                "  <quantity> - Initial quantity of the item" + System.lineSeparator() +
                System.lineSeparator() +
                "Example:" + System.lineSeparator() +
                "  add Aspirin 100" + System.lineSeparator() +
                System.lineSeparator() +
                "Correct input format: add <name> <quantity>" + System.lineSeparator();

        // Redirect Output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Test command
        helpCommand.execute(itemMap, storage);

        // Compare output
        String output = outputStream.toString();
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testCaseInsensitivity() throws PillException {
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();
        HelpCommand helpCommand = new HelpCommand("ADD", false);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        helpCommand.execute(itemMap, storage);

        String output = outputStream.toString();
        assertTrue(output.contains("add:"), "Help should recognize command regardless of case");
    }

    @Test
    public void testTypoSuggestionEdgeCase() throws PillException {
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();
        HelpCommand helpCommand = new HelpCommand("lst", false);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        helpCommand.execute(itemMap, storage);

        String output = outputStream.toString();
        assertTrue(output.contains("Did you mean: list?"), "Should suggest 'list' for 'lst'");
    }

    @Test
    public void testEmptyInput() throws PillException {
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();
        HelpCommand helpCommand = new HelpCommand("", false);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        helpCommand.execute(itemMap, storage);

        String output = outputStream.toString();
        assertTrue(output.contains("Available commands:"), "Should show general help for empty input");
    }

    @Test
    public void testExtraArguments() throws PillException {
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();
        HelpCommand helpCommand = new HelpCommand("add extra args", false);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        helpCommand.execute(itemMap, storage);

        String output = outputStream.toString();
        assertTrue(output.contains("add:"), "Should ignore extra arguments and show help for 'add'");
    }

    @Test
    public void testIsExitAlwaysFalse() {
        HelpCommand helpCommand = new HelpCommand("exit", false);
        assertFalse(helpCommand.isExit(), "isExit should always return false for HelpCommand");
    }

    @Test
    public void testPerformance() throws PillException {
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();
        HelpCommand helpCommand = new HelpCommand(null, false);

        long startTime = System.nanoTime();
        helpCommand.execute(itemMap, storage);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime) / 1_000_000;  // Convert to milliseconds
        assertTrue(duration < 100, "Help command should execute in less than 100ms");
    }
}

package seedu.pill.command;

import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
    public void testTypoSuggestion() throws PillException {
        // Initialize test environment
        ItemMap itemMap = new ItemMap();
        Storage storage = new Storage();
        HelpCommand helpCommand = new HelpCommand("hlep", false);

        // Declare expected output
        String expectedOutput = "Unknown command: hlep" + System.lineSeparator() +
                "Did you mean: help?" + System.lineSeparator() +
                "Type 'help help' for more information on this command." + System.lineSeparator() +
                System.lineSeparator() +
                "Available commands: help, add, delete, edit, list, exit" + System.lineSeparator() +
                "Type 'help <command>' for more information on a specific command." + System.lineSeparator();

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
}

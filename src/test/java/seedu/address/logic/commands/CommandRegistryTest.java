package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class CommandRegistryTest {

    @Test
    public void getUsageMap_containsAllExpectedCommands() {
        Set<String> keys = CommandRegistry.getUsageMap().keySet();
        assertEquals(18, keys.size());
        assertTrue(keys.contains(AddCommand.COMMAND_WORD));
        assertTrue(keys.contains(DeleteCommand.COMMAND_WORD));
        assertTrue(keys.contains(EditCommand.COMMAND_WORD));
        assertTrue(keys.contains(NoteCommand.COMMAND_WORD));
        assertTrue(keys.contains(PlanCommand.COMMAND_WORD));
        assertTrue(keys.contains(StatusCommand.COMMAND_WORD));
        assertTrue(keys.contains(MeasureCommand.COMMAND_WORD));
        assertTrue(keys.contains(RateCommand.COMMAND_WORD));
        assertTrue(keys.contains(LogCommand.COMMAND_WORD));
        assertTrue(keys.contains(LastCommand.COMMAND_WORD));
        assertTrue(keys.contains(FindCommand.COMMAND_WORD));
        assertTrue(keys.contains(FilterCommand.COMMAND_WORD));
        assertTrue(keys.contains(SortCommand.COMMAND_WORD));
        assertTrue(keys.contains(ViewCommand.COMMAND_WORD));
        assertTrue(keys.contains(ListCommand.COMMAND_WORD));
        assertTrue(keys.contains(ClearCommand.COMMAND_WORD));
        assertTrue(keys.contains(HelpCommand.COMMAND_WORD));
        assertTrue(keys.contains(ExitCommand.COMMAND_WORD));
    }

    @Test
    public void getUsageMap_noNullKeysOrValues() {
        for (Map.Entry<String, String> entry : CommandRegistry.getUsageMap().entrySet()) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
            assertFalse(entry.getValue().isBlank());
        }
    }

    @Test
    public void getUsageMap_isUnmodifiable() {
        assertThrows(UnsupportedOperationException.class, () ->
                CommandRegistry.getUsageMap().put("hack", "hacked"));
    }
}

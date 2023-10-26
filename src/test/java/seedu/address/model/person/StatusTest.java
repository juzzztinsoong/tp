package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "Prospecting";
        assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null status
        assertDoesNotThrow(() -> Status.isValidStatus(null));

        // blank status
        assertTrue(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only

        // invalid parts
        assertFalse(Status.isValidStatus("Insured"));

        // valid status
        assertTrue(Status.isValidStatus("active"));
        assertTrue(Status.isValidStatus(""));
        assertTrue(Status.isValidStatus("Prospective"));
        assertTrue(Status.isValidStatus("Active"));
        assertTrue(Status.isValidStatus("Inactive"));
        assertTrue(Status.isValidStatus("Renewal"));
        assertTrue(Status.isValidStatus("Claimant"));
        assertTrue(Status.isValidStatus("NIL"));
    }

    @Test
    public void equals() {
        Status status = new Status("Active");

        // same values -> returns true
        assertTrue(status.equals(new Status("Active")));

        // same object -> returns true
        assertTrue(status.equals(status));

        // null -> returns false
        assertFalse(status.equals(null));

        // different types -> returns false
        assertFalse(status.equals(5.0f));

        // different values -> returns false
        assertFalse(status.equals(new Status("Inactive")));
    }
}


package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LASTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.TitleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditMeetingDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_TITLE_MEETING1 = "CS2103T meeting";
    public static final String VALID_TITLE_MEETING2 = "Lunch with coworkers";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_LOCATION_MEETING1 = "Zoom call url";
    public static final String VALID_LOCATION_MEETING2 = "Food court";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_LASTTIME_AMY = "20.09.2023 1100";
    public static final String VALID_LASTTIME_BOB = "20.09.2023 1200";
    public static final String VALID_START_MEETING1 = "20.09.2023 1000";
    public static final String VALID_START_MEETING2 = "11.10.2023 0900";
    public static final String VALID_STATUS_AMY = "";
    public static final String VALID_STATUS_BOB = "prospective";
    public static final String VALID_REMARK_AMY = "Likes to swim";
    public static final String VALID_REMARK_BOB = "";
    public static final String VALID_END_MEETING1 = "20.09.2023 1200";
    public static final String VALID_END_MEETING2 = "11.10.2023 1300";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_WORK = "work";
    public static final String VALID_ATTENDEE_ALICE = "Alice Pauline";
    public static final String VALID_ATTENDEE_BOB = "Bob Choo";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String TITLE_DESC_MEETING1 = " " + PREFIX_TITLE + VALID_TITLE_MEETING1;
    public static final String TITLE_DESC_MEETING2 = " " + PREFIX_TITLE + VALID_TITLE_MEETING2;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String LOCATION_DESC_MEETING1 = " " + PREFIX_LOCATION + VALID_LOCATION_MEETING1;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String LASTTIME_DESC_AMY = " " + PREFIX_LASTTIME + VALID_LASTTIME_AMY;
    public static final String LASTTIME_DESC_BOB = " " + PREFIX_LASTTIME + VALID_LASTTIME_BOB;
    public static final String START_DESC_MEETING1 = " " + PREFIX_START + VALID_START_MEETING1;
    public static final String STATUS_DESC_AMY = " " + PREFIX_STATUS + "nil";
    public static final String STATUS_DESC_BOB = " " + PREFIX_STATUS + VALID_STATUS_BOB;
    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String END_DESC_MEETING1 = " " + PREFIX_END + VALID_END_MEETING1;
    public static final String END_DESC_MEETING2 = " " + PREFIX_END + VALID_END_MEETING2;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String TAG_DESC_WORK = " " + PREFIX_TAG + VALID_TAG_WORK;
    public static final String ATTENDEE_DESC_ALICE = " " + PREFIX_NAME + VALID_ATTENDEE_ALICE;
    public static final String ATTENDEE_DESC_BOB = " " + PREFIX_NAME + VALID_ATTENDEE_BOB;
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + " "; // " " blank is not allowed in Titles
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION + " "; // blank is not allowed in Location
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_LASTTIME_DESC = " " + PREFIX_LASTTIME + "20.21.2023"; // wrong DateTime format
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "Insured"; // not in predefined status list
    public static final String INVALID_START_DESC = " " + PREFIX_START + "99.99.9999 9999";
    public static final String INVALID_END_DESC = " " + PREFIX_END + "99.99.9999 9999";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_ATTENDEE_DESC = INVALID_NAME_DESC;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withLastContactedTime(VALID_LASTTIME_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withLastContactedTime(VALID_LASTTIME_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }
    public static final EditMeetingCommand.EditMeetingDescriptor DESC_MEET1;
    public static final EditMeetingCommand.EditMeetingDescriptor DESC_MEET2;

    static {
        DESC_MEET1 = new EditMeetingDescriptorBuilder().withTitle(VALID_TITLE_MEETING1)
                .withLocation(VALID_LOCATION_MEETING1).withStart(VALID_START_MEETING1)
                .withEnd(VALID_END_MEETING1).withTags(VALID_TAG_WORK).build();
        DESC_MEET2 = new EditMeetingDescriptorBuilder().withTitle(VALID_TITLE_MEETING2)
                .withLocation(VALID_LOCATION_MEETING2).withStart(VALID_START_MEETING2)
                .withEnd(VALID_END_MEETING2).withTags(VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredPersonList = new ArrayList<>(actualModel.getFilteredPersonList());
        List<Meeting> expectedFilteredMeetingList = new ArrayList<>(actualModel.getFilteredMeetingList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredPersonList, actualModel.getFilteredPersonList());
        assertEquals(expectedFilteredMeetingList, actualModel.getFilteredMeetingList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the meeting at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showMeetingAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMeetingList().size());

        Meeting meeting = model.getFilteredMeetingList().get(targetIndex.getZeroBased());
        final String[] splitName = meeting.getTitle().toString().split("\\s+");
        model.updateFilteredMeetingList(new TitleContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredMeetingList().size());
    }

}

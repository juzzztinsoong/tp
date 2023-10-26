package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_MEETINGS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMeetings.MEETING1;
import static seedu.address.testutil.TypicalMeetings.MEETING2;
import static seedu.address.testutil.TypicalMeetings.MEETING3;
import static seedu.address.testutil.TypicalMeetings.MEETING4;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.AttendeeContainsKeywordsPredicate;
import seedu.address.model.meeting.GeneralMeetingPredicate;
import seedu.address.model.meeting.LocationContainsKeywordsPredicate;
import seedu.address.model.meeting.MeetingTimeContainsPredicate;
import seedu.address.model.meeting.TitleContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;
import seedu.address.testutil.TypicalMeetings;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindMeetingCommandTest {
    private Model model = new ModelManager(TypicalMeetings.getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(TypicalMeetings.getTypicalAddressBook(), new UserPrefs());
    private LocalDateTime start = LocalDateTime.of(LocalDate.of(0001, 01, 01), LocalTime.of(00, 00));
    private LocalDateTime startOn30 = LocalDateTime.of(LocalDate.of(2023, 9, 30), LocalTime.of(10, 00));
    private LocalDateTime endOn30 = LocalDateTime.of(LocalDate.of(2023, 9, 30), LocalTime.of(12, 00));
    private LocalDateTime start2 = LocalDateTime.of(LocalDate.of(0001, 01, 02), LocalTime.of(00, 00));
    private LocalDateTime end = LocalDateTime.of(LocalDate.of(9999, 12, 31), LocalTime.of(23, 59));
    @Test
    public void equals() {
        GeneralMeetingPredicate firstPredicate =
                new GeneralMeetingPredicate(new TitleContainsKeywordsPredicate(List.of("")),
                        new LocationContainsKeywordsPredicate(List.of("")),
                        new MeetingTimeContainsPredicate(start, end),
                        new AttendeeContainsKeywordsPredicate(List.of("")),
                        new TagContainsKeywordsPredicate(List.of("")));
        GeneralMeetingPredicate secondPredicate =
                new GeneralMeetingPredicate(new TitleContainsKeywordsPredicate(List.of("")),
                        new LocationContainsKeywordsPredicate(List.of("")),
                        new MeetingTimeContainsPredicate(start2, end),
                        new AttendeeContainsKeywordsPredicate(List.of("")),
                        new TagContainsKeywordsPredicate(List.of("")));

        FindMeetingCommand filterFirstCommand = new FindMeetingCommand(firstPredicate);
        FindMeetingCommand filterSecondCommand = new FindMeetingCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FindMeetingCommand filterFirstCommandCopy = new FindMeetingCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different predicates -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_allMeetingFound() {
        int expectedNumber = model.getFilteredMeetingList().size();
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, expectedNumber);
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"", "", "", ""},
                        start, end);
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING1, MEETING2, MEETING3, MEETING4), model.getFilteredMeetingList());
    }

    @Test
    public void execute_titleAbcdeKeywords_oneMeetingFound() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 1);
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"ABCDE", "", "", ""},
                        start, end);
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING4), model.getFilteredMeetingList());
    }

    @Test
    public void execute_titleAbcdeCS2101Keywords_threeMeetingFound() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 3);
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"ABCDE CS2101", "", "", ""},
                        start, end);
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING2, MEETING3, MEETING4), model.getFilteredMeetingList());
    }

    @Test
    public void execute_multipleTitleAndLocationKeywords_oneMeetingFound() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 1);
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"ABCDE CS2101", "Zoom", "", ""},
                        start, end);
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING2), model.getFilteredMeetingList());
    }

    @Test
    public void execute_multipleTitleAndMultipleLocationKeywords_oneMeetingFound() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 3);
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"ABCDE CS2101", "Zoom com", "", ""},
                        start, end);
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING2, MEETING3, MEETING4), model.getFilteredMeetingList());
    }

    @Test
    public void execute_multipleTitleMultipleLocationAndAttendeeKeywords_twoMeetingFound() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 2);
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"ABCDE CS2101", "Zoom com", "Hoon", ""},
                        start, end);
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING3, MEETING4), model.getFilteredMeetingList());
    }

    @Test
    public void execute_multipleTitleMultipleLocationAndMultipleAttendeeKeywords_twoMeetingFound() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 3);
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"ABCDE CS2101", "Zoom com", "Alice Benson", ""},
                        start, end);
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING2, MEETING3, MEETING4), model.getFilteredMeetingList());
    }

    @Test
    public void execute_multipleTitleMultipleLocationMultipleAttendeeAndTagKeywords_oneMeetingFound() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 1);
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"ABCDE CS2101", "Zoom com", "Alice Benson", "work"},
                        start, end);
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING4), model.getFilteredMeetingList());
    }

    @Test
    public void execute_multipleTitleMultipleLocationMultipleAttendeeMultipleTagKeywords_oneMeetingFound() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 1);
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"ABCDE CS2101", "Zoom com", "Alice Benson", "work important"},
                        start, end);
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING4), model.getFilteredMeetingList());
    }

    @Test
    public void execute_titleLocationAttendeeTagTimeKeywords_oneMeetingFound() {
        String expectedMessage = String.format(MESSAGE_MEETINGS_LISTED_OVERVIEW, 1);
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"ABCDE CS2101", "Zoom com", "Alice Benson", "work important"},
                        startOn30, endOn30);
        FindMeetingCommand command = new FindMeetingCommand(predicate);
        expectedModel.updateFilteredMeetingList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(MEETING4), model.getFilteredMeetingList());
    }

    @Test
    public void toStringMethod() {
        GeneralMeetingPredicate predicate = new GeneralMeetingPredicate(
                new TitleContainsKeywordsPredicate(List.of("Keywords")),
                new LocationContainsKeywordsPredicate(List.of("Keywords")),
                new MeetingTimeContainsPredicate(start, end),
                new AttendeeContainsKeywordsPredicate(List.of("Keywords")),
                new TagContainsKeywordsPredicate(List.of("Keywords")));
        FindMeetingCommand findMeetingCommand = new FindMeetingCommand(predicate);
        String expected = FindMeetingCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findMeetingCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private GeneralMeetingPredicate preparePredicate(String[] userInput, LocalDateTime start, LocalDateTime end) {
        return new GeneralMeetingPredicate(new TitleContainsKeywordsPredicate(List.of(userInput[0].split("\\s+"))),
                        new LocationContainsKeywordsPredicate(List.of(userInput[1].split("\\s+"))),
                        new MeetingTimeContainsPredicate(start, end),
                        new AttendeeContainsKeywordsPredicate(List.of(userInput[2].split("\\s+"))),
                        new TagContainsKeywordsPredicate(List.of(userInput[3].split("\\s+"))));
    }
}

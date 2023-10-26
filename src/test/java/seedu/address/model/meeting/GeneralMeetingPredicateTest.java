package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.TagContainsKeywordsPredicate;
import seedu.address.testutil.MeetingBuilder;

public class GeneralMeetingPredicateTest {

    private LocalDateTime start = LocalDateTime.of(LocalDate.of(0001, 01, 01), LocalTime.of(00, 00));
    private LocalDateTime startOn30 = LocalDateTime.of(LocalDate.of(2023, 9, 30), LocalTime.of(10, 00));
    private LocalDateTime endOn30 = LocalDateTime.of(LocalDate.of(2023, 9, 30), LocalTime.of(12, 00));
    private LocalDateTime start2 = LocalDateTime.of(LocalDate.of(0001, 01, 02), LocalTime.of(00, 00));
    private LocalDateTime end = LocalDateTime.of(LocalDate.of(9999, 12, 31), LocalTime.of(23, 59));
    @Test
    public void equals() {
        GeneralMeetingPredicate firstPredicate = preparePredicate(new String[]{"", "", "", ""}, start, end);
        GeneralMeetingPredicate secondPredicate = preparePredicate(new String[]{"CS2103T", "", "", ""}, start, end);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GeneralMeetingPredicate firstPredicateCopy = preparePredicate(new String[]{"", "", "", ""}, start, end);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containsKeywords_returnsTrue() {
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"CS2103T", "", "", ""}, start, end);
        assertTrue(predicate.test(new MeetingBuilder().withTitle("CS2103T").build()));

        predicate = preparePredicate(new String[]{"", "Zoom", "", ""}, start, end);
        assertTrue(predicate.test(new MeetingBuilder().withLocation("Zoom call url").build()));

        predicate = preparePredicate(new String[]{"", "", "Alice", ""}, start, end);
        assertTrue(predicate.test(new MeetingBuilder().withAttendees("Alice").build()));

        predicate = preparePredicate(new String[]{"", "", "", "friend"}, start, end);
        assertTrue(predicate.test(new MeetingBuilder().withTags("friend").build()));

        predicate = preparePredicate(new String[]{"", "", "", ""}, start, end);
        assertTrue(predicate.test(new MeetingBuilder()
                .withStart("20.09.2023 1000")
                .withEnd("20.09.2023 1200")
                .build()));

        predicate = preparePredicate(new String[]{"CS2103T", "Zoom", "Alice", "friend"}, start, end);
        assertTrue(predicate.test(new MeetingBuilder()
                .withTitle("CS2103T")
                .withLocation("Zoom call url")
                .withAttendees("Alice")
                .withTags("friend")
                .withStart("20.09.2023 1000")
                .withEnd("20.09.2023 1200")
                .build()));

        predicate = preparePredicate(new String[]{"", "", "", ""}, start, end);
        assertTrue(predicate.test(new MeetingBuilder()
                .withTitle("CS2103T")
                .withLocation("Zoom call url")
                .withAttendees("Alice")
                .withTags("friend")
                .withStart("20.09.2023 1000")
                .withEnd("20.09.2023 1200")
                .build()));

        predicate = preparePredicate(new String[]{"cs2103t", "zOOM", "aLICE", "FRIEND"}, start, end);
        assertTrue(predicate.test(new MeetingBuilder()
                .withTitle("CS2103T")
                .withLocation("Zoom call url")
                .withAttendees("Alice")
                .withTags("friend")
                .withStart("20.09.2023 1000")
                .withEnd("20.09.2023 1200")
                .build()));
    }

    @Test
    public void test_containsKeywords_returnsFalse() {
        GeneralMeetingPredicate predicate =
                preparePredicate(new String[]{"ABCDE", "", "", ""}, start, end);
        assertFalse(predicate.test(new MeetingBuilder().withTitle("CS2103T").build()));

        predicate = preparePredicate(new String[]{"", "Doom", "", ""}, start, end);
        assertFalse(predicate.test(new MeetingBuilder().withLocation("Zoom call url").build()));

        predicate = preparePredicate(new String[]{"", "", "A", ""}, start, end);
        assertFalse(predicate.test(new MeetingBuilder().withAttendees("Alice").build()));

        predicate = preparePredicate(new String[]{"", "", "", "f"}, start, end);
        assertFalse(predicate.test(new MeetingBuilder().withTags("friend").build()));

        predicate = preparePredicate(new String[]{"", "", "", ""}, startOn30, endOn30);
        assertFalse(predicate.test(new MeetingBuilder()
                .withStart("20.09.2023 1000")
                .withEnd("20.09.2023 1200")
                .build()));

        predicate = preparePredicate(new String[]{"D", "Zoom", "Alice", "friend"}, start, end);
        assertFalse(predicate.test(new MeetingBuilder()
                .withTitle("CS2103T")
                .withLocation("Zoom call url")
                .withAttendees("Alice")
                .withTags("friend")
                .withStart("20.09.2023 1000")
                .withEnd("20.09.2023 1200")
                .build()));

        predicate = preparePredicate(new String[]{"", "", "", ""}, startOn30, endOn30);
        assertFalse(predicate.test(new MeetingBuilder()
                .withTitle("CS2103T")
                .withLocation("Zoom call url")
                .withAttendees("Alice")
                .withTags("friend")
                .withStart("20.09.2023 1000")
                .withEnd("20.09.2023 1200")
                .build()));
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

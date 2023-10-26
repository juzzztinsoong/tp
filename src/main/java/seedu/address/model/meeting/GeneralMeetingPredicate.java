package seedu.address.model.meeting;

import java.util.function.Predicate;

import seedu.address.model.tag.TagContainsKeywordsPredicate;


/**
 * The predicate class that brings together of all the other predicate class.
 */
public class GeneralMeetingPredicate implements Predicate<Meeting> {
    private final TitleContainsKeywordsPredicate titlePredicate;
    private final LocationContainsKeywordsPredicate locationPredicate;
    private final MeetingTimeContainsPredicate meetingTimePredicate;
    private final AttendeeContainsKeywordsPredicate attendeePredicate;
    private final TagContainsKeywordsPredicate tagPredicate;

    /**
     * Constructs a predicate class that fulfills all the argument predicates
     * @param titlePredicate A predicate that test a meeting's title.
     * @param locationPredicate A predicate that test a meeting's location.
     * @param meetingTimePredicate A predicate that test a meeting's time.
     * @param attendeePredicate A predicate that test a meeting's attendees.
     * @param tagPredicate A predicate that test a meeting's tags.
     */
    public GeneralMeetingPredicate(TitleContainsKeywordsPredicate titlePredicate,
                                             LocationContainsKeywordsPredicate locationPredicate,
                                             MeetingTimeContainsPredicate meetingTimePredicate,
                                             AttendeeContainsKeywordsPredicate attendeePredicate,
                                             TagContainsKeywordsPredicate tagPredicate) {
        this.titlePredicate = titlePredicate;
        this.locationPredicate = locationPredicate;
        this.meetingTimePredicate = meetingTimePredicate;
        this.attendeePredicate = attendeePredicate;
        this.tagPredicate = tagPredicate;
    }

    @Override
    public boolean test(Meeting meeting) {
        return titlePredicate.test(meeting)
                && locationPredicate.test(meeting)
                && meetingTimePredicate.test(meeting)
                && attendeePredicate.test(meeting)
                && tagPredicate.test(meeting);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GeneralMeetingPredicate)) {
            return false;
        }

        GeneralMeetingPredicate otherGeneralMeetingPredicate = (GeneralMeetingPredicate) other;
        return titlePredicate.equals(otherGeneralMeetingPredicate.titlePredicate)
                && locationPredicate.equals(otherGeneralMeetingPredicate.locationPredicate)
                && meetingTimePredicate.equals(otherGeneralMeetingPredicate.meetingTimePredicate)
                && attendeePredicate.equals(otherGeneralMeetingPredicate.attendeePredicate)
                && tagPredicate.equals(otherGeneralMeetingPredicate.tagPredicate);
    }

}

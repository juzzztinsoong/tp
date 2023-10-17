package seedu.address.model.meeting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Meeting in the address book.
 * Guarantees: details are present and not null, field values are validated,
 * immutable.
 */
public class Meeting {
    private final Title title;
    private final Location location;
    private final MeetingTime meetingTime;
    private final Set<Attendee> attendees;

    /**
     * Every field must be present and not null.
     */
    public Meeting(Title title, Location location, LocalDateTime start, LocalDateTime end, Set<Attendee> attendees) {
        this.title = title;
        this.location = location;
        this.meetingTime = new MeetingTime(start, end);
        this.attendees = new LinkedHashSet<>(attendees);
    }

    public Title getTitle() {
        return title;
    }

    public Location getLocation() {
        return location;
    }

    public LocalDateTime getStart() {
        return meetingTime.getStart();
    }

    public LocalDateTime getEnd() {
        return meetingTime.getEnd();
    }

    public MeetingTime getMeetingTime() {
        return meetingTime;
    }

    /**
     * Returns an immutable attendee set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Attendee> getAttendees() {
        return Collections.unmodifiableSet(attendees);
    }

    /**
     * Returns an attendee at the given {@code index}
     */
    public Attendee getAttendee(Index index) {
        // TODO: catch index out of bounds
        return new ArrayList<>(attendees).get(index.getZeroBased());
    }

    /**
     * Returns true if {@code otherMeeting} has the same identity as this meeting.
     */
    public boolean isSameMeeting(Meeting otherMeeting) {
        if (otherMeeting == this) {
            return true;
        }

        return otherMeeting != null
                && otherMeeting.getTitle().equals(getTitle())
                && otherMeeting.getLocation().equals(getLocation())
                && otherMeeting.getMeetingTime().equals(getMeetingTime());
    }

    /**
     * Returns true if both meetings have the same title and data fields.
     * This defines a stronger notion of equality between two meetings.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Meeting)) {
            return false;
        }

        Meeting otherMeeting = (Meeting) other;
        return title.equals(otherMeeting.title)
                && location.equals(otherMeeting.location)
                && meetingTime.equals(otherMeeting.meetingTime)
                && attendees.equals(otherMeeting.attendees);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, location, meetingTime, attendees);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("title", title)
                .add("location", location)
                .add("start", meetingTime.getStart())
                .add("end", meetingTime.getEnd())
                .add("attendees", attendees)
                .toString();
    }

    /**
     * Returns detailed information of Meeting for viewm command.
     */
    public String toDisplayString() {
        return String.format("Title: %s\nLocation: %s\nStart: %s\nEnd: %s\nAttendees: %s\n", title, location,
                MeetingTime.toDisplayFormat(meetingTime.getStart()), MeetingTime.toDisplayFormat(meetingTime.getEnd()),
                attendees);
    }
}

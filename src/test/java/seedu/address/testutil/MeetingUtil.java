package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.ParserUtil.FORMAT;

import java.util.Set;

import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.commands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Meeting.
 */
public class MeetingUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddMeetingCommand(Meeting meeting) {
        return AddMeetingCommand.COMMAND_WORD + " " + getMeetingDetails(meeting);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getMeetingDetails(Meeting meeting) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TITLE + meeting.getTitle().meetingTitle + " ");
        sb.append(PREFIX_LOCATION + meeting.getLocation().location + " ");
        sb.append(PREFIX_START + meeting.getStart().format(FORMAT) + " ");
        sb.append(PREFIX_END + meeting.getEnd().format(FORMAT) + " ");
        meeting.getAttendees().stream().forEach(
                s -> sb.append(PREFIX_NAME + s.getAttendeeName() + " ")
        );
        meeting.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditMeetingDescriptor}'s details.
     */
    public static String getEditMeetingDescriptorDetails(EditMeetingDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getTitle().ifPresent(title -> sb.append(PREFIX_TITLE).append(title.meetingTitle).append(" "));
        descriptor.getLocation().ifPresent(location -> sb.append(PREFIX_LOCATION).append(location.location)
                .append(" "));
        descriptor.getStart().ifPresent(start -> sb.append(PREFIX_START).append(start.format(FORMAT)).append(" "));
        descriptor.getEnd().ifPresent(end -> sb.append(PREFIX_END).append(end.format(FORMAT)).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}

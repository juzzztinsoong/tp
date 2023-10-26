package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.logging.Logger;

import seedu.address.Main;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.meeting.Attendee;
import seedu.address.model.meeting.Meeting;

/**
 * Adds a meeting to the address book.
 */
public class AddMeetingCommand extends Command {

    public static final String COMMAND_WORD = "addm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the address book. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_START + "START "
            + PREFIX_END + "END "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Meeting with friends "
            + PREFIX_LOCATION + "Starbucks "
            + PREFIX_START + "18.10.2023 1200 "
            + PREFIX_END + "18.10.2023 1400 "
            + PREFIX_TAG + "work "
            + PREFIX_TAG + "impt ";

    public static final String MESSAGE_SUCCESS = "New meeting added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book";
    public static final String MESSAGE_ATTENDEE_NOT_FOUND = "Meeting attendee is not found in Persons list";

    private static Logger logger = LogsCenter.getLogger(Main.class);
    private final Meeting toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddMeetingCommand(Meeting meeting) {
        requireNonNull(meeting);
        toAdd = meeting;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Begin AddMeetingCommand execution");
        requireNonNull(model);

        if (model.hasMeeting(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }
        for (Attendee attendee : toAdd.getAttendees()) {
            if (!model.hasName(attendee.getAttendeeName())) {
                throw new CommandException(MESSAGE_ATTENDEE_NOT_FOUND);
            }
        }

        model.addMeeting(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMeetingCommand)) {
            return false;
        }

        AddMeetingCommand otherAddMeetingCommand = (AddMeetingCommand) other;
        return toAdd.equals(otherAddMeetingCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.Main;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.meeting.GeneralMeetingPredicate;

/**
 * Finds and lists all meetings in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindMeetingCommand extends Command {

    public static final String COMMAND_WORD = "findm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all meetings that contain at least 1 "
            + "specified KEYWORD in all of the different fields, as well as stating the window of time they "
            + "are specifying, and displays them as a list with index numbers.\n"
            + "If no KEYWORD is specified for that field, the find will not be affected that field.\n"
            + "Parameters: m/KEYWORDS a/KEYWORDS n/KEYWORDS t/KEYWORDS s/LOCALDATETIME e/LOCALDATETIME\n"
            + "Example: " + COMMAND_WORD + " m/CS2103T Meeting s/01.09.2023 1000 e/30.09.2023 1200";

    private static Logger logger = LogsCenter.getLogger(Main.class);
    private final GeneralMeetingPredicate predicate;

    /**
     * Constructs a FindMeetingCommand object.
     * @param predicate The predicate that will be used by the FindMeetingCommand object.
     */
    public FindMeetingCommand(GeneralMeetingPredicate predicate) {
        assert predicate != null;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        logger.info("Begin FindMeetingCommand execution");
        requireNonNull(model);
        model.updateFilteredMeetingList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEETINGS_LISTED_OVERVIEW, model.getFilteredMeetingList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindMeetingCommand)) {
            return false;
        }

        FindMeetingCommand otherFindMeetingCommand = (FindMeetingCommand) other;
        return predicate.equals(otherFindMeetingCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

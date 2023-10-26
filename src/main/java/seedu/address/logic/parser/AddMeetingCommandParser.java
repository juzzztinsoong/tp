package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.Main;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Attendee;
import seedu.address.model.meeting.Location;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingTime;
import seedu.address.model.meeting.Title;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddMeetingCommandParser implements Parser<AddMeetingCommand> {

    private static Logger logger = LogsCenter.getLogger(Main.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMeetingCommand parse(String args) throws ParseException {
        logger.info("Begin AddMeetingCommand Parse");
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_LOCATION, PREFIX_START, PREFIX_END, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_LOCATION, PREFIX_START, PREFIX_END)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TITLE, PREFIX_LOCATION, PREFIX_START, PREFIX_END);
        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION).get());
        LocalDateTime start = ParserUtil.parseMeetingTime(argMultimap.getValue(PREFIX_START).get());
        LocalDateTime end = ParserUtil.parseMeetingTime(argMultimap.getValue(PREFIX_END).get());
        if (!MeetingTime.isValidMeetingTime(start, end)) {
            throw new ParseException(MeetingTime.MESSAGE_CONSTRAINTS);
        }
        Set<Attendee> attendeeList = ParserUtil.parseAttendees(argMultimap.getAllValues(PREFIX_NAME));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Meeting meeting = new Meeting(title, location, start, end, attendeeList, tagList);

        return new AddMeetingCommand(meeting);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

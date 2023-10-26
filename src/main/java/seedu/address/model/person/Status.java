package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Locale;

/**
 * Represents a Person's status
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
*/
public class Status {
    /**
     * Represents the available statuses that can be assigned to a Person.
     */
    protected enum StatusList {
        nil,
        prospective,
        active,
        inactive,
        claimant,
        renewal,
    }

    public static final String DEFAULT_STATUS = "NIL";

    public static final String MESSAGE_CONSTRAINTS =
            "Status should be 1 of { NIL, Prospective, Active, Inactive, Claimant, Renewal } or blank.\n"
            + "Will be set to NIL by default if left blank.";

    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        this.value = (status == null || status.isEmpty()) ? DEFAULT_STATUS : status;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }
        for (StatusList enumValue : StatusList.class.getEnumConstants()) {
            if (enumValue.name().equals(input.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return value.equals(otherStatus.value);
    }

    @Override
    public String toString() {
        return value;
    }

}

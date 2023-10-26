package seedu.address.model.tag;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.meeting.Meeting;

/**
 * Tests that a {@code Meetings}'s {@code Title} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Meeting> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Meeting meeting) {
        return keywords.stream()
                .anyMatch(keyword -> keyword.isEmpty() || meeting.getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherTagContainsKeywordsPredicate = (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

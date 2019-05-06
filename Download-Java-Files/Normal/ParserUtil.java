package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attributes.Amount;
import seedu.address.model.attributes.Category;
import seedu.address.model.attributes.Date;
import seedu.address.model.attributes.Frequency;
import seedu.address.model.attributes.Name;
import seedu.address.model.attributes.Occurrence;
import seedu.address.model.attributes.View;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String amount} into a {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!Amount.isValidAmount(trimmedAmount)) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(trimmedAmount);
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        switch (Date.isValidDate(trimmedDate)) {
        case "format":
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        case "does not exist":
            throw new ParseException(Date.MESSAGE_DATE_DOES_NOT_EXIST);
        default:
            break;
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String frequency} into an {@code Frequency}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code frequency} is invalid.
     */
    public static Frequency parseFrequency(String frequency) throws ParseException {
        requireNonNull(frequency);
        String trimmedFrequency = frequency.trim();
        if (!Frequency.isValidFrequency(trimmedFrequency)) {
            throw new ParseException(Frequency.MESSAGE_CONSTRAINTS);
        }
        return new Frequency(trimmedFrequency);
    }

    /**
     * Parses a {@code String occurrence} into an {@code Occurrence}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code occurrence} is invalid.
     */
    public static Occurrence parseOccurrence(String occurrence) throws ParseException {
        requireNonNull(occurrence);
        String trimmedOccurrence = occurrence.trim();
        if (!Occurrence.isValidOccurrence(trimmedOccurrence)) {
            throw new ParseException(Occurrence.MESSAGE_CONSTRAINTS);
        }
        return new Occurrence(occurrence);
    }

    /**
     * Parses a {@code String view} into an {@code view}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code view} is invalid.
     */
    public static View parseView(String view) throws ParseException {
        requireNonNull(view);
        String trimmedView = view.trim().toUpperCase();

        try {
            View.valueOf(trimmedView);
        } catch (IllegalArgumentException e) {
            throw new ParseException(View.MESSAGE_CONSTRAINTS);
        }

        return View.valueOf(trimmedView);
    }

    /**
     * Parses a {@code String category} into an {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code category} is invalid.
     */
    public static Category parseCategory(String category) throws ParseException {
        requireNonNull(category);
        String trimmedCategory = category.trim().toUpperCase();

        try {
            Category.valueOf(trimmedCategory);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Category.MESSAGE_CONSTRAINTS);
        }

        return Category.valueOf(trimmedCategory);
    }


}

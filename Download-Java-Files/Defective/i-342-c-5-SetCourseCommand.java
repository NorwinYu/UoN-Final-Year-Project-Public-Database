package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Course;
import seedu.address.model.Model;

/**
 * Adds a person to the address book.
 */
public class SetCourseCommand extends Command {

    public static final String COMMAND_WORD = "study";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the user's course. "
            + "Parameters: "
            + "CS / CEG";

    public static final String MESSAGE_SUCCESS = "Course is set. ";

    private final Course course;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public SetCourseCommand(Course course) {
        requireNonNull(course);
        this.course = course;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.setCourse(course);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, course));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetCourseCommand // instanceof handles nulls
                && course.equals(((SetCourseCommand) other).course));
    }
}

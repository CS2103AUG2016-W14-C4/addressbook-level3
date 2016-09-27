package seedu.addressbook.commands;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.AddressBook.TagDoesNotExistException;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;

import static seedu.addressbook.data.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import java.util.List;

/*
 * List all users under a particular tag.
 */
public class ListPersonsUnderTagCommand extends Command {
	
	 public static final String COMMAND_WORD = "listtag";

	 public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
	         + "Displays all persons in the address book as a list under a particular tag.\n\t"
			 + "Parameters: TAG\n\t"
	         + MESSAGE_TAG_CONSTRAINTS + "\n\t"
	         + "Example: " + COMMAND_WORD + " friend";
	
	 
	 
	 // attribute
	 
	 private Tag tag;
	 
	 // constructor
	 
	 public ListPersonsUnderTagCommand(Tag tag) {
		 this.tag = tag;
	 }

	@Override
	public CommandResult execute() {
		try {
			List<ReadOnlyPerson> personsUnderTag = addressBook.getPersonsUnderTag(tag);
            if (personsUnderTag.isEmpty()) {
                return new CommandResult(Messages.MESSAGE_NO_PERSONS_UNDER_TAG);
            }
            return new CommandResult(String.format(getMessageForPersonsUnderTagShownSummary(personsUnderTag, tag)), personsUnderTag);
        } catch (TagDoesNotExistException e) {
            return new CommandResult(String.format(Messages.MESSAGE_DOES_NOT_EXIST, tag));
        }
	}

}

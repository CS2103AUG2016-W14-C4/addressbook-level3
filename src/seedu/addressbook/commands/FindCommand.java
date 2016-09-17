package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.*;
import static java.util.stream.Collectors.toList;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithNameContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose names contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithNameContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        keywords = convertToLowerCase(keywords);
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            List<String> list = convertListToLowerCase(person);
            final Set<String> wordsInName = new HashSet<>(list);
            if (!Collections.disjoint(wordsInName, keywords)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }

    private List<String> convertListToLowerCase(ReadOnlyPerson person) {
        List<String> list = person.getName().getWordsInName();
        list = list.stream().map(String::toLowerCase).collect(toList());
        return list;
    }

    private Set<String> convertToLowerCase(Set<String> keywords) {
        ArrayList<String> newList = new ArrayList<String>();
        for (String keyword: keywords) {
            newList.add(keyword.toLowerCase());
        }
        return new HashSet<>(newList);
    }

}

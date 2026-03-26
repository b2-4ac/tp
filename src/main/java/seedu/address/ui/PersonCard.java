package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays a summary of a {@code Person} in the client list.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String PHONE_LABEL_PREFIX = "Phone number: ";
    private static final String LOCATION_LABEL_PREFIX = "Gym Location: ";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As
     * a consequence, UI elements' variable names cannot be set to such keywords or an exception
     * will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on
     *      AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label phone;
    @FXML
    private Label gymLocation;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        name.setText(person.getName().fullName);
        gender.setText(person.getGender().value.toString());
        phone.setText(PHONE_LABEL_PREFIX + person.getPhone().value);
        gymLocation.setText(LOCATION_LABEL_PREFIX + person.getLocation().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}

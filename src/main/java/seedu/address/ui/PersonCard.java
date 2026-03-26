package seedu.address.ui;

import static seedu.address.model.person.Status.StatusEnum.ACTIVE;
import static seedu.address.model.person.Status.StatusEnum.INACTIVE;

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
    private static final String PREFIX_PHONE_LABEL = "Phone number: ";
    private static final String PREFIX_LOCATION_LABEL = "Gym Location: ";

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
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label phone;
    @FXML
    private Label gymLocation;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(String.valueOf(displayedIndex));
        name.setText(person.getName().fullName);
        gender.setText(person.getGender().value.toString());
        phone.setText(PREFIX_PHONE_LABEL + person.getPhone().value);
        gymLocation.setText(PREFIX_LOCATION_LABEL + person.getLocation().value);
        status.setText(person.getStatus().value.toString());
        status.getStyleClass().removeAll("cell_status_active", "cell_status_inactive");
        switch (person.getStatus().value) {
        case ACTIVE -> status.getStyleClass().add("cell_status_active");
        case INACTIVE -> status.getStyleClass().add("cell_status_inactive");
        default -> throw new IllegalStateException(
                "Unexpected status value: " + person.getStatus().value);
        }
        person.getTags().stream().sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}


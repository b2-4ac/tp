package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * Panel that displays the full details of a selected {@code Person}.
 */
public class PersonDetailPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailPanel.fxml";

    private Person currentPerson;

    @FXML
    private StackPane detailPanelRoot;
    @FXML
    private VBox placeholderView;
    @FXML
    private VBox personDetailView;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label dob;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label gymLocation;
    @FXML
    private Label rate;
    @FXML
    private Label height;
    @FXML
    private Label weight;
    @FXML
    private Label bodyFat;
    @FXML
    private Label address;
    @FXML
    private Label note;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonDetailPanel} showing the placeholder message.
     */
    public PersonDetailPanel() {
        super(FXML);
    }

    /**
     * Displays the full details of the given {@code Person}.
     */
    public void displayPerson(Person person) {
        this.currentPerson = person;

        name.setText(person.getName().fullName);
        gender.setText(person.getGender().value.toString());
        dob.setText(person.getDateOfBirth().toString());
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        gymLocation.setText(person.getLocation().value);
        address.setText(person.getAddress().value);
        rate.setText(person.getRate().value.isEmpty() ? "N/A" : person.getRate().value);
        height.setText(person.getHeight().value.isEmpty() ? "N/A" : person.getHeight().value);
        weight.setText(person.getWeight().value.isEmpty() ? "N/A" : person.getWeight().value);
        bodyFat.setText(person.getBodyFatPercentage().value.isEmpty() ? "N/A" : person.getBodyFatPercentage().value);
        note.setText(person.getNote().value.isEmpty() ? "N/A" : person.getNote().value);

        tags.getChildren().clear();
        person.getTags().stream().sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        placeholderView.setVisible(false);
        placeholderView.setManaged(false);
        personDetailView.setVisible(true);
        personDetailView.setManaged(true);
    }

    /**
     * Clears the displayed person and shows the placeholder message.
     */
    public void clearPerson() {
        this.currentPerson = null;
        personDetailView.setVisible(false);
        personDetailView.setManaged(false);
        placeholderView.setVisible(true);
        placeholderView.setManaged(true);
    }

    /**
     * Returns the person currently being displayed, or {@code null} if none.
     */
    public Person getCurrentPerson() {
        return currentPerson;
    }
}

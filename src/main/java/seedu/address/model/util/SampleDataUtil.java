package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.WorkoutLogBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.DateOfBirth;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Location;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.WorkoutLog;
import seedu.address.model.workout.WorkoutTime;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(
                    new ClientId("4f7026f6-af46-437c-8d61-3c4b557146b5"),
                    new Name("Alex Yeoh"),
                    new Gender("M"),
                    new DateOfBirth("14/03/1992"),
                    new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Location("Anytime Fitness Geylang South East"),
                    new Note("Prefers morning workouts"),
                    new Rate("120.50"),
                    new Status("active"),
                    getTagSet("friends")),
            new Person(
                    new ClientId("e6e66102-3112-45e3-9975-ed1e35a11c21"),
                    new Name("Bernice Yu"),
                    new Gender("F"),
                    new DateOfBirth("27/11/1987"),
                    new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Location("ActiveSG Gym @ Serangoon Central"),
                    new Note("Enjoys group fitness classes"),
                    new Rate("199.00"),
                    new Status("active"),
                    getTagSet("colleagues", "friends")),
            new Person(
                    new ClientId("871891b6-7517-48f5-a0c6-3023e1e4a640"),
                    new Name("Charlotte Oliveiro"),
                    new Gender("F"),
                    new DateOfBirth("05/06/2001"),
                    new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Location("ActiveSG Gym @ Ang Mo Kio Community Centre"),
                    new Note("Interested in yoga"),
                    new Rate("80.00"),
                    new Status("active"),
                    getTagSet("neighbours")),
            new Person(
                    new ClientId("b450537f-3619-459d-9d48-6a520a22f357"),
                    new Name("David Li"),
                    new Gender("M"),
                    new DateOfBirth("19/09/1995"),
                    new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Location("ActiveSG Gym @ Serangoon Central"),
                    new Note("Prefers evening workouts"),
                    new Rate("75.00"),
                    new Status("active"),
                    getTagSet("family")),
            new Person(
                    new ClientId("5d3cf4c7-1d6f-4796-9817-d5d36e84d1a5"),
                    new Name("Irfan Ibrahim"),
                    new Gender("M"),
                    new DateOfBirth("02/01/1983"),
                    new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Location("Tampines ActiveSG Gym"),
                    new Note("Enjoys swimming"),
                    new Rate("90.00"),
                    new Status("active"),
                    getTagSet("classmates")),
            new Person(
                    new ClientId("2f65a198-a3f2-4467-b52b-c85d7768d712"),
                    new Name("Roy Balakrishnan"),
                    new Gender("M"),
                    new DateOfBirth("31/07/1998"),
                    new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Location("Anytime Fitness Aljunied 119"),
                    new Note("Prefers morning workouts"),
                    new Rate("120.00"),
                    new Status("active"),
                    getTagSet("colleagues"))
        };
    }

    public static WorkoutLog[] getSampleWorkoutLogs() {
        return new WorkoutLog[] {
            new WorkoutLog(
                    new ClientId("4f7026f6-af46-437c-8d61-3c4b557146b5"),
                    new WorkoutTime("25/02/2025 12:00"),
                    new Location("Anytime Fitness Geylang South East")),
            new WorkoutLog(
                    new ClientId("e6e66102-3112-45e3-9975-ed1e35a11c21"),
                    new WorkoutTime("16/01/2026 15:30"),
                    new Location("ActiveSG Gym @ Serangoon Central"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static WorkoutLogBook getSampleWorkoutLogBook() {
        WorkoutLogBook sampleWlb = new WorkoutLogBook();
        for (WorkoutLog sampleLog : getSampleWorkoutLogs()) {
            sampleWlb.addLog(sampleLog);
        }
        return sampleWlb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

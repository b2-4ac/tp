package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.WorkoutLogBook;

/**
 * Represents a storage for {@link seedu.address.model.WorkoutLogBook}.
 */
public interface WorkoutLogBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWorkoutLogBookFilePath();

    /**
     * Returns WorkoutLogBook data as a {@link WorkoutLogBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<WorkoutLogBook> readWorkoutLogBook() throws DataLoadingException;

    /**
     * @see #readWorkoutLogBook()
     */
    Optional<WorkoutLogBook> readWorkoutLogBook(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link WorkoutLogBook} to the storage.
     * @param workoutLogBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWorkoutLogBook(WorkoutLogBook workoutLogBook) throws IOException;

    /**
     * @see #saveWorkoutLogBook(WorkoutLogBook)
     */
    void saveWorkoutLogBook(WorkoutLogBook workoutLogBook, Path filePath) throws IOException;

}

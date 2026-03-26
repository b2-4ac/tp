package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.WorkoutLogBook;

/**
 * A class to access WorkoutLogBook data stored as a json file on the hard disk.
 */
public class JsonWorkoutLogBookStorage implements WorkoutLogBookStorage {

    private Path filePath;

    public JsonWorkoutLogBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getWorkoutLogBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<WorkoutLogBook> readWorkoutLogBook() throws DataLoadingException {
        return readWorkoutLogBook(filePath);
    }

    @Override
    public Optional<WorkoutLogBook> readWorkoutLogBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableWorkoutLogBook> jsonWorkoutLogBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableWorkoutLogBook.class);
        if (!jsonWorkoutLogBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWorkoutLogBook.get().toModelType());
        } catch (IllegalValueException ive) {
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveWorkoutLogBook(WorkoutLogBook workoutLogBook) throws IOException {
        saveWorkoutLogBook(workoutLogBook, filePath);
    }

    @Override
    public void saveWorkoutLogBook(WorkoutLogBook workoutLogBook, Path filePath) throws IOException {
        requireNonNull(workoutLogBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWorkoutLogBook(workoutLogBook), filePath);
    }

}

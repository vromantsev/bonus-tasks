package ua.hillel.exceptions;

public class ExerciseIsNotCompletedException extends RuntimeException {

    public ExerciseIsNotCompletedException(String message) {
        super(message);
    }

    public ExerciseIsNotCompletedException(String message, Throwable cause) {
        super(message, cause);
    }
}

package ink.anh.api.enums;

/**
 * Enum representing various statuses that can be returned by operations within the application.
 * These statuses provide a standardized way of communicating the outcome of various processes and operations.
 */
public enum Status {

    /** Operation completed successfully. */
    SUCCESS(0, "Operation completed successfully."),

    /** Operation failed due to generic or unspecified reasons. */
    FAILURE(1, "Operation failed due to generic or unspecified reasons."),

    /** Data validation failed during the operation. */
    VALIDATION_ERROR(2, "Data validation failed during the operation."),

    /** Error occurred with the database operation. */
    DATABASE_ERROR(3, "Error occurred with the database operation."),

    /** Connection issue occurred during the operation. */
    CONNECTION_ERROR(4, "Connection issue occurred during the operation."),

    /** Operation was not allowed due to insufficient permissions. */
    PERMISSION_DENIED(5, "Operation was not allowed due to insufficient permissions."),

    /** Operation timed out. */
    TIMEOUT(6, "Operation timed out."),

    /** An exception occurred that was not specifically handled. */
    EXCEPTION(7, "An exception occurred that was not specifically handled."),

    /** An unexpected error occurred during the operation. */
    UNEXPECTED_ERROR(8, "An unexpected error occurred during the operation."),

    /** The required entity or data was not found. */
    NOT_FOUND(9, "The required entity or data was not found."),

    /** A custom error occurred. */
    CUSTOM_ERROR(10, "A custom error occurred."),

    /** The status of the operation is unknown or was not set. */
    UNKNOWN(11, "The status of the operation is unknown or was not set.");

    private final int code;
    private final String description;

    /**
     * Constructs a new Status enum.
     * @param code the numeric code associated with the status, beginning at 0 for SUCCESS.
     * @param description a descriptive text explaining what the status represents.
     */
    Status(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Retrieves the numeric code of the status.
     * @return the numeric code associated with this status.
     */
    public int getCode() {
        return code;
    }

    /**
     * Retrieves the descriptive text of the status.
     * @return the description of this status.
     */
    public String getDescription() {
        return description;
    }
}

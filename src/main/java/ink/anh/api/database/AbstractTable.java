package ink.anh.api.database;

import java.sql.Connection;
import java.sql.SQLException;

import ink.anh.api.LibraryManager;

/**
 * Abstract class representing a database table.
 * Provides common functionality for specific table implementations.
 *
 * @param <T> the type of entity the table manages.
 */
public abstract class AbstractTable<T> {

    protected DatabaseManager dbManager;
    protected String dbName;
    private LibraryManager manager;

    /**
     * Constructs an instance of {@code AbstractTable} with the specified library manager and database name.
     *
     * @param manager the library manager responsible for managing the plugin.
     * @param dbName the name of the database table.
     */
    public AbstractTable(LibraryManager manager, String dbName) {
        this.manager = manager;
        this.dbManager = manager.getDatabaseManager();
        this.dbName = dbManager.getTablePrefix() + dbName;
    }

    /**
     * Initializes the database table.
     */
    protected abstract void initialize();

    /**
     * Inserts the specified entity into the database table.
     *
     * @param entity the entity to be inserted.
     */
    public abstract void insert(T entity);

    /**
     * Updates the specified entity in the database table.
     *
     * @param entity the entity to be updated.
     */
    public abstract void update(T entity);

    /**
     * Updates a specific field of the table using the provided {@code TableField}.
     *
     * @param <K> the type of the key of the field.
     * @param tableField the field to be updated.
     */
    public abstract <K> void updateField(TableField<K> tableField);

    /**
     * Deletes the specified entity from the database table.
     *
     * @param entity the entity to be deleted.
     */
    public abstract void delete(T entity);

    /**
     * Joins an array of strings into a single string separated by commas.
     * Returns null if all elements are null or "null".
     *
     * @param elements the array of strings to be joined.
     * @return the joined string or null if all elements are null or "null".
     */
    public static String joinOrReturnNull(String[] elements) {
        if (elements == null || elements.length == 0) {
            return null;
        }

        boolean allNull = true;
        for (String element : elements) {
            if (element != null && !element.equalsIgnoreCase("null")) {
                allNull = false;
                break;
            }
        }

        if (allNull) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        for (String element : elements) {
            if (element != null && !element.equalsIgnoreCase("null")) {
                if (result.length() > 0) {
                    result.append(",");
                }
                result.append(element);
            }
        }

        return result.length() > 0 ? result.toString() : null;
    }

    /**
     * Splits a string into an array using the specified delimiter.
     * Nullifies any elements that are equal to "null" (case-insensitive).
     *
     * @param input the string to be split.
     * @param delimiter the delimiter used for splitting.
     * @return the array of split and nullified strings.
     */
    public static String[] splitStringAndNullify(String input, String delimiter) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        String[] parts = input.split(delimiter);
        for (int i = 0; i < parts.length; i++) {
            if ("null".equalsIgnoreCase(parts[i])) {
                parts[i] = null;
            }
        }
        return parts;
    }

    /**
     * Functional interface for SQL operations that throw {@code SQLException}.
     *
     * @param <T> the type of the input to the operation.
     */
    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }

    /**
     * Executes a transaction with the provided SQL operation.
     * Rolls back the transaction in case of an error and logs the error message.
     *
     * @param sqlConsumer the SQL operation to be executed.
     * @param errorMessage the error message to be logged in case of a failure.
     */
    protected void executeTransaction(SQLConsumer<Connection> sqlConsumer, String errorMessage) {
        try (Connection conn = dbManager.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try {
                sqlConsumer.accept(conn);
                conn.commit(); // Commit transaction
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction in case of error
                ErrorLogger.log(manager.getPlugin(), e, errorMessage);
            }
        } catch (SQLException e) {
            ErrorLogger.log(manager.getPlugin(), e, "Failed to establish database connection");
        }
    }
}

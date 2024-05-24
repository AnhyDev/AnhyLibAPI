package ink.anh.api.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ink.anh.api.LibraryManager;

/**
 * Abstract class for managing database connections and tables.
 * Provides common functionality for different types of database managers.
 */
public abstract class DatabaseManager {
    private static DatabaseManager instance;
    private LibraryManager manager;
    private AbstractTableRegistrar tableRegistrar;

    protected Connection connection;
    private Map<Class<?>, AbstractTable<?>> tables = new ConcurrentHashMap<>();
    
    /**
     * Constructs an instance of {@code DatabaseManager} with the specified library manager and table registrar.
     *
     * @param manager the library manager responsible for managing the plugin.
     * @param tableRegistrar the table registrar for registering database tables.
     */
    protected DatabaseManager(LibraryManager manager, AbstractTableRegistrar tableRegistrar) {
        this.manager = manager;
        this.tableRegistrar = tableRegistrar;
    }

    /**
     * Gets the library manager associated with this database manager.
     *
     * @return the library manager.
     */
    protected LibraryManager getManager() {
        return manager;
    }

    /**
     * Gets the singleton instance of the {@code DatabaseManager}.
     * Initializes the instance if it is not already initialized.
     *
     * @param manager the library manager responsible for managing the plugin.
     * @param tableRegistrar the table registrar for registering database tables.
     * @return the singleton instance of the {@code DatabaseManager}.
     */
    public static DatabaseManager getInstance(LibraryManager manager, AbstractTableRegistrar tableRegistrar) {
        if (instance == null) {
            if (manager.getMySQLConfig().isUseMySQL()) {
                instance = new MySQLDatabaseManager(manager, tableRegistrar);
            } else {
                instance = new SQLiteDatabaseManager(manager, tableRegistrar);
            }
        }
        return instance;
    }

    /**
     * Initializes the database connection.
     */
    public abstract void initialize();

    /**
     * Gets the current connection to the database.
     *
     * @return the current connection to the database.
     */
    public abstract Connection getConnection();
    
    /**
     * Gets the table prefix for the database.
     *
     * @return the table prefix.
     */
    public abstract String getTablePrefix();

    /**
     * Registers a table with the specified class and table instance.
     *
     * @param <T> the type of the table.
     * @param clazz the class representing the table.
     * @param table the table instance to be registered.
     */
    public <T> void registerTable(Class<T> clazz, AbstractTable<T> table) {
        tables.put(clazz, table);
    }

    /**
     * Gets the table instance associated with the specified class.
     *
     * @param <T> the type of the table.
     * @param clazz the class representing the table.
     * @return the table instance associated with the class.
     */
    @SuppressWarnings("unchecked")
    public <T> AbstractTable<T> getTable(Class<T> clazz) {
        return (AbstractTable<T>) tables.get(clazz);
    }

    /**
     * Reloads the database manager with a new library manager and table registrar.
     *
     * @param manager the new library manager.
     * @param tableRegistrar the new table registrar.
     */
    public static void reload(LibraryManager manager, AbstractTableRegistrar tableRegistrar) {
        if (instance != null) {
            instance.closeConnection();
        }
        instance = null;
        getInstance(manager, tableRegistrar);
    }

    /**
     * Closes the current connection to the database.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            ErrorLogger.log(manager.getPlugin(), e, "Failed to close database connection");
        }
    }

    /**
     * Initializes all tables registered with the table registrar.
     */
    public void initializeTables() {
        tableRegistrar.registerAllTables(instance);
        tables.values().forEach(AbstractTable::initialize);
    }
}

package ink.anh.api.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ink.anh.api.LibraryManager;

/**
 * Manages the connection and initialization of an SQLite database.
 * Extends the {@link DatabaseManager} to provide SQLite specific functionality.
 */
public class SQLiteDatabaseManager extends DatabaseManager {

    /**
     * Constructs an instance of {@code SQLiteDatabaseManager} with the specified library manager and table registrar.
     * Initializes the database upon creation.
     *
     * @param manager the library manager responsible for managing the plugin.
     * @param tableRegistrar the table registrar for registering database tables.
     */
	public SQLiteDatabaseManager(LibraryManager manager, AbstractTableRegistrar tableRegistrar) {
        super(manager, tableRegistrar);
        initialize();
    }

    /**
     * Initializes the SQLite database. Ensures the database file exists and establishes a connection.
     * Logs any errors encountered during the process.
     */
    @Override
    public void initialize() {
        try {
            File dataFolder = new File(getManager().getPlugin().getDataFolder(), "database.db");
            if (!dataFolder.exists()) {
                dataFolder.createNewFile();
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            ErrorLogger.log(getManager().getPlugin(), e, "Failed to initialize database");
        }
    }

    /**
     * Provides the current connection to the SQLite database.
     * Establishes a new connection if the current one is null or closed.
     * Logs any errors encountered during the process.
     *
     * @return the connection to the SQLite database.
     */
    @Override
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                return DriverManager.getConnection("jdbc:sqlite:" + getManager().getPlugin().getDataFolder() + "/database.db");
            }
        } catch (SQLException e) {
            ErrorLogger.log(getManager().getPlugin(), e, "Failed to get database connection");
        }
        return connection;
    }

    /**
     * Provides the table prefix for the SQLite database.
     * SQLite databases typically do not use table prefixes, so this method returns an empty string.
     *
     * @return an empty string as the table prefix.
     */
    @Override
    public String getTablePrefix() {
        return "";
    }
}

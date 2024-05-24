package ink.anh.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ink.anh.api.LibraryManager;

/**
 * Manages the connection and initialization of a MySQL database.
 * Extends the {@link DatabaseManager} to provide MySQL specific functionality.
 */
public class MySQLDatabaseManager extends DatabaseManager {

    private String host;
    private String database;
    private String username;
    private String password;
    private int port;
    private String tablePrefix;
    private boolean useSSL;
    private boolean autoReconnect;

    private Connection connection;

    /**
     * Constructs an instance of {@code MySQLDatabaseManager} with the specified library manager, table registrar,
     * and database configuration. Initializes the database upon creation.
     *
     * @param manager the library manager responsible for managing the plugin.
     * @param tableRegistrar the table registrar for registering database tables.
     */
    public MySQLDatabaseManager(LibraryManager manager, AbstractTableRegistrar tableRegistrar) {
        super(manager, tableRegistrar);
        setMySQLParams(manager);
        initialize();
    }

    /**
     * Sets the MySQL connection parameters using the provided library manager.
     *
     * @param manager the library manager responsible for managing the plugin.
     */
    private void setMySQLParams(LibraryManager manager) {
        MySQLConfig mySQLConfig = manager.getMySQLConfig();
        this.host = mySQLConfig.getHost();
        this.database = mySQLConfig.getDatabase();
        this.username = mySQLConfig.getUsername();
        this.password = mySQLConfig.getPassword();
        this.port = mySQLConfig.getPort();
        this.tablePrefix = mySQLConfig.getPrefix();
        this.useSSL = mySQLConfig.isUseSSL();
        this.autoReconnect = mySQLConfig.isAutoReconnect();
    }

    /**
     * Initializes the MySQL database connection using the provided configuration.
     * Logs any errors encountered during the process.
     */
    @Override
    public void initialize() {
        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database +
                "?autoReconnect=" + this.autoReconnect + "&useSSL=" + this.useSSL +
                "&allowPublicKeyRetrieval=true",
                this.username,
                this.password
            );
        } catch (SQLException e) {
            ErrorLogger.log(getManager().getPlugin(), e, "Could not initialize MySQL connection");
        }
    }

    /**
     * Provides the current connection to the MySQL database.
     * Establishes a new connection if the current one is null or closed.
     * Logs any errors encountered during the process.
     *
     * @return the connection to the MySQL database.
     */
    @Override
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database +
                    "?autoReconnect=" + this.autoReconnect + "&useSSL=" + this.useSSL +
                    "&allowPublicKeyRetrieval=true",
                    this.username,
                    this.password
                );
            }
        } catch (SQLException e) {
            ErrorLogger.log(getManager().getPlugin(), e, "Could not retrieve MySQL connection");
        }
        return connection;
    }

    /**
     * Provides the table prefix for the MySQL database.
     *
     * @return the table prefix configured for the MySQL database.
     */
    @Override
    public String getTablePrefix() {
        return tablePrefix;
    }
}

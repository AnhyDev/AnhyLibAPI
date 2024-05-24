package ink.anh.api.database;

/**
 * Configuration class for MySQL database settings.
 */
public class MySQLConfig {
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private final String prefix;
    private final boolean useSSL;
    private final boolean autoReconnect;
    private final boolean useMySQL;

    /**
     * Constructs an instance of {@code MySQLConfig} with the specified settings.
     *
     * @param host the host address of the MySQL server.
     * @param port the port number of the MySQL server.
     * @param database the name of the MySQL database.
     * @param username the username for accessing the MySQL database.
     * @param password the password for accessing the MySQL database.
     * @param prefix the table prefix to be used in the MySQL database.
     * @param useSSL whether to use SSL for the MySQL connection.
     * @param autoReconnect whether to automatically reconnect to the MySQL database.
     * @param useMySQL whether to use MySQL as the database.
     */
    public MySQLConfig(String host, int port, String database, String username, String password, String prefix, boolean useSSL, boolean autoReconnect, boolean useMySQL) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.prefix = prefix;
        this.useSSL = useSSL;
        this.autoReconnect = autoReconnect;
        this.useMySQL = useMySQL;
    }

    /**
     * Gets the host address of the MySQL server.
     *
     * @return the host address of the MySQL server.
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets the port number of the MySQL server.
     *
     * @return the port number of the MySQL server.
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the name of the MySQL database.
     *
     * @return the name of the MySQL database.
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Gets the username for accessing the MySQL database.
     *
     * @return the username for accessing the MySQL database.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password for accessing the MySQL database.
     *
     * @return the password for accessing the MySQL database.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the table prefix to be used in the MySQL database.
     *
     * @return the table prefix to be used in the MySQL database.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Checks if SSL is to be used for the MySQL connection.
     *
     * @return {@code true} if SSL is to be used, {@code false} otherwise.
     */
    public boolean isUseSSL() {
        return useSSL;
    }

    /**
     * Checks if automatic reconnection to the MySQL database is enabled.
     *
     * @return {@code true} if automatic reconnection is enabled, {@code false} otherwise.
     */
    public boolean isAutoReconnect() {
        return autoReconnect;
    }

    /**
     * Checks if MySQL is to be used as the database.
     *
     * @return {@code true} if MySQL is to be used, {@code false} otherwise.
     */
    public boolean isUseMySQL() {
        return useMySQL;
    }
}

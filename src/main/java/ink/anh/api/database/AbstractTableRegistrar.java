package ink.anh.api.database;

/**
 * Abstract class for registering all tables in the database.
 * Subclasses should provide the implementation for registering the specific tables required.
 */
public abstract class AbstractTableRegistrar {
    
    /**
     * Registers all necessary tables in the provided database manager instance.
     *
     * @param instance the database manager instance in which the tables are to be registered.
     */
    public abstract void registerAllTables(DatabaseManager instance);

}

package ink.anh.api.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Custom event triggered when a player's data changes.
 * This event can be used to listen for changes in player-specific data and react accordingly.
 */
public class PlayerDataChangedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final String key;
    private final String[] values;

    /**
     * Constructs a PlayerDataChangedEvent with the specified player, data key, and values.
     *
     * @param player The player whose data has changed.
     * @param key The key identifying the type of data that changed.
     * @param values The new values of the player's data.
     */
    public PlayerDataChangedEvent(Player player, String key, String[] values) {
        this.player = player;
        this.key = key;
        this.values = values.clone(); // Cloning for data integrity and safety.
    }

    /**
     * Gets the player associated with this event.
     *
     * @return The player whose data has changed.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the key representing the type of data that changed.
     *
     * @return The data key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the new values of the data that changed.
     * Returns a clone of the values to prevent external modification.
     *
     * @return A clone of the new data values.
     */
    public String[] getValues() {
        return values.clone(); // Returning a copy to prevent external modifications.
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Gets the list of handlers handling this event.
     * Required for custom events in Bukkit.
     *
     * @return The handler list.
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }
}

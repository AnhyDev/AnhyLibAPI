package ink.anh.api.utils;

import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

/**
 * Utility class for executing tasks synchronously or asynchronously on the server.
 */
public class SyncExecutor {
    private static Plugin plugin;

    /**
     * Initializes the SyncExecutor with the provided plugin instance.
     * 
     * @param pluginInstance the instance of the plugin to be used for scheduling tasks
     */
    public static void init(Plugin pluginInstance) {
        plugin = pluginInstance;
    }

    /**
     * Runs a given {@link Runnable} synchronously on the main server thread, and calls the specified event.
     * If the event is cancellable and has been cancelled, the runnable will not be executed.
     * 
     * @param event    the event to be called before executing the runnable
     * @param runnable the task to be run synchronously if the event is not cancelled
     */
    public static void runSync(Event event, Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.getPluginManager().callEvent(event);
            if (!(event instanceof Cancellable) || !((Cancellable) event).isCancelled()) {
                runnable.run();
            }
        });
    }

    /**
     * Runs a given {@link Runnable} synchronously on the main server thread.
     * 
     * @param runnable the task to be run synchronously
     */
    public static void runSync(Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    /**
     * Runs a given {@link RunnableWithArg} with a specified object synchronously on the main server thread.
     * 
     * @param <T>      the type of the object passed to the RunnableWithArg
     * @param object   the object to be passed to the runnable
     * @param runnable the task to be run synchronously with the provided object
     */
    public static <T> void runSync(T object, RunnableWithArg<T> runnable) {
        Bukkit.getScheduler().runTask(plugin, () -> runnable.run(object));
    }

    /**
     * Runs a given {@link Runnable} asynchronously.
     * 
     * @param runnable the task to be run asynchronously
     */
    public static void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    /**
     * Runs a given {@link RunnableWithArg} with a specified object asynchronously.
     * 
     * @param <T>      the type of the object passed to the RunnableWithArg
     * @param object   the object to be passed to the runnable
     * @param runnable the task to be run asynchronously with the provided object
     */
    public static <T> void runAsync(T object, RunnableWithArg<T> runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> runnable.run(object));
    }

    /**
     * Functional interface representing a runnable that accepts a single argument.
     * 
     * @param <T> the type of the argument passed to the runnable
     */
    @FunctionalInterface
    public interface RunnableWithArg<T> {
        /**
         * Executes with the given object.
         * 
         * @param object the object to be used by the runnable
         */
        void run(T object);
    }
}

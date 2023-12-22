package ink.anh.api.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.bukkit.scheduler.BukkitRunnable;


import org.bukkit.plugin.Plugin;
import ink.anh.api.messages.Logger;

/**
 * Handles the reporting of plugin information to a remote server.
 * Intended to send the plugin's name as part of a JSON payload to a specified URL.
 */
public class PluginReporter {

    private final Plugin plugin;

    /**
     * Constructs a new PluginReporter with a reference to the plugin.
     *
     * @param plugin The plugin instance that this reporter is associated with.
     */
    public PluginReporter(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Reports the name of the plugin to a predefined URL as a JSON payload.
     * Logs the response code and body to the server's console.
     */
    public void reportPluginName() {
        new BukkitRunnable() {
            @Override
            public void run() {
            	if (!sendReport()) {
            		sendReport();
            	}
            }
        }.runTaskAsynchronously(plugin);
    }

    private boolean sendReport() {
        try {
            String pluginName = plugin.getDescription().getName();
            String jsonData = String.format("{\"plugin\":\"%s\"}", pluginName);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://dev.anh.ink/monitoring/receive-plugin-name.php"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if ("1".equals(response.body())) {
                return true;
            }
        } catch (Exception e) {
            Logger.error(plugin, "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
		return false;
    }
}

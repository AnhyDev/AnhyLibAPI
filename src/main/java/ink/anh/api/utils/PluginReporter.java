package ink.anh.api.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
        try {
            String pluginName = plugin.getDescription().getName();

            // Prepare the JSON string with the plugin name.
            String jsonData = String.format("{\"plugin\":\"%s\"}", pluginName);

            // Set up the HTTP client and construct the request.
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://dev.anh.ink/reporter/receive-plugin-name.php"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                    .build();

            // Send the data and get the response.
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the results.
            Logger.info(plugin, "Response code: " + response.statusCode());
            Logger.info(plugin, "Response body: " + response.body());
        } catch (Exception e) {
            // Log severe errors if the reporting fails.
            Logger.error(plugin, "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

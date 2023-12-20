package ink.anh.api.messages;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import ink.anh.api.AnhyLibAPI;
import ink.anh.api.LibraryManager;
import ink.anh.api.lingo.Translator;
import ink.anh.api.utils.LangUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

/**
 * Utility class for sending formatted messages to players or the console.
 */
public class Messenger {

    /**
     * Sends a formatted message to a CommandSender (player or console).
     *
     * @param libraryManager The LibraryManager instance to access language data.
     * @param sender The CommandSender to whom the message will be sent.
     * @param message The message to be sent.
     * @param type The type of message, which determines the color.
     */
    public static void sendMessage(LibraryManager libraryManager, CommandSender sender, String message, MessageType type) {
        String pluginName = "[" + libraryManager.getPluginName() + "] ";
        String coloredMessage = Translator.translateKyeWorld(libraryManager, message, getPlayerLanguage(sender));

        if (sender instanceof Player) {
            Player player = (Player) sender;
            TextColor color = TextColor.fromCSSHexString(type.getColor(true));
            Component pluginNameComponent = Component.text(pluginName)
                                                     .color(TextColor.fromCSSHexString("#1D87E4")) // Blue color for plugin name
                                                     .decorate(TextDecoration.BOLD); // Bold only for the plugin name

            Component messageComponent = Component.text(coloredMessage)
                                                 .color(color)
                                                 .decoration(TextDecoration.BOLD, false); // No bold for the message text

            sendVersionDependentPlayerMessage(libraryManager, player, pluginName + coloredMessage, pluginNameComponent.append(messageComponent));

        } else {
            sendConsole(libraryManager.getPlugin(), coloredMessage, type);
        }
    }

    /**
     * Sends a simple formatted message to a CommandSender (player or console).
     *
     * @param libraryManager The LibraryManager instance to access language data.
     * @param sender The CommandSender to whom the message will be sent.
     * @param message The message to be sent.
     * @param icon The icon to be prefixed to the message.
     * @param type The type of message.
     */
    public static void sendMessageSimple(LibraryManager libraryManager, CommandSender sender, String message, String icon, MessageType type) {
        String coloredMessage = Translator.translateKyeWorld(libraryManager, icon + message, getPlayerLanguage(sender));

        if (sender instanceof Player) {
            Player player = (Player) sender;
            TextColor color = TextColor.fromCSSHexString(type.getColor(true));
            Component messageComponent = Component.text(coloredMessage)
                                                 .color(color);
            
            sendVersionDependentPlayerMessage(libraryManager, player, coloredMessage, messageComponent);
        } else {
            sendConsole(libraryManager.getPlugin(), coloredMessage, type);
        }
    }
    
    /**
     * Sends an interactive message for showing a folder's contents to a CommandSender.
     *
     * @param libraryManager The LibraryManager instance to access language data.
     * @param sender The CommandSender to whom the message will be sent.
     * @param patch The patch of the folder.
     * @param folder The folder name.
     * @param icon The icon to be prefixed to the folder name.
     * @param type The type of message.
     * @param langs The languages for translation.
     */
    public static void sendShowFolder(LibraryManager libraryManager, CommandSender sender, String patch, String folder, String icon, MessageType type, String[] langs) {
        String separator = patch.endsWith("/") ? "" : "/";
        String command = "/lingo dir " + patch + separator + folder;
        String showFolder = Translator.translateKyeWorld(libraryManager, "lingo_file_show_folder_contents ", langs);
        
        // Yellow color for hover text
        TextColor hoverTextColor = TextColor.fromCSSHexString("#FFFF00");
        Component hoverTextComponent = Component.text("\n " + showFolder + folder + " \n")
                                                .color(hoverTextColor);

        TextColor color = TextColor.fromCSSHexString(type.getColor(true));
        Component folderComponent = Component.text(icon + folder)
                                                .color(color)
                                                .hoverEvent(HoverEvent.showText(hoverTextComponent))
                                                .clickEvent(ClickEvent.runCommand(command));

        if (sender instanceof Player) {
            Player player = (Player) sender;

            sendVersionDependentPlayerMessage(libraryManager, player, icon + folder, folderComponent);
        } else {
            // For console, send a simple message without interactivity
            String coloredFolder = Translator.translateKyeWorld(libraryManager, icon + folder, null);
            sendConsole(libraryManager.getPlugin(), coloredFolder, type);
        }
    }
    
    /**
     * Sends a formatted message to the console.
     *
     * @param plugin The plugin instance.
     * @param message The message to be sent.
     * @param type The type of message.
     */
    private static void sendConsole(Plugin plugin, String message, MessageType type) {
        String formatedMessage = type.formatConsoleColor(message);
        switch (type) {
            case CRITICAL_ERROR:
                Logger.error(plugin, formatedMessage);
                break;
            case ERROR:
                Logger.error(plugin, formatedMessage);
                break;
            case WARNING:
                Logger.warn(plugin, formatedMessage);
                break;
            default:
                Logger.info(plugin, formatedMessage);
                break;
        }
    }
    
    /**
     * Sends a formatted message to a player, considering server version compatibility.
     * 
     * @param libraryManager The LibraryManager instance for language and plugin information.
     * @param player The player to whom the message is to be sent.
     * @param message The formatted a String.
     * @param messageComponent The formatted message as a Component.
     */
    public static void sendVersionDependentPlayerMessage(LibraryManager libraryManager, Player player, String message, Component messageComponent) {
    	BukkitAudiences bukkitAudiences = BukkitAudiences.create(libraryManager.getPlugin());
        if (AnhyLibAPI.getInstance().getCurrentVersion() < 1.19 && (player instanceof Audience)) {
        	if (player instanceof Audience) {
            	Audience audience = (Audience) player;
            	audience.sendMessage(messageComponent);
        	} else {
        		player.sendMessage(message);
        	}
        } else {
        	bukkitAudiences.sender(player).sendMessage(messageComponent);
        }
    }
    
    /**
     * Retrieves the player's language preferences.
     *
     * @param sender The CommandSender, assumed to be a player.
     * @return An array of language codes, or null if the sender is not a player.
     */
    private static String[] getPlayerLanguage(CommandSender sender) {
        return sender instanceof Player ? LangUtils.getPlayerLanguage((Player) sender) : null;
    }
}

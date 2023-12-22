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

/**
 * Utility class for sending formatted messages to players or the console.
 */
public class Messenger {
    
    /**
     * Sends a formatted message to a player using advanced text components.
     * This method utilizes the Adventure library for advanced text formatting and interactivity.
     *
     * @param plugin The plugin instance, used for creating an audience with the BukkitAudiences library.
     * @param player The player to whom the message is to be sent.
     * @param messageComponent The MessageComponents instance containing the formatted text component.
     */
    public static void sendMessage(Plugin plugin, CommandSender sender, MessageComponents messageComponent, String message) {
        sendComponent(plugin, sender, messageComponent.getComponent(), message);
    }

    /**
     * Sends a formatted message to a CommandSender (player or console).
     *
     * @param libraryManager The LibraryManager instance to access language data.
     * @param sender The CommandSender to whom the message will be sent.
     * @param message The message to be sent.
     * @param type The type of message, which determines the color.
     
     * @deprecated replace to {@link #sendMessage(Plugin plugin, CommandSender sender, MessageComponents messageComponent, String message)}.
     */
    @Deprecated
    public static void sendMessage(LibraryManager libraryManager, CommandSender sender, String message, MessageType type) {
        String pluginName = "[" + libraryManager.getPluginName() + "] ";
        String translatedMessage = Translator.translateKyeWorld(libraryManager, message, getPlayerLanguage(sender));

        if (sender instanceof Player) {
            Player player = (Player) sender;
            MessageComponents messageComponents = MessageComponents.builder()
					   											   .content(pluginName)
					   											   .hexColor("#1D87E4")
					   											   .decoration("BOLD", true)
					   											   .content(translatedMessage)
					   											   .hexColor(type.getColor(true)).build();
            
            sendComponent(libraryManager.getPlugin(), player, messageComponents.getComponent(), translatedMessage);

        } else {
            sendConsole(libraryManager.getPlugin(), type.getColor(false) + translatedMessage, type);
        }
    }

    /**
     * Sends a formatted message to a player with an optional hexadecimal color code.
     * If the provided color code is invalid, it falls back to the default color of the specified message type.
     *
     * @param libraryManager The LibraryManager instance to access language data.
     * @param player The player to whom the message will be sent.
     * @param message The message to be sent.
     * @param hexColor The hexadecimal color code for the message.
     
     * @deprecated replace to {@link #sendMessage(Plugin plugin, CommandSender sender, MessageComponents messageComponent, String message)}.
     */
    @Deprecated
    public static void sendMessage(LibraryManager libraryManager, Player player, String message, String hexColor) {
        String translatedMessage = Translator.translateKyeWorld(libraryManager, message, getPlayerLanguage(player));
        MessageComponents messageComponents = MessageComponents.builder()
        													   .content(translatedMessage)
        													   .hexColor(hexColor).build();
        sendComponent(libraryManager.getPlugin(), player, messageComponents.getComponent(), translatedMessage);
    }

    /**
     * Sends a simple formatted message to a CommandSender (player or console).
     *
     * @param libraryManager The LibraryManager instance to access language data.
     * @param sender The CommandSender to whom the message will be sent.
     * @param message The message to be sent.
     * @param icon The icon to be prefixed to the message.
     * @param type The type of message.
     
     * @deprecated replace to {@link #sendMessage(Plugin plugin, CommandSender sender, MessageComponents messageComponent, String message)}.
     */
    @Deprecated
    public static void sendMessageSimple(LibraryManager libraryManager, CommandSender sender, String message, String icon, MessageType type) {
        String translatedMessage = Translator.translateKyeWorld(libraryManager, icon + message, getPlayerLanguage(sender));

        if (sender instanceof Player) {
            Player player = (Player) sender;
            MessageComponents messageComponents = MessageComponents.builder()
            													   .content(translatedMessage)
            													   .hexColor(type.getColor(true)).build();
            sendComponent(libraryManager.getPlugin(), player, messageComponents.getComponent(), translatedMessage);
        } else {
            sendConsole(libraryManager.getPlugin(), type.getColor(false) + translatedMessage, type);
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
     * @deprecated replace to {@link #sendMessage(Plugin plugin, CommandSender sender, MessageComponents messageComponent, String message)}.
     */
    @Deprecated
    public static void sendShowFolder(LibraryManager libraryManager, CommandSender sender, String patch, String folder, String icon, MessageType type, String[] langs) {
        String separator = patch.endsWith("/") ? "" : "/";
        String command = "/lingo dir " + patch + separator + folder;
        String showFolder = Translator.translateKyeWorld(libraryManager, "lingo_file_show_folder_contents ", langs);

        // Створення спливаючого текстового компонента
        MessageComponents hoverTextComponent = MessageComponents.builder()
            .content("\n " + showFolder + folder + " \n")
            .hexColor("#FFFF00") // жовтий колір в hex форматі
            .build();

        // Створення основного текстового компонента
        Component folderComponent = MessageComponents.builder()
            .content(icon + folder)
            .hexColor(type.getColor(true)) // колір з MessageType
            .hoverComponent(hoverTextComponent) // використання спливаючого компонента
            .clickActionRunCommand(command) // клікабельна подія
            .build().getComponent();

        // Відправка компонента гравцеві або в консоль
        if (sender instanceof Player) {
            Player player = (Player) sender;
            sendComponent(libraryManager.getPlugin(), player, folderComponent, icon + folder);
        } else {
            // Для консолі відправити просте повідомлення без інтерактивності
            sendConsole(libraryManager.getPlugin(), icon + folder, type);
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
     * It utilizes the Adventure library for versions 1.19 and above, otherwise, it falls back to legacy chat.
     * 
     * @param libraryManager The LibraryManager instance for language and plugin information.
     * @param player The player to whom the message is to be sent.
     * @param message The formatted message as a String for legacy chat.
     * @param messageComponent The formatted message as a Component for Adventure chat.
     */
    private static void sendComponent(Plugin plugin, CommandSender sender, Component messageComponent, String message) {
    	if (sender instanceof Player) {
    		Player player = (Player) sender;
            if (AnhyLibAPI.getInstance().getCurrentVersion() < 1.19 && (player instanceof Audience)) {
            	if (sender instanceof Audience) {
                	Audience audience = (Audience) player;
                	audience.sendMessage(messageComponent);
            	} else {
            		player.sendMessage(message);
            	}
            } else {
            	BukkitAudiences bukkitAudiences = BukkitAudiences.create(plugin);
            	bukkitAudiences.sender(player).sendMessage(messageComponent);
            }
    	} else {
    		sender.sendMessage(message);
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

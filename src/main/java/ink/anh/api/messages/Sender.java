package ink.anh.api.messages;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ink.anh.api.LibraryManager;
import ink.anh.api.lingo.Translator;
import ink.anh.api.utils.LangUtils;

/**
 * Abstract class that provides message sending functionalities to various types of command senders.
 */
public abstract class Sender {

    public LibraryManager libraryManager;

    /**
     * Constructs a Sender with a specified {@link LibraryManager}.
     *
     * @param libraryManager The library manager that provides access to plugin-specific settings and utilities.
     */
    public Sender(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
    }

    /**
     * Sends a formatted message to one or more command senders without specifying whether to add the plugin name.
     *
     * @param textForFormatting The text to be formatted and sent.
     * @param type              The type of message which dictates how the message is formatted.
     * @param senders           The command senders (such as players or console) to receive the message.
     */
    public void sendMessage(MessageForFormatting textForFormatting, MessageType type, CommandSender... senders) {
        sendMessage(textForFormatting, type, true, senders);
    }

    /**
     * Sends a formatted message to one or more command senders, with an option to add the plugin name to the message.
     *
     * @param textForFormatting The text to be formatted and sent.
     * @param type              The type of message which dictates how the message is formatted.
     * @param addPluginName     Whether to prepend the message with the plugin's name.
     * @param senders           The command senders (such as players or console) to receive the message.
     */
    public void sendMessage(MessageForFormatting textForFormatting, MessageType type, boolean addPluginName, CommandSender... senders) {
        for (CommandSender sender : senders) {
            if (sender != null) {
                MessageChat.sendMessage(libraryManager, sender, textForFormatting, type, addPluginName);
            }
        }
    }
    
    /**
     * Retrieves the language preferences for a command sender.
     *
     * @param sender The command sender whose language preferences are to be retrieved.
     * @return An array of language codes preferred by the sender, or the default language if not a player.
     */
    public String[] getLangs(CommandSender sender) {
        return sender instanceof Player ? LangUtils.getPlayerLanguage((Player) sender) : new String[] {libraryManager.getDefaultLang()};
    }
    
    /**
     * Translates a message based on the command sender's language preferences.
     *
     * @param sender  The command sender for whom the message is to be translated.
     * @param message The message text to translate.
     * @return The translated text.
     */
    public String translate(CommandSender sender, String message) {
        return Translator.translateKyeWorld(libraryManager, message, (sender != null ? getLangs(sender) : null));
    }
}
